package com.unosquare.ballonsimulator.model;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Random;

import com.unosquare.ballonsimulator.model.Observatory.ObservatoryEnum;

/**
 * RecordBallon
 */
public class RecordBallon implements Comparable<RecordBallon> {

    private LocalDateTime timestamp;
    private int[] location;
    private int temperature;
    private Observatory.ObservatoryEnum observatory;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    private RecordBallon(LocalDateTime timestamp, int[] location, int temperature, ObservatoryEnum observatory) {
        this.timestamp = timestamp;
        this.location = location;
        this.temperature = temperature;
        this.observatory = observatory;
    }

    public RecordBallon(String pattern) {
        String[] data = pattern.split("\\|");
        if (data.length != 4) {
            throw new IllegalArgumentException("The pattern has wrong data");
        }

        this.timestamp = LocalDateTime.parse(data[0], formatter);

        String[] arrLocation = data[1].split(",");
        if (arrLocation.length != 2) {
            throw new IllegalArgumentException("Location has wrong data");
        }
        this.location = new int[] { Integer.parseInt(arrLocation[0]), Integer.parseInt(arrLocation[1]) };

        this.temperature = Integer.parseInt(data[2]);

        this.setObservatory(data[3]);

    }

    @Override
    public String toString() {
        return String.format("%s|%d,%d|%d|%s", timestamp.format(formatter), location[0], location[1], temperature,
                observatory.getCode());
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int[] getLocation() {
        return location;
    }

    public void setLocation(int[] location) {
        this.location = location;
    }

    public void setLocation(int x, int y) {
        this.location = new int[] { x, y };
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public Observatory.ObservatoryEnum getObservatory() {
        return observatory;
    }

    public void setObservatory(Observatory.ObservatoryEnum observatory) {
        this.observatory = observatory;
    }

    public void setObservatory(String observatoryCode) {
        this.observatory = Observatory.getObservatory(observatoryCode);
    }

    @Override
    public int compareTo(RecordBallon recordBallon) {
        return this.timestamp.compareTo(recordBallon.timestamp);
    }

    public static RecordBallon createRandomRecord() {

        Random rnd = new Random();

        LocalDateTime start = LocalDateTime.of(1970, Month.JANUARY, 1, 0, 0);
        long days = ChronoUnit.DAYS.between(start, LocalDateTime.now());
        LocalDateTime timestamp = start.plusDays(rnd.nextInt((int) days + 1)).plusHours(rnd.nextInt(23))
                .plusMinutes(rnd.nextInt(59));

        int[] location = { rnd.nextInt(10), rnd.nextInt(10) };
        int temperature = rnd.nextInt(100);
        ObservatoryEnum observatory = ObservatoryEnum.values()[rnd.nextInt(ObservatoryEnum.values().length - 1)];

        return new RecordBallon(timestamp, location, temperature, observatory);
    }
}