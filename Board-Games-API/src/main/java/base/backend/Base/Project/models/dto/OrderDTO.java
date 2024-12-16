package base.backend.Base.Project.models.dto;

import base.backend.Base.Project.models.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    public OrderDTO(Order order) {
        this.orderId = order.getOrderId();
        this.userId = order.getUserId();
        this.orderDate = order.getOrderDate();
        this.totalPrice = order.getTotalPrice();
        this.orderDetails = order.getOrderDetails();
        this.status = order.getStatus();
    }

    private Integer orderId;
    private Integer userId;
    private LocalDateTime orderDate;
    private BigDecimal totalPrice;
    private String orderDetails;
    private Order.OrderStatus status;
}
