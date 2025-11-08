package com.example.gandy.controller;

import com.example.gandy.entity.Category;
import com.example.gandy.entity.SubCategory;
import com.example.gandy.entity.Warranty;
import com.example.gandy.payload.request.CategoryRequest;
import com.example.gandy.payload.request.ProductRequest;
import com.example.gandy.payload.request.SubCategoryRequest;
import com.example.gandy.payload.response.AmazingOfferResponse;
import com.example.gandy.payload.response.MenuResponse;
import com.example.gandy.repo.SubCategoryRepository;
import com.example.gandy.service.CategoryServiceImpl;
import com.example.gandy.service.CityServiceImpl;
import com.example.gandy.service.SubCategoryServiceImpl;
import com.example.gandy.utility.DiscountCalculation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/subcategory")
public class SubCategoryController {

    @Autowired
    SubCategoryRepository repository;

    @Autowired
    SubCategoryServiceImpl subCategoryService;


    @Autowired
    CategoryServiceImpl categoryService;


    @PostMapping("/getsub/")
    public ResponseEntity<?> getsubcategory(@RequestBody SubCategoryRequest subCategoryRequest) {
        return ResponseEntity.ok(subCategoryService.findObjects(subCategoryRequest.getParentId()));
    }

    @PostMapping("/getmain")
    public ResponseEntity<?> getMainMenu(@RequestBody CategoryRequest categoryRequest) {
        return ResponseEntity.ok(subCategoryService.findMain(categoryRequest.getIsMain(), categoryRequest.getIsActive()));
    }

    @PostMapping("/getAllMenu")
    public ResponseEntity<?> getAllMenu() {
        List<Category> menuList = new ArrayList<>();
        List<SubCategory> sublist = new ArrayList<>();

        menuList = categoryService.findObjects();
        sublist = subCategoryService.getAllMenu();
        List<MenuResponse> menuResponseList = new ArrayList<>();

        for (int i = 0; i < menuList.size(); i++) {
            MenuResponse menuResponse = new MenuResponse();
            menuResponse.setId(menuList.get(i).getId());
            menuResponse.setName(menuList.get(i).getName());
            menuResponse.setUrl(menuList.get(i).getUrl());
            menuResponse.setProductType(menuList.get(i).getProductType());
            menuResponse.setOrder_val(menuList.get(i).getOrderVal());
            menuResponseList.add(menuResponse);
        }

        for (int j = 0; j < menuResponseList.size(); j++) {
            List<MenuResponse> subMenuResponseList = new ArrayList<>();
            for (int i = 0; i < sublist.size(); i++) {
                if (menuResponseList.get(j).getId() == sublist.get(i).getParentId().getId()) {
                    MenuResponse menuResponse = new MenuResponse();
                    menuResponse.setId(sublist.get(i).getSubId().getId());
                    menuResponse.setName(sublist.get(i).getSubId().getName());
                    menuResponse.setUrl(sublist.get(i).getSubId().getUrl());
                        menuResponse.setOrder_val(sublist.get(i).getSubId().getOrderVal());
                    menuResponse.setProductType(sublist.get(i).getSubId().getProductType());
                    if (sublist.get(i).getSubId().getImgName() != null)
                        menuResponse.setImage(sublist.get(i).getSubId().getImgName());
                    if (sublist.get(i).getSubId().getAmount() != 0)
                        menuResponse.setAmount(sublist.get(i).getSubId().getAmount());
                    if (sublist.get(i).getSubId().getAttributeOption() != null)
                        menuResponse.setAttributeOption(sublist.get(i).getSubId().getAttributeOption());
                    if (sublist.get(i).getSubId().getProductTag() != null)
                        menuResponse.setProductTag(sublist.get(i).getSubId().getProductTag());
                    subMenuResponseList.add(menuResponse);
                    menuResponseList.get(j).setSubMenuResponses(subMenuResponseList);
                }
            }
        }



            for (int j = 0; j < menuResponseList.size(); j++) {
                if (menuResponseList.get(j).getSubMenuResponses() != null)
                    for (int i = 0; i < menuResponseList.get(j).getSubMenuResponses().size(); i++) {
                    List<MenuResponse> subMenuResponseList = new ArrayList<>();
                    for (int k = 0; k < sublist.size(); k++) {
                        if (menuResponseList.get(j).getSubMenuResponses().get(i).getId() == sublist.get(k).getParentId().getId()) {
                            MenuResponse menuResponse = new MenuResponse();
                            menuResponse.setId(sublist.get(k).getSubId().getId());
                            menuResponse.setName(sublist.get(k).getSubId().getName());
                            menuResponse.setUrl(sublist.get(k).getSubId().getUrl());
                                menuResponse.setOrder_val(sublist.get(k).getSubId().getOrderVal());
                            menuResponse.setProductType(sublist.get(k).getSubId().getProductType());
                            if (sublist.get(i).getSubId().getImgName() != null)
                                menuResponse.setImage(sublist.get(k).getSubId().getImgName());
                            if (sublist.get(k).getSubId().getAmount() != 0)
                                menuResponse.setAmount(sublist.get(k).getSubId().getAmount());
                            if (sublist.get(k).getSubId().getAttributeOption() != null)
                                menuResponse.setAttributeOption(sublist.get(k).getSubId().getAttributeOption());
                            if (sublist.get(k).getSubId().getProductTag() != null)
                                menuResponse.setProductTag(sublist.get(k).getSubId().getProductTag());

                            subMenuResponseList.add(menuResponse);
                            menuResponseList.get(j).getSubMenuResponses().get(i).setSubMenuResponses(subMenuResponseList);
                        }
                    }
                }
            }
//
        return ResponseEntity.ok(menuResponseList);
    }


    @PostMapping("/findAllMenu/{pageNumber}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getAllUser(@PathVariable("pageNumber") int pageNumber) {
        return ResponseEntity.ok(subCategoryService.findAllMenu(pageNumber));
    }


    @PostMapping("/add")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> addSubCategory(@RequestBody SubCategory subCategory) {
        subCategoryService.createObjects(subCategory);
        return ResponseEntity.ok("SubCategory register successfully!");
    }


    @PutMapping("/update")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> update(@RequestBody SubCategory subCategory) {

        repository.findById(subCategory.getId())
                .map(item -> {
                    item.setSubId(subCategory.getSubId());
                    item.setParentId(subCategory.getParentId());
                    subCategoryService.createObjects(item);
                    return ResponseEntity.ok("Product updated successfully!");
                }).orElseGet(() -> {
                    subCategoryService.createObjects(subCategory);
                    return ResponseEntity.ok("Product register successfully!");
                });
        return ResponseEntity.ok("Product registered successfully!");
    }




    @PostMapping("/getSubCategoryByWords")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getCategoryByWords(@RequestBody ProductRequest productRequest) {
        if (!productRequest.name.equals("")) {
            return ResponseEntity.ok(subCategoryService.findSubCategoryByWords(productRequest.getName()));
        }
        return ResponseEntity.ok(new ArrayList<>());
    }


    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteSubCategory(@PathVariable("id") int id) {
        subCategoryService.deleteSubCategory(id);
        return ResponseEntity.ok("subcategory removed successfully!");
    }

    @PostMapping("/getById/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getById(@PathVariable("id") int id) {
        return ResponseEntity.ok(subCategoryService.getById(id));
    }


}
