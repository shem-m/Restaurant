package com.javarush.task.task27.task2712;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomOrderGeneratorTask implements Runnable {
    private List<Tablet> tabletList;
    private int interval;

    public RandomOrderGeneratorTask(List<Tablet> tablets, int interval) {
        this.tabletList = tablets;
        this.interval = interval;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            int randomTablet = ThreadLocalRandom.current().nextInt(tabletList.size());
            Tablet tablet = tabletList.get(randomTablet);
            tablet.createTestOrder();
            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
