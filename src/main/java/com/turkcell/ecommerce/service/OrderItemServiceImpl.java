package com.turkcell.ecommerce.service;

import com.turkcell.ecommerce.dto.orderitem.CreateOrderItemDto;
import com.turkcell.ecommerce.dto.orderitem.OrderItemListiningDto;
import com.turkcell.ecommerce.dto.orderitem.UpdateOrderItemDto;
import com.turkcell.ecommerce.entity.Order;
import com.turkcell.ecommerce.entity.OrderItem;
import com.turkcell.ecommerce.entity.Product;
import com.turkcell.ecommerce.repository.OrderItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService{

    private final ProductService productService;
    private final OrderService orderService;
    private final OrderItemRepository orderItemRepository;

    public OrderItemServiceImpl(ProductService productService, OrderService orderService, OrderItemRepository orderItemRepository) {
        this.productService = productService;
        this.orderService = orderService;
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public void add(CreateOrderItemDto createOrderItemDto) {

        Order order=orderService.findById(createOrderItemDto.getOrderId()).orElse(null);
        Product product=productService.findById(createOrderItemDto.getProductId()).orElse(null);


        OrderItem orderItem = new OrderItem();
        orderItem.setStatus(createOrderItemDto.getStatus());
        orderItem.setPrice(createOrderItemDto.getPrice());
        orderItem.setQuantity(createOrderItemDto.getQuantity());
        orderItem.setOrder(order);
        orderItem.setProduct(product);

    }

    @Override
    public void update(UpdateOrderItemDto updateOrderItemDto) {

        Order order=orderService.findById(updateOrderItemDto.getId()).orElse(null);
        Product product=productService.findById(updateOrderItemDto.getId()).orElse(null);

        OrderItem orderItemToUpdate = orderItemRepository.findById(updateOrderItemDto.getId()).orElse(null);
        orderItemToUpdate.setStatus(updateOrderItemDto.getStatus());
        orderItemToUpdate.setPrice(updateOrderItemDto.getPrice());
        orderItemToUpdate.setQuantity(updateOrderItemDto.getQuantity());
        orderItemToUpdate.setOrder(order);
        orderItemToUpdate.setProduct(product);
        orderItemRepository.save(orderItemToUpdate);



    }

    @Override
    public List<OrderItemListiningDto> getAll() {
        List<OrderItemListiningDto> orderItemListiningDtos = orderItemRepository
                .findAll()
                .stream()
                .map((orderItem) -> new OrderItemListiningDto(orderItem.getId(), orderItem.getStatus()))
                .toList();

        return orderItemListiningDtos;
    }
}
