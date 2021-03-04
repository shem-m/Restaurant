package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.ad.AdvertisementManager;
import com.javarush.task.task27.task2712.ad.NoVideoAvailableException;
import com.javarush.task.task27.task2712.kitchen.Order;
import com.javarush.task.task27.task2712.kitchen.TestOrder;
import com.javarush.task.task27.task2712.statistic.StatisticManager;
import com.javarush.task.task27.task2712.statistic.event.NoAvailableVideoEventDataRow;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * На каждом столике лежит планшет, через который можно сделатть заказ.
 * Пока заказ готовится, на планшете показывается реклама.
 * В конце рабочего дня можно посмотреть статистику по:
 * -загруженности повара;
 * -сумме выручки за заказы;
 * -сумме выручки за показы рекламы.
 */
public class Tablet {
    private final int number;
    private static Logger logger = Logger.getLogger(Tablet.class.getName()); //Позволяет узнать причину возможного исключения.
    private LinkedBlockingQueue<Order> queue;

    public Tablet(int number) {
        this.number = number;
//        setQueue(queue);
    }


    public void setQueue(LinkedBlockingQueue<Order> queue) {
        this.queue = queue;
    }

    public Order createOrder() {
        Order order = null;
        try {
            order = new Order(this);
            startAdAndAddOrder(order);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Console is unavailable.");
        }
        return order;
    }

    public void createTestOrder() {
        TestOrder testOrder;
        try {
            testOrder = new TestOrder(this);
            startAdAndAddOrder(testOrder);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Console is unavailable.");
        }
    }

    private void startAdAndAddOrder(Order order) {
        if (!order.isEmpty()) { //Если заказ не пустой, он отправляется повару
            try {
                AdvertisementManager manager = new AdvertisementManager(
                        order.getTotalCookingTime() * 60); //Создаем обработчика рекламы, задаем время приготовления заказа в секундах
                manager.processVideos();
            } catch (NoVideoAvailableException e) {
                logger.log(Level.INFO, ("No video is available for the order " + order));
                StatisticManager.getInstance().register(new NoAvailableVideoEventDataRow(order.getTotalCookingTime() * 60));
            }

            ConsoleHelper.writeMessage(order.toString());
            try {
                queue.put(order);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String toString() {
        return "Tablet{" +
                "number=" + number +
                '}';
    }
}
