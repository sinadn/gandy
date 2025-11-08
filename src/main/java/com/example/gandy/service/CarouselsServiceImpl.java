package com.example.gandy.service;

import com.example.gandy.entity.Article;
import com.example.gandy.entity.Carousels;
import com.example.gandy.repo.ArticleRepository;
import com.example.gandy.repo.CarouselsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarouselsServiceImpl {
    @Autowired
    CarouselsRepository carouselsRepository;

    public void create(Carousels carousels) {
        carouselsRepository.save(carousels);
    }


    public void update(Carousels carousels) {
        Carousels carousels1 = new Carousels();
        try {
            carousels1 = carouselsRepository.findCarouselsById(carousels.getId());
//            carousels1.setOption1(carousels.getOption1());
//            carousels1.setOption2(carousels.getOption2());
//            carousels1.setOption3(carousels.getOption3());
            carousels1.setMain_Option(carousels.getMain_Option());
            carousels1.setRow_num(carousels.getRow_num());
            carousels1.setEn_name(carousels.getEn_name());
            carousels1.setPer_name(carousels.getPer_name());
            carousels1.setHide(carousels.getHide());
            carouselsRepository.save(carousels1);
        }catch (Exception e){
            e.getMessage();
        }
    }

//    public List<Carousels> searchCarouselByProductTypeName(String name) {
//        return  carouselsRepository.getCarouselsByName(name);
//    }

    public List<Carousels> getAll() {
        return  carouselsRepository.findAll();
    }

    public void deleteObjects(int id) {
        carouselsRepository.deleteById(id);
    }


}
