/*
 * Project Honkai Stargazer (崩壞•星穹觀星者) was
 * Created & Develop by Voc-夜芷冰 , Programmer of Xectorda
 * Copyright © 2023 Xectorda 版權所有
 */

package com.voc.honkai_stargazer.data;

import com.voc.honkai_stargazer.util.ItemRSS;

public class FilterPreference {

    public static final int FILTER_ROOT_ELEMENT = 100;
    public static final int FILTER_PHYSICAL = 101;
    public static final int FILTER_FIRE = 102;
    public static final int FILTER_ICE = 103;
    public static final int FILTER_LIGHTNING = 104;
    public static final int FILTER_WIND = 105;
    public static final int FILTER_QUANTUM = 106;
    public static final int FILTER_IMAGINARY = 107;

    public static final int FILTER_ROOT_PATH = 201;
    public static final int FILTER_ABNDANCE = 201;
    public static final int FILTER_DESTRUCTION = 202;
    public static final int FILTER_HUNT = 203;
    public static final int FILTER_HARMONY = 204;
    public static final int FILTER_ERUDITION = 205;
    public static final int FILTER_NIHILITY = 206;
    public static final int FILTER_PRESERVATION = 207;

    public static final int FILTER_ROOT_RARITY = 300;
    public static final int FILTER_RARE1 = 301;
    public static final int FILTER_RARE2 = 302;
    public static final int FILTER_RARE3 = 303;
    public static final int FILTER_RARE4 = 304;
    public static final int FILTER_RARE5 = 305;

    public static final int FILTER_STATUS = 400;
    public static final int FILTER_RELEASED = 401;
    public static final int FILTER_SOON = 402;
    public static final int FILTER_BETA = 403;
    private boolean isAll = true;

    private boolean isPhysical = false;
    private boolean isFire = false;
    private boolean isIce = false;
    private boolean isLightning = false;
    private boolean isWind = false;
    private boolean isQuantum = false;
    private boolean isImaginary = false;

    private boolean isAbundance = false;
    private boolean isDestruction = false;
    private boolean isHunt = false;
    private boolean isHarmony = false;
    private boolean isErudition = false;
    private boolean isNihility = false;
    private boolean isPreservation = false;

    private boolean isRare1 = false;
    private boolean isRare2 = false;
    private boolean isRare3 = false;
    private boolean isRare4 = false;
    private boolean isRare5 = false;

    private boolean isRelease = false;
    private boolean isSoon = false;
    private boolean isBeta = false;

    private String TYPE;

    public void setType(String TYPE){this.TYPE = TYPE;}
    public String getType(){return TYPE;}

    public boolean isElementNotRequest(){
        int tmp = 0;
        if (isPhysical){tmp++;}
        if (isFire){tmp++;}
        if (isIce){tmp++;}
        if (isLightning){tmp++;}
        if (isQuantum){tmp++;}
        if (isImaginary){tmp++;}
        if (isWind){tmp++;}
        return (tmp == 0);
    }
    public boolean isPathNotRequest(){
        int tmp = 0;
        if (isErudition){tmp++;}
        if (isHarmony){tmp++;}
        if (isNihility){tmp++;}
        if (isHunt){tmp++;}
        if (isPreservation){tmp++;}
        if (isDestruction){tmp++;}
        if (isAbundance){tmp++;}
        return (tmp == 0);
    }
    public boolean isRareNotRequest(){
        int tmp = 0;
        //if (isRare1){tmp++;}
        //if (isRare2){tmp++;}
        if (isRare3 && getType().equals(ItemRSS.TYPE_LIGHTCONE)){tmp++;}
        if (isRare4){tmp++;}
        if (isRare5){tmp++;}
        return (tmp == 0);
    }
    public boolean isStatusNotRequest(){
        int tmp = 0;
        if (isBeta){tmp++;}
        if (isSoon){tmp++;}
        if (isRelease){tmp++;}
        return (tmp == 0);
    }

