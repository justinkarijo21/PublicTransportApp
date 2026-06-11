package com.example.demo.model;

public class Station {
    private final String name;
    private final int platforms;
    private final boolean wheelchairAccessible;
    private final String city;

    public Station(String name, int platforms, boolean wheelchairAccessible, String city) {
        this.name = name;
        this.platforms = platforms;
        this.wheelchairAccessible = wheelchairAccessible;
        this.city = city;
    }

    public String getName(){
        return name;
    }

    public int getPlatforms(){
        return platforms;
    }

    public boolean isWheelchairAccessible(){
        return wheelchairAccessible;
    }

    public String getCity(){
        return city;
    }

}
