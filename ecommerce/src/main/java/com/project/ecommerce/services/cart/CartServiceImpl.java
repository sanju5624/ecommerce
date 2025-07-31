package com.project.ecommerce.services.cart;

import com.project.ecommerce.dto.*;
import com.project.ecommerce.entity.CartItems;
import com.project.ecommerce.entity.Order;
import com.project.ecommerce.entity.Product;
import com.project.ecommerce.entity.User;
import com.project.ecommerce.enums.OrderStatus;
import com.project.ecommerce.repository.CartItemsRepository;
import com.project.ecommerce.repository.OrderRepository;
import com.project.ecommerce.repository.ProductRepository;
import com.project.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartItemsRepository cartItemsRepository;

    public ResponseEntity<?> addProductToCart(AddProductInCartDto addProductInCartDto){
        Order activeOrder=orderRepository.findByUserIdAndOrderStatus(addProductInCartDto.getUserId(), OrderStatus.Pending);
        if (activeOrder == null) {
            Optional<User> optionalUser = userRepository.findById(addProductInCartDto.getUserId());
            if (optionalUser.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }

            activeOrder = new Order();
            activeOrder.setUser(optionalUser.get());
            activeOrder.setOrderStatus(OrderStatus.Pending);
            activeOrder.setTotalAmount(0L);
            activeOrder.setCartItems(new ArrayList<>());
            activeOrder = orderRepository.save(activeOrder); // Save to get the ID
        }
        Optional<CartItems> optionalCartItems=cartItemsRepository.findByProductIdAndOrderIdAndUserId(
               addProductInCartDto.getProductId(),activeOrder.getId(),addProductInCartDto.getUserId()
        );
        if (optionalCartItems.isPresent()) {
            CartItems existingCartItem = optionalCartItems.get();
            existingCartItem.setQuantity(existingCartItem.getQuantity() + 1);
            existingCartItem.setPrice(existingCartItem.getQuantity() * existingCartItem.getProduct().getPrice());

            cartItemsRepository.save(existingCartItem);

            activeOrder.setTotalAmount(activeOrder.getTotalAmount() + existingCartItem.getProduct().getPrice());
            orderRepository.save(activeOrder);

            return ResponseEntity.ok(existingCartItem);
        }
        else{
            Optional<Product> optionalProduct = productRepository.findById(addProductInCartDto.getProductId());
            Optional<User> optionalUser = userRepository.findById(addProductInCartDto.getUserId());

            if (optionalProduct.isPresent() && optionalUser.isPresent()) {
                CartItems cart = new CartItems();
                cart.setProduct(optionalProduct.get());
                cart.setPrice(optionalProduct.get().getPrice());
                cart.setQuantity(1L);
                cart.setUser(optionalUser.get());
                cart.setOrder(activeOrder);

                CartItems updatedCart = cartItemsRepository.save(cart);

                activeOrder.setTotalAmount(activeOrder.getTotalAmount() + cart.getPrice());
                activeOrder.getCartItems().add(cart);

                orderRepository.save(activeOrder);

                return ResponseEntity.status(HttpStatus.CREATED).body(cart);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or product not found");
            }

        }
    }

    public OrderDto getCartByUserId(Long userId){
        Order activeOrder=orderRepository.findByUserIdAndOrderStatus(userId, OrderStatus.Pending);
        List<CartItemsDto> cartItemsDtoList=activeOrder.getCartItems().stream().map(CartItems::getCartDto).collect(Collectors.toList());
        OrderDto orderDto=new OrderDto();
        orderDto.setId(activeOrder.getId());
        orderDto.setOrderStatus(activeOrder.getOrderStatus());
        orderDto.setTotalAmount(activeOrder.getTotalAmount());
        orderDto.setCartItems(cartItemsDtoList);
        return orderDto;

    }

    public OrderDto increaseProductDto(AddProductInCartDto addProductInCartDto){
        Order activeOrder= orderRepository.findByUserIdAndOrderStatus(addProductInCartDto.getUserId(),OrderStatus.Pending);
        Optional<Product> optionalProduct=productRepository.findById(addProductInCartDto.getProductId());
        Optional<CartItems> optionalCartItem=cartItemsRepository.findByProductIdAndOrderIdAndUserId(
                addProductInCartDto.getProductId(), activeOrder.getId(), addProductInCartDto.getUserId()
        );
        if(optionalProduct.isPresent() && optionalCartItem.isPresent()){
            CartItems cartItem=optionalCartItem.get();
            Product product=optionalProduct.get();

            activeOrder.setTotalAmount(activeOrder.getTotalAmount() + product.getPrice());

            cartItem.setQuantity(cartItem.getQuantity()+1);
            cartItemsRepository.save(cartItem);
            orderRepository.save(activeOrder);
            return  activeOrder.getOrderDto();
        }
        return null;

    }

    public OrderDto decreaseProductDto(AddProductInCartDto addProductInCartDto){
        Order activeOrder= orderRepository.findByUserIdAndOrderStatus(addProductInCartDto.getUserId(),OrderStatus.Pending);
        Optional<Product> optionalProduct=productRepository.findById(addProductInCartDto.getProductId());
        Optional<CartItems> optionalCartItem=cartItemsRepository.findByProductIdAndOrderIdAndUserId(
                addProductInCartDto.getProductId(), activeOrder.getId(), addProductInCartDto.getUserId()
        );
        if(optionalProduct.isPresent() && optionalCartItem.isPresent()){
            CartItems cartItem=optionalCartItem.get();
            Product product=optionalProduct.get();

            activeOrder.setTotalAmount(activeOrder.getTotalAmount() - product.getPrice());

            cartItem.setQuantity(cartItem.getQuantity()-1);
            cartItemsRepository.save(cartItem);
            orderRepository.save(activeOrder);
            return  activeOrder.getOrderDto();
        }
        return null;

    }

    public OrderDto placeOrder(PlaceOrderDTO placeOrderDTO){
        Order activeOrder= orderRepository.findByUserIdAndOrderStatus(placeOrderDTO.getUserId(),OrderStatus.Pending);
        Optional<User> optionalUser=userRepository.findById(placeOrderDTO.getUserId());
        if(optionalUser.isPresent() ){
            activeOrder.setOrderDescription(placeOrderDTO.getOrderDescription());
            activeOrder.setAddress(placeOrderDTO.getAddress());
            activeOrder.setDate(new Date());
            activeOrder.setOrderStatus(OrderStatus.Placed);

            orderRepository.save(activeOrder);

            Order order=new Order();
            order.setTotalAmount(0L);
            order.setUser(optionalUser.get());
            order.setOrderStatus(OrderStatus.Pending);
            orderRepository.save(order);

            return  activeOrder.getOrderDto();

        }
        return null;

    }

}
