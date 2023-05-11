package com.voc.honkai_stargazer.data;

public class HSRItem {
    public String name; //名稱
    public String type; //類別
    public String element; //元素
    public String path; //元素
    public int rare = 0; //稀有度
    public String status; //即將推出

    public double HP;
    public double DEF;
    public double ATK;
    public double SPEED;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getRare() {
        return rare;
    }

    public void setRare(int rare) {
        this.rare = rare;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getHP() {
        return HP;
    }

    public void setHP(double HP) {
        this.HP = HP;
    }

    public double getDEF() {
        return DEF;
    }

    public void setDEF(double DEF) {
        this.DEF = DEF;
    }

    public double getATK() {
        return ATK;
    }

    public void setATK(double ATK) {
        this.ATK = ATK;
    }

    public double getSPEED() {
        return SPEED;
    }

    public void setSPEED(double SPEED) {
        this.SPEED = SPEED;
    }
}
