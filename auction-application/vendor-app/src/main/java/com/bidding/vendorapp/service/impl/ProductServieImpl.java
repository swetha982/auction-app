package com.bidding.vendorapp.service.impl;

import com.bidding.commons.exception.DuplicateException;
import com.bidding.commons.exception.NoDataFoundException;
import com.bidding.vendorapp.model.Vendor;
import com.bidding.vendorapp.dao.ProductRepository;
import com.bidding.vendorapp.dto.ProductDto;
import com.bidding.vendorapp.model.Category;
import com.bidding.vendorapp.model.Product;
import com.bidding.vendorapp.service.CategoryService;
import com.bidding.vendorapp.service.ProductService;
import com.bidding.vendorapp.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.bidding.commons.dto.ProductResponseDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;

@Service
public class ProductServieImpl implements ProductService {

    @Autowired
    CategoryService categoryService;

    @Autowired
    VendorService vendorService;

    @Autowired
    ProductRepository productRepository;

    @Override
    @Transactional
    public String create(ProductDto product) {
        if(!validate(product.getName())) {
            if (vendorService.validateVendor(product.getVendorEmail())) {

                if (categoryService.validate(product.getCategory())) {
                    Product newProduct = new Product(product.getName(), product.getDescription(), product.getImageUrl());
                    Category category = categoryService.findCategory(product.getCategory());
                    newProduct.setCategory(category);
                    Vendor vendor = vendorService.findVendor(product.getVendorEmail());
                    newProduct.setVendor(vendor);
                    return (productRepository.save(newProduct)) != null ? "Product Created Successfully" : "Product creation Failed";

                } else {
                    throw new NoDataFoundException("Category should be registered");
                }

            } else {
                throw new NoDataFoundException("Registration of Vendor is required");
            }

        }
        throw new DuplicateException("Product with the given name is already available ->"+ product.getName());

    }

    public boolean validate(String productName){
        Optional<Integer> productId = productRepository.checkForProduct(productName.toUpperCase());
        return productId.isPresent();
    }

    public List<Product> showByCategory(String categoryName){
        List<Product> productList = new ArrayList<>();
        if(categoryService.validate(categoryName)){
            Category c = categoryService.findCategory(categoryName);
            productList=productRepository.findByCategory(c.getCategoryId());
            if(productList.isEmpty()){
                throw new NoDataFoundException("No product is available under this category ->" + categoryName);
            }
            return productList;

        }
        throw new NoDataFoundException("This category is not configured ->" + categoryName);
    }

    public List<ProductResponseDto> convertProductToResponse(List<Product> products){
        ProductResponseDto response;
        List<ProductResponseDto> responseList = new ArrayList<>();
        for(Product p:products){
            response=new ProductResponseDto(p.getProductId(),p.getName(),p.getDescription(),p.getImageUrl(),p.getCategory().getName(),p.getVendor().getName());
            responseList.add(response);
        }
        return responseList;
    }

    @Override
    public List<Product> showProducts() {
        List<Product> productList = new ArrayList<>();
        productList= (List<Product>) productRepository.findAll();
        if(productList.isEmpty())
            throw new NoDataFoundException("No product is available");
        else
            return productList;
    }

    public ProductResponseDto getProduct(String productName, Integer productId){
        //return productRepository.findByNameIgnoreCase(productName).get();
        System.out.println("In p");
        String productNameToUpper = productName;
        if(productName!=null){
             productNameToUpper = productName.toUpperCase();
        }else
            productName=null;
        String res[] = productRepository.findProductByName(productNameToUpper,productId).get();
        System.out.println("MMMMMMM:" + res.length);


        if(res.length > 0) {

            System.out.println("NNNNNNNN:" + res[0]);
            StringTokenizer st = new StringTokenizer(res[0], ",");
            String[] op = new String[6];
            //String id = (String) object[0];
            //System.out.println("MMMMMMM:"+id);
        /*String name = (String)(res[0]);
        String description = String.valueOf(res[1]);
        String imageUrl = String.valueOf(res[2]);
        String categoryName = String.valueOf(res[3]);
        String vendorName = String.valueOf(res[4]);*/
            int i = 0;
            while (st.hasMoreTokens()) {

                op[i] = st.nextToken();
                System.out.println("op " + i + " " + op[i]);
                i++;
            }
            int id = Integer.parseInt(op[0]);
            System.out.println("id: " + id);
            ProductResponseDto product = new ProductResponseDto(id, op[1], op[2], op[3], op[4], op[5]);
            return product;
        }
        throw  (productName == null)?
                new NoDataFoundException("No product available for given id ->"+ productId):
                new NoDataFoundException("No product available for given name ->"+ productName);

    }
}
