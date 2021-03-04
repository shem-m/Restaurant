package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.kitchen.Dish;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс предназначен для работы с консолью.
 */

public class ConsoleHelper {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    private ConsoleHelper() {
    }

    /**
     * Выводит строку в консоль
     */
    public static void writeMessage(String message) {
        System.out.println(message);
    }

    /**
     * Считывает строку
     */
    public static String readString() throws IOException {
        return reader.readLine();
    }

    /**
     * Просит пользователя выбрать блюдо и добавляет его в список
     * Возвращает списко выбранных блюд
     */
    public static List<Dish> getAllDishesForOrder() throws IOException {
        List<Dish> dishesForOrder = new ArrayList<>();

        Dish.allDishesToString();
        writeMessage("Select a dish:");

        String dishName;
        while (!(dishName = readString()).equals("exit")) {
            if (Dish.contains(dishName)) {
                dishesForOrder.add(Dish.valueOf(dishName));
                ConsoleHelper.writeMessage("The dish was added to the order");
            } else {
                writeMessage("This dish is not on the menu.");
            }
        }
        return dishesForOrder;
    }
}
