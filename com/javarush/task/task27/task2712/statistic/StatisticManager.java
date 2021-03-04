package com.javarush.task.task27.task2712.statistic;

import com.javarush.task.task27.task2712.statistic.event.CookedOrderEventDataRow;
import com.javarush.task.task27.task2712.statistic.event.EventDataRow;
import com.javarush.task.task27.task2712.statistic.event.EventType;
import com.javarush.task.task27.task2712.statistic.event.VideoSelectedEventDataRow;

import java.util.*;

/**
 * Класс регистрирует события в хранилище.
 */

public class StatisticManager {
    private static StatisticManager statisticManager;
    private StatisticStorage statisticStorage = new StatisticStorage();

    private StatisticManager() {
    }

    public static StatisticManager getInstance() {
        if (statisticManager == null) {
            statisticManager = new StatisticManager();
        }
        return statisticManager;
    }

    public void register(EventDataRow data) {
        statisticStorage.put(data);
    }

    public Map<Date, Double> getAdvertisementProfitBuDate() {
        List<EventDataRow> videos = statisticStorage.getStorage().get(EventType.SELECTED_VIDEOS);
        Map<Date, Double> profitAmountByDays = new TreeMap<>(Collections.reverseOrder());

        for (EventDataRow adVideo : videos) {
            VideoSelectedEventDataRow videoSelected = ((VideoSelectedEventDataRow) adVideo);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(videoSelected.getDate());
            GregorianCalendar gregorianCalendar = new GregorianCalendar(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH));

            Date date = gregorianCalendar.getTime();

            double amount = (double) videoSelected.getAmount() / 100.0;
            if (!profitAmountByDays.containsKey(date)) {
                profitAmountByDays.put(date, amount);
            } else {
                double sum = profitAmountByDays.get(date) + amount;
                profitAmountByDays.put(date, sum);
            }

        }

        return profitAmountByDays;
    }

    public Map<Date, Map<String, Integer>> getCookWorkloading() {

        List<EventDataRow> orders = statisticStorage.getStorage().get(EventType.COOKED_ORDER);
        Map<Date, Map<String, Integer>> cookWorkloadingMap = new TreeMap<>(Collections.reverseOrder());

        for (EventDataRow order : orders) { //перебираем заказы
            CookedOrderEventDataRow cookedOrder = (CookedOrderEventDataRow) order;
            String cookName = cookedOrder.getCookName();

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(cookedOrder.getDate());
            GregorianCalendar gregorianCalendar = new GregorianCalendar(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH));

            Date date = gregorianCalendar.getTime();
            int cookingTime = cookedOrder.getTime() / 60;

            if (!cookWorkloadingMap.containsKey(date)) {
                Map<String, Integer> treeMap = new TreeMap<>();
                treeMap.put(cookName, cookingTime);
                cookWorkloadingMap.put(date, treeMap);

            } else if (cookWorkloadingMap.containsKey(date)) {
                if (!cookWorkloadingMap.get(date).containsKey(cookName)) {
                    cookWorkloadingMap.get(date).put(cookName, cookingTime);

                } else {
                    int totalCookingTime = cookWorkloadingMap.get(date).get(cookName) + cookingTime;
                    cookWorkloadingMap.get(date).put(cookName, totalCookingTime);
                }
            }
        }
        return cookWorkloadingMap;
    }

    /**
     * Хранилище связано 1 к 1 с менеджером, т.е. один менеджер и одно хранилище на приложение.
     * Доступ к хранилищиу имеет только StatisticManager.
     */
    private class StatisticStorage {
        private Map<EventType, List<EventDataRow>> storage = new HashMap<>();

        public StatisticStorage() {
            for (EventType eventType : EventType.values()) {
                storage.put(eventType, new ArrayList<>());
            }
        }

        public Map<EventType, List<EventDataRow>> getStorage() {
            return storage;
        }

        private void put(EventDataRow data) {
            storage.get(data.getType()).add(data);

        }
    }

}
