package com.example.gandy.service.slider;

import com.example.gandy.entity.FooterMenu;
import com.example.gandy.entity.Slider;
import com.example.gandy.repo.FooterMenuRepository;
import com.example.gandy.repo.SliderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class SliderServiceImpl {
    @Autowired
    private SliderRepository repository;


    public void addObject(Slider object) {
        repository.save(object);
    }

    public void updateObject(Slider object) {
        repository.save(object);
    }


    public Slider getByIdObject(Long id) {
        return repository.findSliderById(id);
    }

    public List<Slider> getAllObjects() {
        return repository.findAll();
    }

    public void deleteByIdObject(Long id) {
        repository.deleteById(id);
    }

    public void deleteSliderByImgName(String name) {
        Slider slider = repository.findSliderByImage(name);
        slider.setImage("");
        addObject(slider);
//        repository.deleteSliderByImage(name);
    }

}
