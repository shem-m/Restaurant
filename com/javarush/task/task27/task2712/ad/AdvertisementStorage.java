package com.javarush.task.task27.task2712.ad;

import java.util.ArrayList;
import java.util.List;

/**
 * Хранилище рекламных роликов
 */

public class AdvertisementStorage {
    private static AdvertisementStorage advertisementStorage;
    private final List<Advertisement> videos = new ArrayList();

    private AdvertisementStorage() {
        Object someContent = new Object();
        add(new Advertisement(someContent, "First Video", 5000, 100, 3 * 60)); // 3 min
        add(new Advertisement(someContent, "Second Video", 100, 10, 15 * 60)); //15 min
        add(new Advertisement(someContent, "Third Video", 400, 120, 10 * 60)); //10 min
        add(new Advertisement(someContent, "Fourth Video", 1500, 20, 25 * 60)); //25 min
        add(new Advertisement(someContent, "Fifth Video", 800, 50, 5 * 60)); //5 min
        add(new Advertisement(someContent, "Sixth Video", 2500, 5, 7 * 60)); //7 min
        add(new Advertisement(someContent, "Seventh Video", 350, 70, 4 * 60)); //4 min

    }

    public static AdvertisementStorage getInstance() {
        if (advertisementStorage == null) {
            advertisementStorage = new AdvertisementStorage();
        }
        return advertisementStorage;
    }

    public List<Advertisement> list() {
        return videos;
    }

    public void add(Advertisement advertisement) {
        videos.add(advertisement);
    }
}
