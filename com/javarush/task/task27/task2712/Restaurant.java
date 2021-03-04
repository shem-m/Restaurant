package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.kitchen.Cook;
import com.javarush.task.task27.task2712.kitchen.Order;
import com.javarush.task.task27.task2712.kitchen.Waiter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class Restaurant {
    private static final int ORDER_CREATING_INTERVAL = 100;
    private final static LinkedBlockingQueue<Order> orderQueue = new LinkedBlockingQueue<>();

    public static void main(String[] args) throws IOException {
        Waiter waiter = new Waiter();

        Cook cook = new Cook("Amigo");
        cook.addObserver(waiter);
        cook.setQueue(orderQueue);
        Cook cook2 = new Cook("Santiago");
        cook2.setQueue(orderQueue);
        cook2.addObserver(waiter);

        Thread cookThread = new Thread(cook);
        cookThread.setDaemon(true);
        cookThread.start();
        Thread cook2Thread = new Thread(cook2);
        cook2Thread.setDaemon(true);
        cook2Thread.start();

        List<Tablet> tablets = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            Tablet tablet = new Tablet(i);
            tablet.setQueue(orderQueue);
            tablets.add(tablet);
        }

        Thread thread = new Thread(new RandomOrderGeneratorTask(tablets, ORDER_CREATING_INTERVAL)); //генерируем случайные заказы
        thread.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.interrupt();

        DirectorTablet directorTablet = new DirectorTablet(); //статистика по заказам
        directorTablet.printAdvertisementProfit();
        directorTablet.printCookWorkloading();
        directorTablet.printActiveVideoSet();
        directorTablet.printArchivedVideoSet();

    }
}
