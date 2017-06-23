package com.scarlat.marius.connect3.GameDesign;


import android.graphics.Color;

public class Player {

    private int id;
    private String name;
    private boolean active;
    private int color;

    public Player() {
        id = -1;
        name = "";
        active = false;
        color = Color.rgb(255, 255, 255);
    }

    public Player (int id, String name, boolean active, int color) {
        this.id = id;
        this.name = name;
        this.active = active;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
