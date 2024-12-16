package base.backend.Base.Project.services;

import base.backend.Base.Project.models.Order;
import base.backend.Base.Project.models.dto.OrderDTO;
import base.backend.Base.Project.repositories.OrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Integer orderId) {
        return orderRepository.findById(orderId);
    }

    public List<Order> getOrdersByUserId(Integer userId) {
        return orderRepository.findByUserId(userId);
    }

    public Order createOrder(OrderDTO orderDTO) {
        Order order = new Order(orderDTO);
        return orderRepository.save(order);
    }

    public Order updateOrder(Integer orderId, Map<String, Object> updates) {
        Order existingOrder = orderRepository.findById(orderId)
                .orElseThrow(this::orderNotFound);

        if (updates.containsKey("orderDate")) {
            existingOrder.setOrderDate((LocalDateTime) updates.get("orderDate"));
        }

        if (updates.containsKey("orderDetails")) {
            existingOrder.setOrderDetails((String) updates.get("orderDetails"));
        }

        if (updates.containsKey("totalPrice")) {
            existingOrder.setTotalPrice((BigDecimal) updates.get("totalPrice"));
        }

        if (updates.containsKey("status")) {
            existingOrder.setStatus(Order.OrderStatus.valueOf((String) updates.get("status")));
        }

        return orderRepository.save(existingOrder);
    }

    public void deleteOrder(Integer orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(this::orderNotFound);
        orderRepository.delete(order);
    }

    private ResponseStatusException orderNotFound() {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");
    }
}
