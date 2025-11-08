package com.example.gandy.service;

import com.example.gandy.entity.*;
import com.example.gandy.payload.request.SearchProductRequest;
import com.example.gandy.repo.AttributeOptionRepository;
import com.example.gandy.repo.ProductCountRepository;
import com.example.gandy.service.productImage.ProductImageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductCountServiceImpl {
    @Autowired
    ProductCountRepository productCountRepository;

    @Autowired
    ProductTypeServiceImpl productTypeService;

    @Autowired
    AttributeOptionRepository attributeOptionRepository;


    @Autowired
    ProductImageServiceImpl productImageService;


    public void createObjects(ProductCount objects) {
        productCountRepository.save(objects);
    }


    public void addProductCounts(List<ProductCount> productCountList, Product product) {
        List<ProductImage> list = new ArrayList<>();
        list = productImageService.findByProductId(product.getId());
        for (ProductCount x : productCountList) {
            x.setProduct(product);
            if (x.getMain()==1) {
                if (list.size() > 0) {
                    x.setProductImage(list.get(0));
                }
            }
        }
        productCountRepository.saveAll(productCountList);
    }


    public void updateProductCounts(List<ProductCount> productCountList , Product product) {
        for (ProductCount x : productCountList) {
            ProductCount productCount = new ProductCount();
            try {
                productCount = findProductCount(x.getId());
            } catch (Exception e) {
                e.getMessage();
            }

            if (productCount != null) {
                productCount.setProduct(x.getProduct());
                productCount.setCount(x.getCount());
                productCount.setColor(x.getColor());
                productCount.setColorHex(x.getColorHex());
                productCount.setMain(x.getMain());
                productCount.setPrice(x.getPrice());
                if (x.getProductImage() != null) {
                    productCount.setProductImage(x.getProductImage());
                }
                if (x.getDiscount() != null) {
                    productCount.setDiscount(x.getDiscount());
                }
                productCountRepository.save(productCount);
            } else {
                x.setProduct(product);
                productCountRepository.save(x);
            }

        }
    }


    public List<ProductCount> findProduct(long id) {
        return productCountRepository.findProduct(id);
    }

    public List<ProductCount> findByProductName(String name) {
        return productCountRepository.findByProductName(name);
    }


    public List<String> findProductColor(long id) {
        return productCountRepository.findProductColor(id);
    }


    public void deleteObjects(long id) {
        try {
            productCountRepository.deleteById(id);
        } catch (Exception e) {
            e.getMessage();
        }
    }


    public ProductCount findProductCount(long id) {
        return productCountRepository.findByProdoctCountId(id);
    }

    public List<ProductCount> findProductByBrand_Id(int id, int pageNumber) {
        Pageable paging = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "id"));
        return productCountRepository.findProductByBrand_Id(id, 1, paging);
    }


    public List<ProductCount> getByProductType(int id, int pageNumber) {
        List<Long> ptypes = new ArrayList<>();
        List<ProductCount> list = new ArrayList<>();
        if (id != 0) {
            try {
                ptypes = productTypeService.findPTfromhead(id);
            } catch (Exception e) {
                e.getMessage();
            }
        }

        Pageable paging = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "id"));
        return productCountRepository.getByProductType(1, ptypes, paging);
    }


    public List<ProductCount> advanceSearch(SearchProductRequest object, int pageNumber) {
        Pageable paging = null;

        if (object.getSortByPrice() == 1) {
            paging = PageRequest.of(pageNumber, 100, Sort.by(Sort.Direction.ASC, "price"));
            return filter(object, paging);

        } else if (object.getSortByPrice() == 2) {
            paging = PageRequest.of(pageNumber, 100, Sort.by(Sort.Order.desc("price")));
            return filter(object, paging);

        } else if (object.getSortByNewerProduct() == 1) {
            paging = PageRequest.of(pageNumber, 100, Sort.by(Sort.Direction.DESC, "id"));
            return filter(object, paging);

        } else if (object.getSortByMostDiscount() == 1) {
            paging = PageRequest.of(pageNumber, 100, Sort.by(Sort.Direction.DESC, "discount"));
            return filter(object, paging);

        } else {
            paging = PageRequest.of(pageNumber, 100, Sort.by(Sort.Direction.DESC, "id"));
            return filter(object, paging);

        }
    }


    public List<ProductCount> filter(SearchProductRequest object, Pageable paging) {
        List<Long> ptypes = new ArrayList<>();
        List<ProductCount> list = new ArrayList<>();
        if (object.getProductType() != 0) {
            try {
                ptypes = productTypeService.findPTfromhead(object.getProductType());
            } catch (Exception e) {
                e.getMessage();
            }
            list = productCountRepository.findProductCountByProductType(1, ptypes);
        }

        if (object.getTag() != 0) {
            List<ProductCount> tagList = new ArrayList<>();
            tagList = productCountRepository.findProductCountByTag(1, object.getTag(), list);
            list.clear();
            list.addAll(tagList);
        }

        if (object.getAmount() != 0) {
            List<ProductCount> amountList = new ArrayList<>();
            amountList = productCountRepository.findProductCountByPrice(1, object.getAmount(), list);
            list.clear();
            list = amountList;
        }

        if (object.getAmazingOffer() != 0) {
            List<ProductCount> amazingOfferList = new ArrayList<>();
            amazingOfferList = productCountRepository.findAmazingOffer(object.getAmazingOffer(), 1, list);
            list.clear();
            list.addAll(amazingOfferList);
        }

        if (object.getSortByAvailableProduct() != 0) {
            List<ProductCount> availableProducts = new ArrayList<>();
            availableProducts = productCountRepository.findAvailableProduct(1, list);
            list.clear();
            list.addAll(availableProducts);
        }

        if (object.getAttributeOption().size() > 0) {
            List<ProductCount> atOList = new ArrayList<>();
            List<AttributeOption> attributeOptionList = new ArrayList<>();
            attributeOptionList = attributeOptionRepository.attributeOptionList(object.getAttributeOption());
            List<AttributeOption> commonAttributeOption = new ArrayList<>();
            for (AttributeOption i : attributeOptionList) {
                for (AttributeOption j : attributeOptionList) {
                    if (i.getAttributeType().getId().equals(j.getAttributeType().getId()))
                        commonAttributeOption.add(i);
                }
                List<Long> attributeOptionSend = new ArrayList<>();
                for (int j = 0; j < attributeOptionList.size(); j++) {
                    attributeOptionSend.add(attributeOptionList.get(j).getId());
                }

                atOList = productCountRepository.findProductCountByAtO(1, attributeOptionSend, list, paging);
                if (atOList == null) {
                    return null;
                }
                list.clear();
                list.addAll(atOList);
            }
        }
        list = productCountRepository.finalResultForAdvanceSearch(1, list, paging);
        System.out.println(list);

        return list;
    }

    public List<ProductCount> findProductByProductTypeAndBrand(int ptId, int brandId) {
        Pageable paging = PageRequest.of(0, 4, Sort.by(Sort.Direction.DESC, "id"));
        return productCountRepository.findProductByProductTypeAndBrand(ptId, brandId, 1, paging);
    }

    public List<ProductCount> findAmazingOffer(int pageNumber) {
        Pageable paging = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "id"));
        return productCountRepository.findAmazingOffer(1, 1, paging);
    }

