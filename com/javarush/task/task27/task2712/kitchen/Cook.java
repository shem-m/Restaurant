package com.javarush.task.task27.task2712.kitchen;

import com.javarush.task.task27.task2712.ConsoleHelper;
import com.javarush.task.task27.task2712.statistic.StatisticManager;
import com.javarush.task.task27.task2712.statistic.event.CookedOrderEventDataRow;

import java.util.Observable;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Наблюдает за планшетом в ожидании нового заказа,
 * готовит заказ какое то время и отмечает его приготовленным,
 * после чего официант относит заказ.
 */
public class Cook extends Observable implements Runnable {
    private String name;
    private boolean busy;
    private LinkedBlockingQueue<Order> queue = new LinkedBlockingQueue<>();

    public Cook(String name) {
        this.name = name;
    }

    public void setQueue(LinkedBlockingQueue<Order> queue) {
        this.queue = queue;
    }

    public boolean isBusy() {
        return busy;
    }

    @Override
    public String toString() {
        return name;
    }

    public void startCookingOrder(Order order) {
        busy = true;
        ConsoleHelper.writeMessage("Start cooking - "
                + order
                + ",  cooking time "
                + order.getTotalCookingTime()
                + "min");

        try {
            Thread.sleep(order.getTotalCookingTime() * 10); //Имитируем задержку при приготовлении блюда в 10-кратном размере от времени приготовления заказа
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        setChanged();
        notifyObservers(order); //уведомляем наблюдателей(официанта) о готовности заказа

        StatisticManager.getInstance().register(new CookedOrderEventDataRow( //регистрируем событие для статистики
                order.getTablet().toString(),
                this.name,
                order.getTotalCookingTime() * 60,
                order.getDishes()));

        busy = false;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            Thread.currentThread().interrupt();
            if (!queue.isEmpty()) {
                if (!this.isBusy()) {
                    try {
                        Order order = queue.poll();
                        if (order != null) {
                            startCookingOrder(order);
                        }
                    } catch (Exception e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
            if (queue.isEmpty()) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
