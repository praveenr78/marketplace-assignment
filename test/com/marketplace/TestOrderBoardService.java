package com.marketplace;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;


public class TestOrderBoardService {

    public OrderBoardService testOrderBoardService;

    @Before
    public void startUp() {
        testOrderBoardService = new OrderBoardService();
    }

    @Test
    public void testNoOrdersHasEmptyBoard() {

        assertTrue(testOrderBoardService.getOrderBoard().isEmpty());

    }

    @Test
    public void testRegisterOrderUpdatesBoard() {
        testOrderBoardService.registerOrder("user1", 1.5, 315l, OrderType.BUY);
        assertTrue(!testOrderBoardService.getOrderBoard().isEmpty());
        assertEquals(1, testOrderBoardService.getOrderBoard().size());
    }

    @Test
    public void testCancelOrderUpdatesBoard() {

        registerBuyOrders();
        assertEquals(4, testOrderBoardService.getOrderBoard().size());
        testOrderBoardService.cancelOrder("user1", 1.5, 315l, OrderType.BUY);
        assertEquals(3, testOrderBoardService.getOrderBoard().size());
    }

    @Test
    public void testSellSummarySamePricesMerged() {

        testOrderBoardService.registerOrder("user1", 1.5, 300l, OrderType.SELL);
        testOrderBoardService.registerOrder("user2", 3.5, 300l, OrderType.SELL);
        testOrderBoardService.registerOrder("user3", 1.5, 312l, OrderType.SELL);

        List<String> result = testOrderBoardService.getOrderBoard();

        assertTrue(!result.isEmpty());
        assertEquals(2, result.size());
        assertTrue(result.contains("5.00 kg for £300"));

    }

    @Test
    public void testBuySummarySamePricesMerged() {
        testOrderBoardService.registerOrder("user1", 1.5, 300l, OrderType.BUY);
        testOrderBoardService.registerOrder("user2", 3.5, 300l, OrderType.BUY);
        testOrderBoardService.registerOrder("user3", 1.5, 312l, OrderType.BUY);

        List<String> result = testOrderBoardService.getOrderBoard();

        assertTrue(!result.isEmpty());
        assertEquals(2, result.size());
        assertTrue(result.contains("5.00 kg for £300"));

    }

    @Test
    public void testSellSummaryHasLowestPriceFirst() {

        testOrderBoardService.registerOrder("user1", 1.5, 400l, OrderType.SELL);
        testOrderBoardService.registerOrder("user2", 1.5, 400l, OrderType.SELL);
        testOrderBoardService.registerOrder("user3", 1.5, 312l, OrderType.SELL);

        List<String> result = testOrderBoardService.getOrderBoard();

        assertTrue(!result.isEmpty());
        assertEquals(2, result.size());
        assertEquals("1.50 kg for £312", result.get(0));

    }

    @Test
    public void testBuySummaryHasHighestPriceFirst() {

        testOrderBoardService.registerOrder("user1", 1.5, 400l, OrderType.BUY);
        testOrderBoardService.registerOrder("user2", 1.5, 400l, OrderType.BUY);
        testOrderBoardService.registerOrder("user3", 1.5, 312l, OrderType.BUY);

        List<String> result = testOrderBoardService.getOrderBoard();

        assertTrue(!result.isEmpty());
        assertEquals(2, result.size());
        assertEquals("3.00 kg for £400", result.get(0));

    }

    /**
     *
     */
    private void registerBuyOrders() {

        testOrderBoardService.registerOrder("user1", 1.5, 315l, OrderType.BUY);
        testOrderBoardService.registerOrder("user2", 7.2, 300l, OrderType.BUY);
        testOrderBoardService.registerOrder("user3", 1.5, 312l, OrderType.BUY);
        testOrderBoardService.registerOrder("user4", 5.0, 305l, OrderType.BUY);
    }


    @After
    public void tearDown() {
        testOrderBoardService = null;
    }

}
