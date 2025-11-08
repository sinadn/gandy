package com.example.gandy.controller;

import com.example.gandy.entity.*;
import com.example.gandy.exception.TokenRefreshException;
import com.example.gandy.payload.request.OtpRequest;
import com.example.gandy.payload.request.ProductRequest;
import com.example.gandy.payload.request.RegisterRequest;
import com.example.gandy.payload.request.UserInfoRequest;
import com.example.gandy.payload.response.MessageResponse;
import com.example.gandy.payload.response.UserInfoResponse;
import com.example.gandy.repo.RoleRepository;
import com.example.gandy.repo.UsersRepository;
import com.example.gandy.security.jwt.JwtUtils;
import com.example.gandy.security.services.RefreshTokenService;
import com.example.gandy.security.services.UserDetailsImpl;
import com.example.gandy.service.AddressServiceImpl;
import com.example.gandy.service.UsersServiceImpl;
import com.example.gandy.service.ActivationServiceImpl;
import com.example.gandy.utility.RandomString;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class UsersController {


    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsersRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    RefreshTokenService refreshTokenService;

    @Autowired
    UsersServiceImpl usersServiceImpl;

    @Autowired
    ActivationServiceImpl activationService;

    @Autowired
    AddressServiceImpl addressService;


    @PostMapping("/signup")
    public ResponseEntity<?> RegisterUser(@Valid @RequestBody OtpRequest otpRequest) {
        try {
            String pass = RandomString.getAlphaNumericString(10);
            Activation activation = activationService.confirmOtp(otpRequest.getMobile(), otpRequest.getOtp());
            Duration duration = Duration.between(activation.getExpiration_date(), LocalDateTime.now());
            long years = ChronoUnit.YEARS.between(activation.getExpiration_date(), LocalDateTime.now());
            long months = ChronoUnit.MONTHS.between(activation.getExpiration_date(), LocalDateTime.now());
            if (years == 0 && months == 0 && duration.toDays() == 0 && duration.toHours() == 0 && duration.toMinutes() <= 60) {
                Users users = usersServiceImpl.findUser(otpRequest.getMobile());
                users.setPassword(encoder.encode(pass));
                users.setActive(true);
                usersServiceImpl.createObjects(users);

                Authentication authentication = authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(users.getMobile(), pass));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
                ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);
                List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                        .collect(Collectors.toList());

//                refreshTokenService.deleteByUserId(userDetails.getId());
                RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
                ResponseCookie jwtRefreshCookie = jwtUtils.generateRefreshJwtCookie(refreshToken.getToken());

                return ResponseEntity.ok()
                        .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                        .header(HttpHeaders.SET_COOKIE, jwtRefreshCookie.toString())
                        .body(new UserInfoResponse(userDetails.getId(),
                                userDetails.getMobile(),
                                userDetails.getNationalCode(),
                                roles));

//                return ResponseEntity.ok(new JwtResponse(jwt, refreshToken.getToken(), userDetails.getId(),
//                        userDetails.getMobile(), roles));

            }
        } catch (Exception e) {
            e.getMessage();
        }
        return ResponseEntity.ok("server error");
    }

    @PostMapping("/signin")
    public ResponseEntity<?> LoginUser(@Valid @RequestBody RegisterRequest registerRequest) {
        Users users = null;
        Random randomNumber = new Random();
        int random = 10000 + randomNumber.nextInt(90000);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formatDateTime = LocalDateTime.now().format(formatter);
        LocalDateTime expire = LocalDateTime.parse(formatDateTime, formatter);

        try {
            users = usersServiceImpl.findUser(registerRequest.getMobile());
        } catch (Exception e) {
            e.getMessage();
        }
        if (users != null) {
            activationService.addObject(initActivaion(users, random, expire));
            sendSms(registerRequest.getMobile(), random);
            return ResponseEntity.ok(registerRequest.getMobile());
        }


        users = new Users(registerRequest.getMobile());
//        Set<String> strRoles = registerRequest.getRole();
        Set<Role> roles = new HashSet<>();

        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(userRole);



//        if (strRoles == null) {
//            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
//                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//            roles.add(userRole);
//        } else {
//            strRoles.forEach(role -> {
//                switch (role) {
//                    case "admin":
//                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
//                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                        roles.add(adminRole);
//                        break;
//                    case "mod":
//                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
//                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                        roles.add(modRole);
//                        break;
//                    default:
//                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
//                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                        roles.add(userRole);
//                }
//            });
//        }


        users.setRoles(roles);
        users.setCreate_at(expire);
        usersServiceImpl.createObjects(users);
        activationService.addObject(initActivaion(usersServiceImpl.findUser(registerRequest.getMobile()), random, expire));
        sendSms(registerRequest.getMobile(), random);
        System.out.println("salaaaam");
        return ResponseEntity.ok(registerRequest.getMobile());
    }


    @PostMapping("/signout")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> logoutUser(HttpServletRequest request) {
        String refreshToken = jwtUtils.getJwtRefreshFromCookies(request);

        Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principle.toString() != "anonymousUser") {
            Long userId = ((UserDetailsImpl) principle).getId();
            refreshTokenService.deleteByUserId(userId , refreshToken);
        }

        System.out.println(refreshToken);
        ResponseCookie jwtCookie = jwtUtils.getCleanJwtCookie();
        ResponseCookie jwtRefreshCookie = jwtUtils.getCleanJwtRefreshCookie();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .header(HttpHeaders.SET_COOKIE, jwtRefreshCookie.toString())
                .body(new MessageResponse("You've been signed out!"));
    }


    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(HttpServletRequest request) {
        String refreshToken = jwtUtils.getJwtRefreshFromCookies(request);
        if ((refreshToken != null) && (refreshToken.length() > 0)) {
            return refreshTokenService.findByToken(refreshToken)
                    .map(refreshTokenService::verifyExpiration)
                    .map(RefreshToken::getUser)
                    .map(user -> {
                        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(user);

                        return ResponseEntity.ok()
                                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                                .body(new MessageResponse("Token is refreshed successfully!"));
                    })
                    .orElseThrow(() -> new TokenRefreshException(refreshToken,
                            "Refresh token is not in database!"));
        }
        return ResponseEntity.badRequest().body(new MessageResponse("Refresh Token is empty!"));
    }





    @PostMapping("/fillUserInfo")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> Register(@Valid @RequestBody UserInfoRequest userInfoRequest) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formatDateTime = LocalDateTime.now().format(formatter);
        LocalDateTime update = LocalDateTime.parse(formatDateTime, formatter);



        Users users = null;
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        UserDetailsImpl userDetails1 = (UserDetailsImpl) userDetails ;


        try {
            users = usersServiceImpl.findUser(userDetails1.getMobile());
        } catch (Exception e) {
            e.getMessage();
        }

    users.setActive(true);
    users.setName(userInfoRequest.getName());
    users.setFamily(userInfoRequest.getFamily());
    users.setNationalCode(userInfoRequest.getNationalCode());
    users.setId(users.getId());
    users.setUpdate_at(update);
    users.setBirthDay(userInfoRequest.getBirthDay());
    users.setEmail(userInfoRequest.getEmail());

        usersServiceImpl.createObjects(users);
        return ResponseEntity.ok("ثبت با موفقیت انجام شد");
    }



    @PutMapping("/updateUser")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> updateUser(@Valid @RequestBody Users users) {
        Users users1 = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formatDateTime = LocalDateTime.now().format(formatter);
        LocalDateTime update = LocalDateTime.parse(formatDateTime, formatter);
        try {
            users1 = usersServiceImpl.findUserById(users.getId());
            users1.setFamily(users.getFamily());
            users1.setName(users.getName());
            users1.setMobile(users.getMobile());
            users1.setActive(users.getActive());
            users1.setNationalCode(users.getNationalCode());
            users1.setPassword(encoder.encode(users.getPassword()));
            users1.setUpdate_at(update);
        } catch (Exception e) {
            e.getMessage();
        }
        usersServiceImpl.createObjects(users1);
        return ResponseEntity.ok("ثبت با موفقیت انجام شد");
    }




    @PostMapping("/getUserInfo")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getUserInfo() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
        .getPrincipal();
        UserDetailsImpl userDetails1 = (UserDetailsImpl) userDetails ;
        return ResponseEntity.ok(usersServiceImpl.findUser(userDetails1.getMobile()));
    }


    @PostMapping("/getAllUser/{pageNumber}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getAllUser(@PathVariable("pageNumber") int pageNumber) {
        return ResponseEntity.ok(usersServiceImpl.getAllUser(pageNumber));
    }


    @PostMapping("/findUser/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> findUser(@PathVariable("id") long id) {
        return ResponseEntity.ok(usersServiceImpl.findUserById(id));
    }

    @PostMapping("/findUserByMobile/{number}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> findUserByMobile(@PathVariable("number") String number) {
        return ResponseEntity.ok(usersServiceImpl.findUser(number));
    }


    @PostMapping("/getUserByWords")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getUserByWords(@RequestBody ProductRequest productRequest) {
        if (!productRequest.name.equals("")) {
            return ResponseEntity.ok(usersServiceImpl.getUserByWords(productRequest.getName()));
        }
        return ResponseEntity.ok(new ArrayList<>());
    }



    @PostMapping("/getUserByMobile")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> findUserByMobile(@RequestBody ProductRequest productRequest) {
        if (!productRequest.name.equals("")) {
            return ResponseEntity.ok(usersServiceImpl.findUserByMobile(productRequest.getName()));
        }
        return ResponseEntity.ok(new ArrayList<>());
    }







    @DeleteMapping("/deleteById/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteById(@Valid @PathVariable long id) {
        usersServiceImpl.deleteByIdObject(id);
        return ResponseEntity.ok("The desired item was deleted successfully!");
    }


    public Activation initActivaion(Users users, int random, LocalDateTime expireDate) {
        Activation activation = new Activation();
        activation.setUsers(users);
        activation.setOtp(String.valueOf(random));
        activation.setExpiration_date(expireDate);
        return activation;
    }


    public void sendSms(String mobile, int number)  {
        final String uri = "https://panel.ghasedaksms.com/tools/urlservice/send/?username=farzad.arabs@gmail.com&password=Sindin46044604!&from=9999178836&to=" + mobile + "&message=برای احراز هویت در سایت موبایل گاندی کد " + number + " را وارد کنید ";
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);
        System.out.println(result);
    }
}
