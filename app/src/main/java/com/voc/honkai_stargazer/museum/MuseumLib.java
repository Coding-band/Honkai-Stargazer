/*
 * Project Honkai Stargazer (崩壞•星穹觀星者) was
 * Created & Develop by Voc-夜芷冰 , Programmer of Xectorda
 * Copyright © 2023 Xectorda 版權所有
 */

package com.voc.honkai_stargazer.museum;

public class MuseumLib {
    //Data from https://honkai-star-rail.fandom.com/wiki/Everwinter_City_Museum_Ledger_of_Curiosities/Museum_Management#General_Hall_-_Exterior
    /**
     * C : 該地區缺乏一切需求
     * B：該區域至少缺少 2 種需求
     * A : 該區域至少缺少 1 項需求
     * S：該地區已滿足所有需求
     *
     * 獲得金幣 = 2x獲得振興值
     *  i.e. $540 = 2x (270 振興值)
     */
    //遊覽時間
    public static final int TYPE_TIME = 100;
    public static final int TYPE_VALUE = 101;
    public static final int TYPE_PERSON = 102;
    public static final int TYPE_TIME_ADD = 200;
    public static final int TYPE_VALUE_ADD = 201;
    public static final int TYPE_PERSON_ADD = 202;

    //振興值 最高等級 (等級1 對應 expLvl 1)
    public static final int expMaxLvl = 5;
    //展區 最高等級 (等級14可升級到等級15)
    public static final int areaMaxLvl = 15;
    //遊覽導引,推廣素材,遊客宣傳 最高等級
    public static final int itemMaxLvl = 25;
    //遊覽時間,推廣價值,吸引人流 最高值
    public static final int itemValueMax = 500;

    //遊覽時間,推廣價值,吸引人流 初始值 (系統贈送)
    public static final int itemValueInit = 40;
    //遊覽時間,推廣價值,吸引人流 初始目標值
    public static final int itemValueTargetInit = 65;

    //展區名稱 (稍後會變為R.string)
    public static final String[] areaName = new String[]{"綜合區-外","綜合區-内","工業區","歷史區"};

    //振興值等級 基礎值
    public static final long[] expMaxList = new long[]{
            4000, 55000, 400000, 1600000, 4000000
    };

    //展區升級成本 基礎值
    public static final long[] priceArea = new long[]{
            200,300,400,1500,3000,7000,12000,20000,40000,70000,
            120000,200000,320000,500000
    };
    //遊覽導引,推廣素材,遊客宣傳升級成本 基礎值
    public static final long[] priceItem = new long[]{
            100,200,300,400,550,650,850,1200,2000,3000,
            4500,6500,10000,15000,20000,25000,30000,40000,50000,60000,
            70000,80000,90000,100000
    };

    //展區升級附帶 遊覽導引需求增加 (累積)
    public static final long[] plusTimeFromArea = new long[]{
            20,30,25,30,30,20,25,30,15,20,
            15,20,30,25
    };

    //展區升級附帶 推廣素材需求增加 (累積)
    public static final long[] plusValueFromArea = new long[]{
            20,15,20,15,30,20,10,25,30,10,
            10,15,5,5
    };

    //展區升級附帶 遊客宣傳需求增加 (累積)
    public static final long[] plusPersonFromArea = new long[]{
            20,15,15,35,15,35,40,20,30,40,
            45,30,30,30
    };

    //展區升級附帶 最高獲得振興值
    public static final long[] plusMaxEXPFromArea = new long[]{
            100,120,100,100,350,450,600,1200,1600,2200,
            4000,6000,9000,14000
    };



    public String getRankingFromProgress(int 展區ID, int 展區等級, int 遊覽時間,int 推廣價值,int 吸引人流){
        int match = 0;
        if (遊覽時間 >= (itemValueTargetInit + loopAdd(plusTimeFromArea, 展區ID*3+1, 展區ID*3+1+展區等級))){match++;}
        if (推廣價值 >= (itemValueTargetInit + loopAdd(plusValueFromArea, 展區ID*3+1, 展區ID*3+1+展區等級))){match++;}
        if (吸引人流 >= (itemValueTargetInit + loopAdd(plusPersonFromArea, 展區ID*3+1, 展區ID*3+1+展區等級))){match++;}

        switch (match){
            case 3 : return "S";
            case 2 : return "A";
            case 1 : return "B";
            case 0 : return "C";
            default: return "D";
        }
    }

    public long getEXPFromProgress(int 展區ID, int 展區等級, int 遊覽時間,int 推廣價值,int 吸引人流){
        int match = 0;
        if (遊覽時間 >= (itemValueTargetInit + loopAdd(plusTimeFromArea, 展區ID*3+1, 展區ID*3+1+展區等級))){match++;}
        if (推廣價值 >= (itemValueTargetInit + loopAdd(plusValueFromArea, 展區ID*3+1, 展區ID*3+1+展區等級))){match++;}
        if (吸引人流 >= (itemValueTargetInit + loopAdd(plusPersonFromArea, 展區ID*3+1, 展區ID*3+1+展區等級))){match++;}

        switch (match){
            case 3 : return loopAdd(plusMaxEXPFromArea, 1, 展區ID*3+1+展區等級) * 3;
            case 2 : return loopAdd(plusMaxEXPFromArea, 1, 展區ID*3+1+展區等級) * 2;
            case 1 : return loopAdd(plusMaxEXPFromArea, 1, 展區ID*3+1+展區等級) * 1;
            case 0 : return loopAdd(plusMaxEXPFromArea, 1, 展區ID*3+1+展區等級) * 0;
            default: return 0;
        }
    }

    public long getCurrLvlItemMax(int 展區ID, int 展區等級, int TYPE){
        switch (TYPE){
            case TYPE_TIME: return loopAdd(plusTimeFromArea, 展區ID*3+1, 展區ID*3+1+展區等級);
            case TYPE_VALUE: return loopAdd(plusValueFromArea, 展區ID*3+1, 展區ID*3+1+展區等級);
            case TYPE_PERSON: return loopAdd(plusPersonFromArea, 展區ID*3+1, 展區ID*3+1+展區等級);
            default: return 1;
        }
    }

    public long loopAdd(long[] data, int begin, int end){
        long sum = 0;
        for (int x = begin-1 ; x < end && x < data.length ; x++){
            sum += data[x];
        }
        return sum;
    }

}
