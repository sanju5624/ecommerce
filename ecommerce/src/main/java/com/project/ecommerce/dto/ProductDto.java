package com.project.ecommerce.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
@Data
public class ProductDto {

    private Long id;

    private String name;

    private Long price;

    private String description;

     private byte[] byteimg;

     private Long categoryId;

    private String categoryName;


    private MultipartFile img;


}