//    public List<ProductCount> availabilityCheck(List<ProductCount> list) {
//        List<ProductCount> zeroCountList= new ArrayList<>();
//        List<ProductCount> content= new ArrayList<>();
//
//        for (ProductCount pcount:list) {
//            if (pcount.getCount()==0){
//                zeroCountList.add(pcount);
//            }else {
//                content.add(pcount);
//            }
//        }
//        list.clear();
//        list.addAll(content);
//        list.addAll(zeroCountList);
//        return list;
//    }

    public List<ProductCount> findProductByWords(String name) {
        Pageable paging = PageRequest.of(0, 8, Sort.by(Sort.Direction.DESC, "id"));
        return productCountRepository.findProductByWords(name, 1, paging);
    }


    public List<ProductCount> findProductCountByWordsForAdmin(String name) {
        Pageable paging = PageRequest.of(0, 8, Sort.by(Sort.Direction.DESC, "id"));
        return productCountRepository.findProductCountByWordsForAdmin(name, paging);
    }


    public List<ProductCount> findProductByAmount(long price, long ptype, int pageNumber) {
        List<Long> ptypes = productTypeService.findPTfromhead((int) ptype);
        Pageable paging = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "id"));
        return productCountRepository.findProductByAmount(price, ptypes, 1, paging);
    }

    public List<ProductCount> findProductByTag(long tagId, long ptype, int pageNumber) {
        List<Long> ptypes = productTypeService.findPTfromhead((int) ptype);
        Pageable paging = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "id"));
        return productCountRepository.findProductByTag(1, ptypes, tagId, paging);
    }

    public List<ProductCount> findProductByAO(long attributeOption, long ptype, int pageNumber) {
        List<Long> ptypes = productTypeService.findPTfromhead((int) ptype);
        Pageable paging = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "id"));
        return productCountRepository.findProductByAO(1, ptypes, attributeOption, paging);
    }


    public List<ProductCount> findNewProduct(int pageNumber) {
        Pageable paging = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "id"));
        List<Long> ptypes = new ArrayList<>();
        ProductType productType = productTypeService.getProductTypeByName("گوشی موبایل");
        try {
            ptypes = productTypeService.findPTfromhead(productType.getId());
        } catch (Exception e) {
            e.getMessage();
        }
        return productCountRepository.findNewProduct(ptypes, 1, paging);
    }

    public List<ProductCount> findByprice(int price, int pageNumber) {
        Pageable paging = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "id"));
        return productCountRepository.findByprice(price, 1, paging);

    }

    public Page<ProductCount> getAllProductCount(int pageNumber) {
        Pageable paging = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "id"));
        return productCountRepository.findAll(paging);

    }


    public Page<ProductCount> findAll(int pageNumber) {
        Pageable paging = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "id"));
        return productCountRepository.findAll(paging);
    }


}
