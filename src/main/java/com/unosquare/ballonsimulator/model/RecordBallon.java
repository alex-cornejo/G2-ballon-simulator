package com.unosquare.ballonsimulator.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * RecordBallon
 */
public class RecordBallon implements Comparable<RecordBallon> {

    private LocalDateTime timestamp;
    private int[] location;
    private int temperature;
    private Observatory.ObservatoryEnum observatory;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

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

}