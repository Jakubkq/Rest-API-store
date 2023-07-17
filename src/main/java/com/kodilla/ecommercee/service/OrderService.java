package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.domain.Order;
import com.kodilla.ecommercee.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
   private final OrderRepository orderRepository;

  public List<Order> findOrders (){
  return orderRepository.findAll();
  }

  public Order findOrder(Long id){
      return orderRepository.findById(id).orElse(null);
  }

  public void deleteOrder (Long id){
      orderRepository.deleteById(id);
  }

  public Order saveOrder (final Order order){
      return orderRepository.save(order);
  }


}
