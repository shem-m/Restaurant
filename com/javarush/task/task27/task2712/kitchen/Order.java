package com.javarush.task.task27.task2712.kitchen;

import com.javarush.task.task27.task2712.ConsoleHelper;
import com.javarush.task.task27.task2712.Tablet;

import java.io.IOException;
import java.util.List;

/**
 * Класс содержит информацию о списке выбранных пользователем блюд,
 * а также о планшете с которого был сделан заказ.
 */

public class Order {
    private final Tablet tablet;
    protected List<Dish> dishes;

    public Order(Tablet tablet) throws IOException {
        this.tablet = tablet;
        initDishes();
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public Tablet getTablet() {
        return tablet;
    }

    public int getTotalCookingTime() {
        return dishes.stream().mapToInt(Dish::getDuration).sum();
    }

    @Override
    public String toString() {
        if (dishes.isEmpty()) {
            return "";
        }
        return String.format("Your order: %s of %s", dishes.toString(), tablet);
    }

    public boolean isEmpty() {
        return dishes.isEmpty();
    }

    protected void initDishes() throws IOException {
        dishes = ConsoleHelper.getAllDishesForOrder();
    }

}
