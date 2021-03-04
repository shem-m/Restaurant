package com.javarush.task.task27.task2712.ad;

import java.util.*;

public class StatisticAdvertisementManager {
    private static StatisticAdvertisementManager manager;
    private AdvertisementStorage advertisementStorage = AdvertisementStorage.getInstance();

    private StatisticAdvertisementManager() {
    }

    public static StatisticAdvertisementManager getInstance() {
        if (manager == null) {
            manager = new StatisticAdvertisementManager();
        }
        return manager;
    }

    public Map<String, Integer> getActiveVideoSet() {
        Map<String, Integer> activeVideoSet = new TreeMap<>();
        for (Advertisement ad : advertisementStorage.list()) {
            if (ad.getHits() > 0) {
                activeVideoSet.put(ad.getName(), ad.getHits());
            }
        }
        return activeVideoSet;
    }

    public List<String> getArchivedVideoSet() {
        List<String> archivedVideoSet = new ArrayList<>();
        for (Advertisement ad : advertisementStorage.list()) {
            if (ad.getHits() == 0) {
                archivedVideoSet.add(ad.getName());
            }
        }
        archivedVideoSet.sort(String::compareToIgnoreCase);
        return archivedVideoSet;
    }
}