    public boolean isAll() {
        if (
                (isRelease && isSoon && isBeta) &&     //COMMON

                (TYPE.equals(ItemRSS.TYPE_CHARACTER) ? (
                        (!isPhysical && !isFire && !isIce && !isLightning && !isQuantum && !isImaginary && !isWind) && //Character Element
                        (!isErudition && !isHarmony && !isNihility && !isHunt && !isQuantum && !isDestruction && !isAbundance) && //Character Path
                        (!isRare4 && !isRare5) //Character Rare
                ) : false) && //Character Case

                (TYPE.equals(ItemRSS.TYPE_LIGHTCONE) ? (
                        (!isErudition && !isHarmony && !isNihility && !isHunt && !isQuantum && !isDestruction && !isAbundance) && //Lightcone Path
                        (!isRare3 && !isRare4 && !isRare5) //Lightcone Rare
                ) : false) && //Lightcone Case

                (TYPE.equals(ItemRSS.TYPE_RELIC) ? (
                        (!isRare1 && !isRare2 && !isRare3 && !isRare4 && !isRare5) //Relic Rare
                ) : false) //Relic Case

        ){
            isAll = true;
        }else{
            isAll = false;
        }
        return isAll;
    }

    public boolean isPhysical() {
        return isPhysical;
    }

    public void setPhysical(boolean physical) {
        isPhysical = physical;
    }

    public boolean isFire() {
        return isFire;
    }

    public void setFire(boolean fire) {
        isFire = fire;
    }

    public boolean isIce() {
        return isIce;
    }

    public void setIce(boolean ice) {
        isIce = ice;
    }

    public boolean isLightning() {
        return isLightning;
    }

    public void setLightning(boolean lightning) {
        isLightning = lightning;
    }

    public boolean isWind() {
        return isWind;
    }

    public void setWind(boolean wind) {
        isWind = wind;
    }

    public boolean isQuantum() {
        return isQuantum;
    }

    public void setQuantum(boolean quantum) {
        isQuantum = quantum;
    }

    public boolean isImaginary() {
        return isImaginary;
    }

    public void setImaginary(boolean imaginary) {
        isImaginary = imaginary;
    }

    public boolean isAbundance() {
        return isAbundance;
    }

    public void setAbundance(boolean abundance) {
        isAbundance = abundance;
    }

    public boolean isDestruction() {
        return isDestruction;
    }

    public void setDestruction(boolean destruction) {
        isDestruction = destruction;
    }

    public boolean isHunt() {
        return isHunt;
    }

    public void setHunt(boolean hunt) {
        isHunt = hunt;
    }

    public boolean isHarmony() {
        return isHarmony;
    }

    public void setHarmony(boolean harmony) {
        isHarmony = harmony;
    }

    public boolean isErudition() {
        return isErudition;
    }

    public void setErudition(boolean erudition) {
        isErudition = erudition;
    }

    public boolean isNihility() {
        return isNihility;
    }

    public void setNihility(boolean nihility) {
        isNihility = nihility;
    }

    public boolean isPreservation() {
        return isPreservation;
    }

    public void setPreservation(boolean preservation) {
        isPreservation = preservation;
    }

    public boolean isRare1() {
        return isRare1;
    }

    public void setRare1(boolean rare1) {
        isRare1 = rare1;
    }

    public boolean isRare2() {
        return isRare2;
    }

    public void setRare2(boolean rare2) {
        isRare2 = rare2;
    }

    public boolean isRare3() {
        return isRare3;
    }

    public void setRare3(boolean rare3) {
        isRare3 = rare3;
    }

    public boolean isRare4() {
        return isRare4;
    }

    public void setRare4(boolean rare4) {
        isRare4 = rare4;
    }

    public boolean isRare5() {
        return isRare5;
    }

    public void setRare5(boolean rare5) {
        isRare5 = rare5;
    }

    public boolean isRelease() {
        return isRelease;
    }

    public void setRelease(boolean release) {
        isRelease = release;
    }

    public boolean isSoon() {
        return isSoon;
    }

    public void setSoon(boolean soon) {
        isSoon = soon;
    }

    public boolean isBeta() {
        return isBeta;
    }

    public void setBeta(boolean beta) {
        isBeta = beta;
    }
}
