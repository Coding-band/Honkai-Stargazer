/*
 * Project Honkai Stargazer (崩壞•星穹觀星者) was
 * Created & Develop by Voc-夜芷冰 , Programmer of Xectorda
 * Copyright © 2023 Xectorda 版權所有
 */

package com.voc.honkai_stargazer.museum;

public class MuseumAssistant {

    public String name = "N/A"; //System Name of Assistant, E.g. Clara
    public int rare = 5; //Rare of Assistant, range from 3 - 5
    public int statusTime = 10; //遊覽時間 MAX 1000
    public int statusValue = 15; //推廣價值 MAX 1000
    public int statusPerson = 5; //吸引人流 MAX 1000

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRare() {
        return rare;
    }

    public void setRare(int rare) {
        this.rare = rare;
    }

    public int getStatusTime() {
        return statusTime;
    }

    public void setStatusTime(int statusTime) {
        this.statusTime = statusTime;
    }

    public int getStatusValue() {
        return statusValue;
    }

    public void setStatusValue(int statusValue) {
        this.statusValue = statusValue;
    }

    public int getStatusPerson() {
        return statusPerson;
    }

    public void setStatusPerson(int statusPerson) {
        this.statusPerson = statusPerson;
    }
}
