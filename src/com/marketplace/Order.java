package com.marketplace;

import java.util.Objects;

/**
 *  Order details
 */
public class Order {

    private String userId;
    private Double quantity;
    private Long price;
    private OrderType orderType;

    /**
     * @param userId
     * @param quantity
     * @param price
     * @param orderType
     */
    public Order(String userId, Double quantity, Long price, OrderType orderType) {
        setUserId(userId);
        setQuantity(quantity);
        setPrice(price);
        setOrderType(orderType);
    }

    public String getUserId() {

        return userId;
    }

    private void setUserId(String userId) {

        this.userId = userId;
    }

    public Double getQuantity() {

        return quantity;
    }

    private void setQuantity(Double quantity) {

        this.quantity = quantity;
    }

    public Long getPrice() {

        return price;
    }

    private void setPrice(Long price) {

        this.price = price;
    }

    public OrderType getOrderType() {

        return orderType;
    }

    private void setOrderType(OrderType orderType) {

        this.orderType = orderType;
    }

    /**
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return userId.equals(order.userId) &&
                quantity.equals(order.quantity) &&
                price.equals(order.price) &&
                orderType == order.orderType;
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(userId, quantity, price, orderType);
    }

}
