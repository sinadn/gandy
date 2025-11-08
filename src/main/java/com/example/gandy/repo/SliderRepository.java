package com.example.gandy.repo;

import com.example.gandy.entity.Cover;
import com.example.gandy.entity.Slider;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface SliderRepository extends JpaRepository<Slider, Long> {


    Slider findSliderById(long id);

    Slider findSliderByImage(String name);

//    void deleteSliderByImage(String imageName);


}