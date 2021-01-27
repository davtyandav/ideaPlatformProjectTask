package com.github.daviddavtyan;


import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class Util {

    public static Duration getAverage(List<Duration> items) {
        long sum = 0;
        for (Duration item : items) {
            sum = sum + item.toMinutes();
        }
        long average = sum / items.size();
        return Duration.ofMinutes(average);
    }

    public static Duration getPercentile(List<Duration> items, int percentile) {
        List<Duration> collect = items.stream().sorted().collect(Collectors.toList());
        int index = (collect.size() - 1) * percentile / 100;

        return items.get(index);
    }
}
