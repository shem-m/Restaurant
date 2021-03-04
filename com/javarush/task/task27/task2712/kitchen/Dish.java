package com.javarush.task.task27.task2712.kitchen;

import java.util.Arrays;

/**
 * Класс содержит список доступных для заказа блюд.
 */

public enum Dish {
    Fish(25),
    Steak(30),
    Soup(15),
    Juice(5),
    Water(3);

    private int duration;

    Dish(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public static String allDishesToString() {
        return Arrays.toString(Dish.values());
    }

    /**
     * Метод проверяет содержится ли переданное блюдо в перечне всех блюд.
     */
    public static boolean contains(String dishName) {
        for (Dish dish : Dish.values()) {
            if (dish.toString().equals(dishName)) {
                return true;
            }
        }
        return false;
    }
}
