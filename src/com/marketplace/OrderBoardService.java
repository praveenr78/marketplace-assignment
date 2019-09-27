package com.marketplace;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;

/**
 * Live order board service
 */
public class OrderBoardService {

    /**
     *  Register order
     *
     * @param userId
     * @param quantity
     * @param price
     * @param orderType
     * @return
     */
    public boolean registerOrder(String userId, Double quantity, Long price, OrderType orderType) {
        return addOrder(new Order(userId, quantity, price, orderType));
    }

    /**
     *  Cancel order
     *
     * @param userId
     * @param quantity
     * @param price
     * @param orderType
     * @return
     */
    public boolean cancelOrder(String userId, Double quantity, Long price, OrderType orderType) {
        return removeOrder(new Order(userId, quantity, price, orderType));
    }

    /**
     * live order board listed with same price merged. SELL orders listed starting with lowest price
     *  and listed in reverse for 'BUY'
     *
     * @return
     */
    public List<String> getOrderBoard() {

        // Partition by order type
        Map<Boolean, Map<Long, Double>> partitionList =
                getOrderList()
                        .stream()
                        .parallel()
                .collect(partitioningBy(order -> order.getOrderType().equals(OrderType.SELL),
                        groupingBy(Order::getPrice, summingDouble(Order::getQuantity))));

        //Sort partition and join
        return Stream.concat(
                //SELL
                partitionList.get(true)
                        .entrySet()
                        .stream()
                        .sorted(Map.Entry.<Long, Double>comparingByKey()),
                //BUY
                partitionList.get(false)
                        .entrySet()
                        .stream()
                        .sorted(Map.Entry.<Long, Double>comparingByKey().reversed())

        ).parallel()
                .map(v -> String.format("%.2f kg for £%d", v.getValue(), v.getKey())).collect(toList());


    }


    // In memory order stores
    private List<Order> orderList = new CopyOnWriteArrayList<>();

    /**
     *
     * @return
     */
    public List<Order> getOrderList() {
        return orderList;
    }

    /**
     * Add a new order
     *
     * @param order
     * @return
     */
    private boolean addOrder(Order order) {
        return getOrderList().add(order);

    }

    /**
     * remove a order
     *
     * @param order
     * @return
     */
    private boolean removeOrder(Order order) {

        return getOrderList().remove(order);
    }
}
