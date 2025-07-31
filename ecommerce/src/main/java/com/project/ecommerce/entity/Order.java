package com.project.ecommerce.entity;

import com.project.ecommerce.dto.CartItemsDto;
import com.project.ecommerce.dto.OrderDto;
import com.project.ecommerce.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderDescription;

    private Date date;

    private String address;

    private String payment;

    private OrderStatus orderStatus;

    private Long totalAmount;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="user_id",referencedColumnName = "id")
    private User user;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "order")
    private List<CartItems> cartItems;

    public OrderDto getOrderDto(){
        OrderDto orderDto=new OrderDto();
        orderDto.setId(id);
        orderDto.setOrderStatus(orderStatus);
        orderDto.setOrderDescription(orderDescription);
       // orderDto.setCartItems(cartItems);
        orderDto.setTotalAmount(totalAmount);
        orderDto.setDate(date);
        orderDto.setAddress(address);
        orderDto.setPayment(payment);
        orderDto.setUserName(user.getName());


        return orderDto;
    }
}
