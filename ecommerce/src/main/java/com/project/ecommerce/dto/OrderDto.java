package com.project.ecommerce.dto;

import com.project.ecommerce.entity.CartItems;
import com.project.ecommerce.entity.User;
import com.project.ecommerce.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderDto {

         private Long id;

        private String orderDescription;

        private Date date;

        private String address;

        private String payment;

        private OrderStatus orderStatus;

        private Long totalAmount;

        private String userName;

        private List<CartItemsDto> cartItems;




}
