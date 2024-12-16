package base.backend.Base.Project.models;

import base.backend.Base.Project.models.dto.OrderDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    public Order(OrderDTO orderDTO) {
        this.orderId = orderDTO.getOrderId();
        this.userId = orderDTO.getUserId();
        this.orderDate = orderDTO.getOrderDate();
        this.totalPrice = orderDTO.getTotalPrice();
        this.orderDetails = orderDTO.getOrderDetails();
        this.status = orderDTO.getStatus();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer orderId;
    @Column(name = "user_id")
    private Integer userId;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
    private User user;
    @Column(name = "order_date")
    private LocalDateTime orderDate;
    private String orderDetails;
    @Column(name = "total_price")
    private BigDecimal totalPrice;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public enum OrderStatus {
        OPENED, DELIVERY, CONFIRMED, CANCELLED, PREORDER, CART
    }
}