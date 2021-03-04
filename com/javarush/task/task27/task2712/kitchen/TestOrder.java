package com.javarush.task.task27.task2712.kitchen;

import com.javarush.task.task27.task2712.Tablet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class TestOrder extends Order {
    public TestOrder(Tablet tablet) throws IOException {
        super(tablet);
    }

    @Override
    protected void initDishes() throws IOException {
        dishes = new ArrayList<>();
        int randomCount = ThreadLocalRandom.current().nextInt(Dish.values().length - 1);
        for (int i = 0; i < randomCount; i++) {
            int random = ThreadLocalRandom.current().nextInt(Dish.values().length - 1);
            dishes.add(Dish.values()[random]);
        }
    }
}
