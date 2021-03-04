package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.ad.StatisticAdvertisementManager;
import com.javarush.task.task27.task2712.statistic.StatisticManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class DirectorTablet {
    private StatisticManager statisticManager = StatisticManager.getInstance();
    private StatisticAdvertisementManager statisticAdvertisementManager = StatisticAdvertisementManager.getInstance();

    public void printAdvertisementProfit() { //показывает какую сумму заработали на рекламе, сгруппированную по дням
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        double sum = 0.00;
        for (Map.Entry<Date, Double> entry : statisticManager.getAdvertisementProfitBuDate().entrySet()) {
            if (entry.getValue() > 0) {
                ConsoleHelper.writeMessage(String.format("%s - %.2f%n", dateFormat.format(entry.getKey()), entry.getValue()));
                sum += entry.getValue();
            }
        }
        ConsoleHelper.writeMessage(String.format("Total - %.2f%n", sum));
        ConsoleHelper.writeMessage("");
    }

    public void printCookWorkloading() { //показывает загрузку (рабочее время) повара, сгруппированную по дням
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);

        for (Map.Entry<Date, Map<String, Integer>> dateMapEntry : statisticManager.getCookWorkloading().entrySet()) {
            Date date = dateMapEntry.getKey();
            ConsoleHelper.writeMessage(dateFormat.format(date));

            for (Map.Entry<String, Integer> stringIntegerEntry : dateMapEntry.getValue().entrySet()) {
                String cookName = stringIntegerEntry.getKey();
                int cookingTime = stringIntegerEntry.getValue();

                ConsoleHelper.writeMessage(cookName + " - " + cookingTime + " min");
            }
            ConsoleHelper.writeMessage("");
        }
    }

    public void printActiveVideoSet() { //показывает список активных роликов и оставшееся количество показов по каждому
        for (Map.Entry<String, Integer> entry : statisticAdvertisementManager.getActiveVideoSet().entrySet()) {
            ConsoleHelper.writeMessage(entry.getKey() + " - " + entry.getValue());
        }
        ConsoleHelper.writeMessage("");
    }

    public void printArchivedVideoSet() { //показывает список неактивных роликов (с оставшемся количеством показов равным нулю)
        for (String adName : statisticAdvertisementManager.getArchivedVideoSet()) {
            ConsoleHelper.writeMessage(adName);
        }
        ConsoleHelper.writeMessage("");
    }
}
