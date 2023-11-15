package com.bidding.biddingapp.util;

import com.bidding.commons.dto.ProductResponseDto;
import com.bidding.commons.exception.NoDataFoundException;
import com.mindstix.web.rest.baseline.common.model.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@Slf4j
public class AuctionServiceUtil {
    @Autowired
    RestTemplate restTemplate;

    @Value("${vendor.url}")
    String  vendorUrl;

    @Value("${user.url}")
    String userUrl;

    public ProductResponseDto getProductDtlsByName(String productName){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity request = new HttpEntity(headers);

        String url = vendorUrl + "product/" + productName;

        ResponseEntity<ApiResponse<ProductResponseDto>> response = null;
        try{
            log.info("Inside try");
             response = restTemplate.exchange(url, HttpMethod.GET, request,
               new ParameterizedTypeReference<ApiResponse<ProductResponseDto>>() {});


        } catch (HttpStatusCodeException e){
            log.info(" Error Response from Http call " + e.getResponseBodyAsString());
            throw new NoDataFoundException(e.getResponseBodyAsString());
        }catch(Exception e){
            log.info(" Error Response from Http call " + e.toString());

        }
        System.out.println("Response:"+response);

        if (response.getBody() != null) {
            ProductResponseDto p=(ProductResponseDto) response.getBody().getData();
            return p;
        }

         throw new RuntimeException("Exception occured while getting product details by name ->"+productName);

    }

    public List<ProductResponseDto> getProductDtlsByCategoty(String categoryName){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity request = new HttpEntity(headers);

        String url = vendorUrl + "products/" + categoryName;

        ResponseEntity<ApiResponse<List<ProductResponseDto>>> response = null;
        try {
            response = restTemplate.exchange(url, HttpMethod.GET, request,
                    new ParameterizedTypeReference<ApiResponse<List<ProductResponseDto>>>() {
                    });
        }catch (HttpStatusCodeException e){
            log.info(" Error Response from Http call " + e.getResponseBodyAsString());
            throw new NoDataFoundException(e.getResponseBodyAsString());
        }catch(Exception e){
            log.info(" Error Response from Http call " + e.toString());

        }
        System.out.println("Response:"+response);

        if(response.getBody()!=null){
            List<ProductResponseDto> productResponseDtoList = response.getBody().getData();
            return productResponseDtoList;
        }
        throw new RuntimeException("Exception occured while getting product details by category ->"+ categoryName);

    }


    public ProductResponseDto getProductDtlsById(Integer productId){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity request = new HttpEntity(headers);

        String url = vendorUrl + "show-products/" + productId;

        ResponseEntity<ApiResponse<ProductResponseDto>> response = null;
        try {
            response = restTemplate.exchange(url, HttpMethod.GET, request,
                    new ParameterizedTypeReference<ApiResponse<ProductResponseDto>>() {
                    });
        }catch (HttpStatusCodeException e){
            log.info(" Error Response from Http call " + e.getResponseBodyAsString());
            throw new NoDataFoundException(e.getResponseBodyAsString());
        }catch(Exception e){
            log.info(" Error Response from Http call " + e.toString());

        }

        System.out.println("Response:"+response);
        if(response.getBody() != null){
            ProductResponseDto p=(ProductResponseDto) response.getBody().getData();
            return p;
        }
        throw new RuntimeException("Exception occured while getting product details by name ->"+productId);
    }

    public String getUserDtls(Integer userId){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity request = new HttpEntity(headers);

        String url = userUrl + "user-app/id/" + userId;

        ResponseEntity<ApiResponse<String>> response = null;
        try {
            response = restTemplate.exchange(url, HttpMethod.GET, request, new ParameterizedTypeReference<ApiResponse<String>>() {});
        }catch (HttpStatusCodeException e){
            log.info(" Error Response from Http call " + e.getResponseBodyAsString());
            throw new NoDataFoundException(e.getResponseBodyAsString());
        }catch(Exception e){
            log.info(" Error Response from Http call " + e.toString());

        }
        System.out.println("Response:"+response);
        if(response.getBody() != null){
            String email=(String) response.getBody().getData();
            return email;
        }
        throw new RuntimeException("Exception occured while getting user details by id ->"+userId);
    }


}
