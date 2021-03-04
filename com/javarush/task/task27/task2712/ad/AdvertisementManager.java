package com.javarush.task.task27.task2712.ad;

import com.javarush.task.task27.task2712.ConsoleHelper;
import com.javarush.task.task27.task2712.statistic.StatisticManager;
import com.javarush.task.task27.task2712.statistic.event.VideoSelectedEventDataRow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Класс подбирает оптимальный набор роликов и их последовательность для каждого заказа.
 * А также взаимодействовует с плеером и отображает ролики.
 */

public class AdvertisementManager {
    private final AdvertisementStorage storage = AdvertisementStorage.getInstance();
    private int timeSeconds; //Время приготовления заказа

    public AdvertisementManager(int timeSeconds) {
        this.timeSeconds = timeSeconds;
    }

    public void processVideos() {
        if (storage.list().isEmpty()) {
            throw new NoVideoAvailableException();
        }

        List<Advertisement> videoList = new ArrayList<>();
        for (Advertisement video : storage.list()) {
            if (video.getHits() > 0) {
                videoList.add(video);
            }
        }

        videoList.sort(Comparator.comparingInt(Advertisement::getDuration));
        videoList.sort((Comparator.comparingLong(Advertisement::getAmountPerOneDisplaying)));
        Collections.reverse(videoList);

        long amount;
        int totalDuration = 0;

        int timeLeft = timeSeconds;
        for (Advertisement advertisement : videoList) {
            if (advertisement.getDuration() <= timeLeft && advertisement.getAmountPerOneDisplaying() > 0) {

                totalDuration += advertisement.getDuration();
                amount = advertisement.getAmountPerOneDisplaying();
                StatisticManager.getInstance().register(new VideoSelectedEventDataRow(videoList, amount, totalDuration));

                ConsoleHelper.writeMessage(advertisement.getName()
                        + " is displaying... "
                        + advertisement.getAmountPerOneDisplaying() //стоимость показа одного рекламного ролика в копейках
                        + ", "
                        + advertisement.getAmountPerOneDisplaying() * 1000 / advertisement.getDuration()); //стоимость одной секунды просмтора видео в тысячных частях копейки (277 = 0.277 коп.)
                advertisement.revalidate();
                timeLeft -= advertisement.getDuration();
            }
        }
        ConsoleHelper.writeMessage("");
    }
}
