/*
 * Project Honkai Stargazer (崩壞•星穹觀星者) was
 * Created & Develop by Voc-夜芷冰 , Programmer of Xectorda
 * Copyright © 2024 Xectorda 版權所有
 */

package com.voc.honkai_stargazer;

import static com.voc.honkai_stargazer.util.ItemRSS.LoadAssestData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

public class RelicScore {

    public static final String ATTR_CRIT_RATE = "暴擊率";
    public static final String ATTR_CRIT_DMG = "暴擊傷害";
    public static final String ATTR_HP = "生命值";
    public static final String ATTR_HPP = "生命值%";
    public static final String ATTR_ATK = "攻擊力";
    public static final String ATTR_ATKP = "攻擊力%";
    public static final String ATTR_DEF = "防禦力";
    public static final String ATTR_DEFP = "防禦力%";
    public static final String ATTR_SPD = "速度";
    public static final String ATTR_BREAK_EFFECT = "擊破特攻";
    public static final String ATTR_EFFECT_HIT = "效果命中";
    public static final String ATTR_EFFECT_RES = "效果抵抗";
    public static final String ATTR_QUANTUM = "量子屬性傷害提高";
    public static final String ATTR_FIRE = "火屬性傷害提高";
    public static final String ATTR_ICE = "冰屬性傷害提高";
    public static final String ATTR_IMAGINARY = "虛數屬性傷害提高";
    public static final String ATTR_WIND = "風屬性傷害提高";
    public static final String ATTR_THUNDER = "雷屬性傷害提高";
    public static final String ATTR_PHYSICAL = "物理屬性傷害提高";
    public static final String POS_HEAD_STR = "頭部";
    public static final String POS_HANDS_STR = "手部";
    public static final String POS_BODY_STR = "軀幹";
    public static final String POS_SHOES_STR = "鞋子";
    public static final String POS_BALL_STR = "位面球";
    public static final String POS_LINK_STR = "連結繩";
    public static final String SUB_ATTR_STR = "副詞條";
    public static final String MAIN_ATTR_STR = "主詞條";
    public static final int POS_HEAD = 0;
    public static final int POS_HANDS = 1;
    public static final int POS_BODY = 2;
    public static final int POS_SHOES = 3;
    public static final int POS_BALL = 4;
    public static final int POS_LINK = 5;
    public static final String[] ORDERs = new String[] { POS_HEAD_STR, POS_HANDS_STR, POS_BODY_STR, POS_SHOES_STR,
            POS_BALL_STR, POS_LINK_STR };
    private ArrayList<ArrayList<KeyAndValue>> relicSubArr = new ArrayList<>();
    private ArrayList<KeyAndValue> relicMainArr = new ArrayList<>();
    private ArrayList<Integer> relicLvls = new ArrayList<>();
    private ArrayList<KeyAndValue> relicFinalScoreMaps = new ArrayList<>();

    private class KeyAndValue {
        private String key;
        private Double value;

        public KeyAndValue(String key, Double value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public Double getValue() {
            return value;
        }

        public void setValue(Double value) {
            this.value = value;
        }

        public String toString() {
            return getKey() + " : " + getValue() + "\n";
        }
    }

    @Test
    public void testData() {
        // 權重
        String scorejs = "{\"main\":{\"1\":{\"HPDelta\":1},\"2\":{\"AttackDelta\":1},\"3\":{\"HPAddedRatio\":0,\"AttackAddedRatio\":0.8,\"DefenceAddedRatio\":0,\"CriticalChanceBase\":1,\"CriticalDamageBase\":0.9,\"HealRatioBase\":0,\"StatusProbabilityBase\":0},\"4\":{\"HPAddedRatio\":0,\"AttackAddedRatio\":0.8,\"DefenceAddedRatio\":0,\"SpeedDelta\":1},\"5\":{\"HPAddedRatio\":0,\"AttackAddedRatio\":0.8,\"DefenceAddedRatio\":0,\"PhysicalAddedRatio\":0,\"FireAddedRatio\":0,\"IceAddedRatio\":0,\"ThunderAddedRatio\":0,\"WindAddedRatio\":0,\"QuantumAddedRatio\":1,\"ImaginaryAddedRatio\":0},\"6\":{\"BreakDamageAddedRatioBase\":0,\"SPRatioBase\":0.5,\"HPAddedRatio\":0,\"AttackAddedRatio\":1,\"DefenceAddedRatio\":0}},\"weight\":{\"HPDelta\":0,\"AttackDelta\":0.3,\"DefenceDelta\":0,\"HPAddedRatio\":0,\"AttackAddedRatio\":0.8,\"DefenceAddedRatio\":0,\"SpeedDelta\":1,\"CriticalChanceBase\":1,\"CriticalDamageBase\":1,\"StatusProbabilityBase\":0,\"StatusResistanceBase\":0,\"BreakDamageAddedRatioBase\":0},\"max\":10.18}";
        // 儀器數據
        String json = "[{\"id\":\"61081\",\"name\":\"天才的超距遙感\",\"set_id\":\"108\",\"set_name\":\"繁星璀璨的天才\",\"rarity\":5,\"level\":15,\"icon\":\"icon/relic/108_0.png\",\"main_affix\":{\"type\":\"HPDelta\",\"field\":\"hp\",\"name\":\"生命值\",\"icon\":\"icon/property/IconMaxHP.png\",\"value\":705.6000000101048,\"display\":\"705\",\"percent\":false},\"sub_affix\":[{\"type\":\"AttackDelta\",\"field\":\"atk\",\"name\":\"攻擊力\",\"icon\":\"icon/property/IconAttack.png\",\"value\":35.986915002111346,\"display\":\"35\",\"percent\":false,\"count\":2,\"step\":1},{\"type\":\"DefenceDelta\",\"field\":\"def\",\"name\":\"防禦力\",\"icon\":\"icon/property/IconDefence.png\",\"value\":38.10379200289026,\"display\":\"38\",\"percent\":false,\"count\":2,\"step\":2},{\"type\":\"CriticalDamageBase\",\"field\":\"crit_dmg\",\"name\":\"暴擊傷害\",\"icon\":\"icon/property/IconCriticalDamage.png\",\"value\":0.11016000201925599,\"display\":\"11.0%\",\"percent\":true,\"count\":2,\"step\":1},{\"type\":\"StatusResistanceBase\",\"field\":\"effect_res\",\"name\":\"效果抗性\",\"icon\":\"icon/property/IconStatusResistance.png\",\"value\":0.11232000170275701,\"display\":\"11.2%\",\"percent\":true,\"count\":3,\"step\":2}]},{\"id\":\"61082\",\"name\":\"天才的頻變捕手\",\"set_id\":\"108\",\"set_name\":\"繁星璀璨的天才\",\"rarity\":5,\"level\":15,\"icon\":\"icon/relic/108_1.png\",\"main_affix\":{\"type\":\"AttackDelta\",\"field\":\"atk\",\"name\":\"攻擊力\",\"icon\":\"icon/property/IconAttack.png\",\"value\":352.8000000116881,\"display\":\"352\",\"percent\":false},\"sub_affix\":[{\"type\":\"HPDelta\",\"field\":\"hp\",\"name\":\"生命值\",\"icon\":\"icon/property/IconMaxHP.png\",\"value\":114.3113820059225,\"display\":\"114\",\"percent\":false,\"count\":3,\"step\":3},{\"type\":\"AttackAddedRatio\",\"field\":\"atk\",\"name\":\"攻擊力\",\"icon\":\"icon/property/IconAttack.png\",\"value\":0.043200000654907,\"display\":\"4.3%\",\"percent\":true,\"count\":1,\"step\":2},{\"type\":\"DefenceAddedRatio\",\"field\":\"def\",\"name\":\"防禦力\",\"icon\":\"icon/property/IconDefence.png\",\"value\":0.043199999956414,\"display\":\"4.3%\",\"percent\":true,\"count\":1,\"step\":0},{\"type\":\"BreakDamageAddedRatioBase\",\"field\":\"break_dmg\",\"name\":\"擊破特攻\",\"icon\":\"icon/property/IconBreakUp.png\",\"value\":0.233280004933476,\"display\":\"23.3%\",\"percent\":true,\"count\":4,\"step\":4}]},{\"id\":\"61083\",\"name\":\"天才的元域深潛\",\"set_id\":\"108\",\"set_name\":\"繁星璀璨的天才\",\"rarity\":5,\"level\":15,\"icon\":\"icon/relic/108_2.png\",\"main_affix\":{\"type\":\"CriticalChanceBase\",\"field\":\"crit_rate\",\"name\":\"暴擊率\",\"icon\":\"icon/property/IconCriticalChance.png\",\"value\":0.32399999862536205,\"display\":\"32.3%\",\"percent\":true},\"sub_affix\":[{\"type\":\"HPDelta\",\"field\":\"hp\",\"name\":\"生命值\",\"icon\":\"icon/property/IconMaxHP.png\",\"value\":84.67509800568223,\"display\":\"84\",\"percent\":false,\"count\":2,\"step\":4},{\"type\":\"AttackDelta\",\"field\":\"atk\",\"name\":\"攻擊力\",\"icon\":\"icon/property/IconAttack.png\",\"value\":78.32446100655943,\"display\":\"78\",\"percent\":false,\"count\":4,\"step\":5},{\"type\":\"HPAddedRatio\",\"field\":\"hp\",\"name\":\"生命值\",\"icon\":\"icon/property/IconMaxHP.png\",\"value\":0.043200000654907,\"display\":\"4.3%\",\"percent\":true,\"count\":1,\"step\":2},{\"type\":\"SpeedDelta\",\"field\":\"spd\",\"name\":\"速度\",\"icon\":\"icon/property/IconSpeed.png\",\"value\":2.600000000558794,\"display\":\"2\",\"percent\":false,\"count\":1,\"step\":2}]},{\"id\":\"61084\",\"name\":\"天才的引力漫步\",\"set_id\":\"108\",\"set_name\":\"繁星璀璨的天才\",\"rarity\":5,\"level\":15,\"icon\":\"icon/relic/108_3.png\",\"main_affix\":{\"type\":\"AttackAddedRatio\",\"field\":\"atk\",\"name\":\"攻擊力\",\"icon\":\"icon/property/IconAttack.png\",\"value\":0.43200000724755605,\"display\":\"43.2%\",\"percent\":true},\"sub_affix\":[{\"type\":\"AttackDelta\",\"field\":\"atk\",\"name\":\"攻擊力\",\"icon\":\"icon/property/IconAttack.png\",\"value\":76.20758400578052,\"display\":\"76\",\"percent\":false,\"count\":4,\"step\":4},{\"type\":\"HPAddedRatio\",\"field\":\"hp\",\"name\":\"生命值\",\"icon\":\"icon/property/IconMaxHP.png\",\"value\":0.034560000523925,\"display\":\"3.4%\",\"percent\":true,\"count\":1,\"step\":0},{\"type\":\"SpeedDelta\",\"field\":\"spd\",\"name\":\"速度\",\"icon\":\"icon/property/IconSpeed.png\",\"value\":2.0,\"display\":\"2\",\"percent\":false,\"count\":1,\"step\":0},{\"type\":\"StatusProbabilityBase\",\"field\":\"effect_hit\",\"name\":\"效果命中\",\"icon\":\"icon/property/IconStatusProbability.png\",\"value\":0.082080001244323,\"display\":\"8.2%\",\"percent\":true,\"count\":2,\"step\":3}]},{\"id\":\"63065\",\"name\":\"薩爾索圖的移動城市\",\"set_id\":\"306\",\"set_name\":\"停轉的薩爾索圖\",\"rarity\":5,\"level\":15,\"icon\":\"icon/relic/306_0.png\",\"main_affix\":{\"type\":\"QuantumAddedRatio\",\"field\":\"quantum_dmg\",\"name\":\"量子屬性傷害提高\",\"icon\":\"icon/property/IconQuantumAddedRatio.png\",\"value\":0.38880301429889397,\"display\":\"38.8%\",\"percent\":true},\"sub_affix\":[{\"type\":\"DefenceDelta\",\"field\":\"def\",\"name\":\"防禦力\",\"icon\":\"icon/property/IconDefence.png\",\"value\":52.92193400277756,\"display\":\"52\",\"percent\":false,\"count\":3,\"step\":1},{\"type\":\"AttackAddedRatio\",\"field\":\"atk\",\"name\":\"攻擊力\",\"icon\":\"icon/property/IconAttack.png\",\"value\":0.034560000523925,\"display\":\"3.4%\",\"percent\":true,\"count\":1,\"step\":0},{\"type\":\"CriticalDamageBase\",\"field\":\"crit_dmg\",\"name\":\"暴擊傷害\",\"icon\":\"icon/property/IconCriticalDamage.png\",\"value\":0.064800001680851,\"display\":\"6.4%\",\"percent\":true,\"count\":1,\"step\":2},{\"type\":\"StatusProbabilityBase\",\"field\":\"effect_hit\",\"name\":\"效果命中\",\"icon\":\"icon/property/IconStatusProbability.png\",\"value\":0.11664000176824801,\"display\":\"11.6%\",\"percent\":true,\"count\":3,\"step\":3}]},{\"id\":\"63066\",\"name\":\"薩爾索圖的晨昏界線\",\"set_id\":\"306\",\"set_name\":\"停轉的薩爾索圖\",\"rarity\":5,\"level\":15,\"icon\":\"icon/relic/306_1.png\",\"main_affix\":{\"type\":\"AttackAddedRatio\",\"field\":\"atk\",\"name\":\"攻擊力\",\"icon\":\"icon/property/IconAttack.png\",\"value\":0.43200000724755605,\"display\":\"43.2%\",\"percent\":true},\"sub_affix\":[{\"type\":\"AttackDelta\",\"field\":\"atk\",\"name\":\"攻擊力\",\"icon\":\"icon/property/IconAttack.png\",\"value\":35.986915002111346,\"display\":\"35\",\"percent\":false,\"count\":2,\"step\":1},{\"type\":\"CriticalDamageBase\",\"field\":\"crit_dmg\",\"name\":\"暴擊傷害\",\"icon\":\"icon/property/IconCriticalDamage.png\",\"value\":0.168480003252625,\"display\":\"16.8%\",\"percent\":true,\"count\":3,\"step\":2},{\"type\":\"StatusResistanceBase\",\"field\":\"effect_res\",\"name\":\"效果抗性\",\"icon\":\"icon/property/IconStatusResistance.png\",\"value\":0.043200000654907,\"display\":\"4.3%\",\"percent\":true,\"count\":1,\"step\":2},{\"type\":\"BreakDamageAddedRatioBase\",\"field\":\"break_dmg\",\"name\":\"擊破特攻\",\"icon\":\"icon/property/IconBreakUp.png\",\"value\":0.11016000201925599,\"display\":\"11.0%\",\"percent\":true,\"count\":2,\"step\":1}]}]";

        // String scorejs =
        // "{\"main\":{\"1\":{\"HPDelta\":1},\"2\":{\"AttackDelta\":1},\"3\":{\"HPAddedRatio\":0.8,\"AttackAddedRatio\":0,\"DefenceAddedRatio\":0,\"CriticalChanceBase\":0,\"CriticalDamageBase\":0,\"HealRatioBase\":1,\"StatusProbabilityBase\":0},\"4\":{\"HPAddedRatio\":1,\"AttackAddedRatio\":0,\"DefenceAddedRatio\":0,\"SpeedDelta\":1},\"5\":{\"HPAddedRatio\":1,\"AttackAddedRatio\":0,\"DefenceAddedRatio\":0.5,\"PhysicalAddedRatio\":0,\"FireAddedRatio\":0,\"IceAddedRatio\":0,\"ThunderAddedRatio\":0,\"WindAddedRatio\":0,\"QuantumAddedRatio\":0,\"ImaginaryAddedRatio\":0},\"6\":{\"BreakDamageAddedRatioBase\":0,\"SPRatioBase\":0.8,\"HPAddedRatio\":1,\"AttackAddedRatio\":0,\"DefenceAddedRatio\":0}},\"weight\":{\"HPDelta\":0.5,\"AttackDelta\":0,\"DefenceDelta\":0.2,\"HPAddedRatio\":1,\"AttackAddedRatio\":0,\"DefenceAddedRatio\":0.5,\"SpeedDelta\":1,\"CriticalChanceBase\":0,\"CriticalDamageBase\":0,\"StatusProbabilityBase\":0,\"StatusResistanceBase\":0.5,\"BreakDamageAddedRatioBase\":0},\"max\":9.3}";
        // String json =
        // "[{\"id\":\"61011\",\"name\":\"過客的逢春木簪\",\"set_id\":\"101\",\"set_name\":\"雲無留跡的過客\",\"rarity\":5,\"level\":15,\"icon\":\"icon/relic/101_0.png\",\"main_affix\":{\"type\":\"HPDelta\",\"field\":\"hp\",\"name\":\"生命值\",\"icon\":\"icon/property/IconMaxHP.png\",\"value\":705.6000000101048,\"display\":\"705\",\"percent\":false},\"sub_affix\":[{\"type\":\"HPAddedRatio\",\"field\":\"hp\",\"name\":\"生命值\",\"icon\":\"icon/property/IconMaxHP.png\",\"value\":0.159840002423155,\"display\":\"15.9%\",\"percent\":true,\"count\":4,\"step\":5},{\"type\":\"AttackAddedRatio\",\"field\":\"atk\",\"name\":\"攻擊力\",\"icon\":\"icon/property/IconAttack.png\",\"value\":0.043200000654907,\"display\":\"4.3%\",\"percent\":true,\"count\":1,\"step\":2},{\"type\":\"CriticalChanceBase\",\"field\":\"crit_rate\",\"name\":\"暴擊率\",\"icon\":\"icon/property/IconCriticalChance.png\",\"value\":0.05832000123337,\"display\":\"5.8%\",\"percent\":true,\"count\":2,\"step\":2},{\"type\":\"StatusProbabilityBase\",\"field\":\"effect_hit\",\"name\":\"效果命中\",\"icon\":\"icon/property/IconStatusProbability.png\",\"value\":0.034560000523925,\"display\":\"3.4%\",\"percent\":true,\"count\":1,\"step\":0}]},{\"id\":\"61132\",\"name\":\"蒔者的機巧木手\",\"set_id\":\"113\",\"set_name\":\"寶命長存的蒔者\",\"rarity\":5,\"level\":15,\"icon\":\"icon/relic/113_1.png\",\"main_affix\":{\"type\":\"AttackDelta\",\"field\":\"atk\",\"name\":\"攻擊力\",\"icon\":\"icon/property/IconAttack.png\",\"value\":352.8000000116881,\"display\":\"352\",\"percent\":false},\"sub_affix\":[{\"type\":\"HPDelta\",\"field\":\"hp\",\"name\":\"生命值\",\"icon\":\"icon/property/IconMaxHP.png\",\"value\":71.97383300308138,\"display\":\"71\",\"percent\":false,\"count\":2,\"step\":1},{\"type\":\"AttackAddedRatio\",\"field\":\"atk\",\"name\":\"攻擊力\",\"icon\":\"icon/property/IconAttack.png\",\"value\":0.155520002357664,\"display\":\"15.5%\",\"percent\":true,\"count\":4,\"step\":4},{\"type\":\"StatusProbabilityBase\",\"field\":\"effect_hit\",\"name\":\"效果命中\",\"icon\":\"icon/property/IconStatusProbability.png\",\"value\":0.043200000654907,\"display\":\"4.3%\",\"percent\":true,\"count\":1,\"step\":2},{\"type\":\"StatusResistanceBase\",\"field\":\"effect_res\",\"name\":\"效果抗性\",\"icon\":\"icon/property/IconStatusResistance.png\",\"value\":0.086400001309814,\"display\":\"8.6%\",\"percent\":true,\"count\":2,\"step\":4}]},{\"id\":\"61013\",\"name\":\"過客的殘繡風衣\",\"set_id\":\"101\",\"set_name\":\"雲無留跡的過客\",\"rarity\":5,\"level\":15,\"icon\":\"icon/relic/101_2.png\",\"main_affix\":{\"type\":\"HealRatioBase\",\"field\":\"heal_rate\",\"name\":\"治療量加成\",\"icon\":\"icon/property/IconHealRatio.png\",\"value\":0.345606000395488,\"display\":\"34.5%\",\"percent\":true},\"sub_affix\":[{\"type\":\"AttackDelta\",\"field\":\"atk\",\"name\":\"攻擊力\",\"icon\":\"icon/property/IconAttack.png\",\"value\":38.10379200289026,\"display\":\"38\",\"percent\":false,\"count\":2,\"step\":2},{\"type\":\"DefenceDelta\",\"field\":\"def\",\"name\":\"防禦力\",\"icon\":\"icon/property/IconDefence.png\",\"value\":35.986915002111346,\"display\":\"35\",\"percent\":false,\"count\":2,\"step\":1},{\"type\":\"StatusProbabilityBase\",\"field\":\"effect_hit\",\"name\":\"效果命中\",\"icon\":\"icon/property/IconStatusProbability.png\",\"value\":0.07344000111334101,\"display\":\"7.3%\",\"percent\":true,\"count\":2,\"step\":1},{\"type\":\"BreakDamageAddedRatioBase\",\"field\":\"break_dmg\",\"name\":\"擊破特攻\",\"icon\":\"icon/property/IconBreakUp.png\",\"value\":0.168480003252625,\"display\":\"16.8%\",\"percent\":true,\"count\":3,\"step\":2}]},{\"id\":\"61134\",\"name\":\"蒔者的天人絲履\",\"set_id\":\"113\",\"set_name\":\"寶命長存的蒔者\",\"rarity\":5,\"level\":15,\"icon\":\"icon/relic/113_3.png\",\"main_affix\":{\"type\":\"HPAddedRatio\",\"field\":\"hp\",\"name\":\"生命值\",\"icon\":\"icon/property/IconMaxHP.png\",\"value\":0.43200000724755605,\"display\":\"43.2%\",\"percent\":true},\"sub_affix\":[{\"type\":\"HPDelta\",\"field\":\"hp\",\"name\":\"生命值\",\"icon\":\"icon/property/IconMaxHP.png\",\"value\":76.20758800394833,\"display\":\"76\",\"percent\":false,\"count\":2,\"step\":2},{\"type\":\"AttackAddedRatio\",\"field\":\"atk\",\"name\":\"攻擊力\",\"icon\":\"icon/property/IconAttack.png\",\"value\":0.077760001178832,\"display\":\"7.7%\",\"percent\":true,\"count\":2,\"step\":2},{\"type\":\"DefenceAddedRatio\",\"field\":\"def\",\"name\":\"防禦力\",\"icon\":\"icon/property/IconDefence.png\",\"value\":0.14579999959096202,\"display\":\"14.5%\",\"percent\":true,\"count\":3,\"step\":3},{\"type\":\"CriticalChanceBase\",\"field\":\"crit_rate\",\"name\":\"暴擊率\",\"icon\":\"icon/property/IconCriticalChance.png\",\"value\":0.05832000123337,\"display\":\"5.8%\",\"percent\":true,\"count\":2,\"step\":2}]},{\"id\":\"63025\",\"name\":\"羅浮仙舟的天外樓船\",\"set_id\":\"302\",\"set_name\":\"不老者的仙舟\",\"rarity\":5,\"level\":15,\"icon\":\"icon/relic/302_0.png\",\"main_affix\":{\"type\":\"HPAddedRatio\",\"field\":\"hp\",\"name\":\"生命值\",\"icon\":\"icon/property/IconMaxHP.png\",\"value\":0.43200000724755605,\"display\":\"43.2%\",\"percent\":true},\"sub_affix\":[{\"type\":\"DefenceDelta\",\"field\":\"def\",\"name\":\"防禦力\",\"icon\":\"icon/property/IconDefence.png\",\"value\":19.05189600144513,\"display\":\"19\",\"percent\":false,\"count\":1,\"step\":1},{\"type\":\"CriticalChanceBase\",\"field\":\"crit_rate\",\"name\":\"暴擊率\",\"icon\":\"icon/property/IconCriticalChance.png\",\"value\":0.055080001009628995,\"display\":\"5.5%\",\"percent\":true,\"count\":2,\"step\":1},{\"type\":\"CriticalDamageBase\",\"field\":\"crit_dmg\",\"name\":\"暴擊傷害\",\"icon\":\"icon/property/IconCriticalDamage.png\",\"value\":0.12312000291422,\"display\":\"12.3%\",\"percent\":true,\"count\":2,\"step\":3},{\"type\":\"BreakDamageAddedRatioBase\",\"field\":\"break_dmg\",\"name\":\"擊破特攻\",\"icon\":\"icon/property/IconBreakUp.png\",\"value\":0.174960003700107,\"display\":\"17.4%\",\"percent\":true,\"count\":3,\"step\":3}]},{\"id\":\"63026\",\"name\":\"羅浮仙舟的建木枝蔓\",\"set_id\":\"302\",\"set_name\":\"不老者的仙舟\",\"rarity\":5,\"level\":15,\"icon\":\"icon/relic/302_1.png\",\"main_affix\":{\"type\":\"HPAddedRatio\",\"field\":\"hp\",\"name\":\"生命值\",\"icon\":\"icon/property/IconMaxHP.png\",\"value\":0.43200000724755605,\"display\":\"43.2%\",\"percent\":true},\"sub_affix\":[{\"type\":\"HPDelta\",\"field\":\"hp\",\"name\":\"生命值\",\"icon\":\"icon/property/IconMaxHP.png\",\"value\":122.7788920076564,\"display\":\"122\",\"percent\":false,\"count\":3,\"step\":5},{\"type\":\"AttackAddedRatio\",\"field\":\"atk\",\"name\":\"攻擊力\",\"icon\":\"icon/property/IconAttack.png\",\"value\":0.034560000523925,\"display\":\"3.4%\",\"percent\":true,\"count\":1,\"step\":0},{\"type\":\"CriticalChanceBase\",\"field\":\"crit_rate\",\"name\":\"暴擊率\",\"icon\":\"icon/property/IconCriticalChance.png\",\"value\":0.025920000392944,\"display\":\"2.5%\",\"percent\":true,\"count\":1,\"step\":0},{\"type\":\"StatusProbabilityBase\",\"field\":\"effect_hit\",\"name\":\"效果命中\",\"icon\":\"icon/property/IconStatusProbability.png\",\"value\":0.11664000176824801,\"display\":\"11.6%\",\"percent\":true,\"count\":3,\"step\":3}]}]";
        try {
            JSONArray relicArrayJS = new JSONArray(json);
            JSONObject scoreObj = new JSONObject(scorejs);
            // 主詞條
            relicMainArr = new ArrayList<>();
            // 副詞條
            relicSubArr = new ArrayList<>();
            // 遺器等級
            relicLvls = new ArrayList<>();
            // 詞條評分
            relicFinalScoreMaps = new ArrayList<>();

            for (int x = 0; x < relicArrayJS.length(); x++) {
                // main affix
                if (!relicArrayJS.getJSONObject(x).has("main_affix")) {
                    System.out.println("Seele dont have main_affix");
                    return;
                } else {
                    JSONObject main_affix = relicArrayJS.getJSONObject(x).getJSONObject("main_affix");
                    relicMainArr.add(new KeyAndValue(getTypeBySourceName(main_affix.getString("type")),
                            main_affix.getDouble("value")));
                }
                // sub affix
                if (!relicArrayJS.getJSONObject(x).has("sub_affix")) {
                    System.out.println("Seele dont have sub_affix");
                    return;
                } else {
                    JSONArray sub_affix = relicArrayJS.getJSONObject(x).getJSONArray("sub_affix");
                    ArrayList<KeyAndValue> keyAndValueArrayList = new ArrayList<>();
                    for (int y = 0; y < sub_affix.length(); y++) {
                        keyAndValueArrayList
                                .add(new KeyAndValue(getTypeBySourceName(sub_affix.getJSONObject(y).getString("type")),
                                        sub_affix.getJSONObject(y).getDouble("value")));
                    }
                    relicSubArr.add(keyAndValueArrayList);
                    relicLvls.add(relicArrayJS.getJSONObject(x).getInt("level"));
                }

            }

            // Score calculate
            JSONObject mainAttrWeight = scoreObj.getJSONObject("main");
            JSONObject subAttrWeight = scoreObj.getJSONObject("weight");
            ArrayList<String> tmpSubKey = new ArrayList<>();
            ArrayList<Double> tmpSubValue = new ArrayList<>();

            // SUB ATTR LIST
            for (Iterator<String> it = subAttrWeight.keys(); it.hasNext();) {
                String key = it.next();
                tmpSubKey.add(getTypeBySourceName(key));
                tmpSubValue.add(subAttrWeight.getDouble(key));
            }

            for (int x = 0; x < 6; x++) {
                // MAIN
                JSONObject mainAttr = mainAttrWeight.getJSONObject(String.valueOf(x + 1));
                ArrayList<String> tmpMainKey = new ArrayList<>();
                ArrayList<Double> tmpMainValue = new ArrayList<>();
                for (Iterator<String> it = mainAttr.keys(); it.hasNext();) {
                    String key = it.next();
                    tmpMainKey.add(getTypeBySourceName(key));
                    tmpMainValue.add(mainAttr.getDouble(key));
                }
                // Seek out whether is same
                Double score = 0d;
                if (tmpMainKey.contains(relicMainArr.get(x).getKey())) {
                    // Calculate
                    score = getMainAttrPlusScore(relicMainArr.get(x).getKey(), relicMainArr.get(x).getValue(),
                            tmpMainValue.get(tmpMainKey.indexOf(relicMainArr.get(x).getKey())), relicLvls.get(x), x);
                }
                relicFinalScoreMaps.add(new KeyAndValue(ORDERs[x] + MAIN_ATTR_STR, score));

                // SUB
                for (int y = 0; y < relicSubArr.get(x).size(); y++) {
                    score = 0d;
                    if (tmpSubKey.contains(relicSubArr.get(x).get(y).getKey())) {
                        // Calculate
                        score = getSubAttrScore(relicSubArr.get(x).get(y).getKey(),
                                relicSubArr.get(x).get(y).getValue(),
                                tmpSubValue.get(tmpSubKey.indexOf(relicSubArr.get(x).get(y).getKey())));
                    }
                    relicFinalScoreMaps.add(new KeyAndValue(ORDERs[x] + SUB_ATTR_STR + String.valueOf(y + 1) + " - "
                            + relicSubArr.get(x).get(y).getKey(), score));
                }
            }

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        // 以下東西沒有意義
        // 以下東西沒有意義
        // 以下東西沒有意義
        {
            String tmp = "";

            System.out.println("主詞條列表");

            tmp = "";
            for (KeyAndValue keyAndValue : relicMainArr) {
                tmp += keyAndValue.toString();
            }
            System.out.println(tmp);

            System.out.println("副詞條列表");
            tmp = "";
            for (ArrayList<KeyAndValue> arrayList : relicSubArr) {
                for (KeyAndValue keyAndValue : arrayList) {
                    tmp += keyAndValue.toString();
                }
            }
            System.out.println(tmp);

            System.out.println("詞條分數");
            Collections.sort(relicFinalScoreMaps, (key1, key2) -> key1.getKey().compareTo(key2.getKey()));
            System.out.println(relicFinalScoreMaps);

            System.out.println("每遺器分數");
            Double finalScore = 0d;
            tmp = "";
            double[] valueFinal = { 0, 0, 0, 0, 0, 0 };
            for (int x = 0; x < relicFinalScoreMaps.size(); x++) {
                for (String keyInOrders : ORDERs) {
                    if (relicFinalScoreMaps.get(x).getKey().contains(keyInOrders)) {
                        valueFinal[Arrays.asList(ORDERs).indexOf(keyInOrders)] += relicFinalScoreMaps.get(x).getValue();
                    }
                }

                finalScore += relicFinalScoreMaps.get(x).getValue();
            }

            for (int x = 0; x < valueFinal.length; x++) {
                tmp += ORDERs[x] + " : " + valueFinal[x] + "\n";
            }

            System.out.println(tmp);
            System.out.println("詞條總分");
            System.out.println(finalScore);
        }
    }

    /**
     * 有効副詞條加分
     * 
     * @param attrName       副詞條名稱
     * @param attrValue      副詞條數值
     * @param charAttrWeight 角色對該詞條權重
     * @return
     */
    public Double getSubAttrScore(String attrName, Double attrValue, Double charAttrWeight) {
        if (charAttrWeight == 0.3 || charAttrWeight == 0.8) {
            charAttrWeight -= 0.05;
        } // 避免數據四捨五入用
        switch (attrName) {
            case ATTR_CRIT_RATE: {
                return attrValue * 100 * 2 * charAttrWeight;
            }
            case ATTR_CRIT_DMG: {
                return attrValue * 100 * 1 * charAttrWeight;
            }
            case ATTR_HPP: {
                return attrValue * 100 * 1.5 * charAttrWeight;
            }
            case ATTR_ATKP: {
                return attrValue * 100 * 1.5 * charAttrWeight;
            }
            case ATTR_DEFP: {
                return attrValue * 100 * 1.19 * charAttrWeight;
            }
            case ATTR_SPD: {
                return attrValue * 2.53 * charAttrWeight;
            }
            case ATTR_BREAK_EFFECT: {
                return attrValue * 100 * 1 * charAttrWeight;
            }
            case ATTR_EFFECT_HIT: {
                return attrValue * 100 * 1.49 * charAttrWeight;
            }
            case ATTR_EFFECT_RES: {
                return attrValue * 100 * 1.49 * charAttrWeight;
            }
            case ATTR_HP: {
                return attrValue * 0.153 * 0.5 * charAttrWeight;
            }
            case ATTR_ATK: {
                return attrValue * 0.3 * 0.5 * charAttrWeight;
            }
            case ATTR_DEF: {
                return attrValue * 0.3 * 0.5 * charAttrWeight;
            }
            case ATTR_QUANTUM: {
                return attrValue * charAttrWeight;
            }
            case ATTR_FIRE: {
                return attrValue * 100 * charAttrWeight;
            }
            case ATTR_WIND: {
                return attrValue * 100 * charAttrWeight;
            }
            case ATTR_ICE: {
                return attrValue * 100 * charAttrWeight;
            }
            case ATTR_IMAGINARY: {
                return attrValue * 100 * charAttrWeight;
            }
            case ATTR_THUNDER: {
                return attrValue * 100 * charAttrWeight;
            }
            case ATTR_PHYSICAL: {
                return attrValue * 100 * charAttrWeight;
            }
        }
        return 0d;
    }

    /**
     * 有効主詞條加分
     * 
     * @param attrName       主詞條名稱
     * @param attrValue      主詞條數值
     * @param charAttrWeight 角色對該詞條權重
     * @param position       遺器位置
     * @param relicLvl       遺器等級
     * @return
     */
    public Double getMainAttrPlusScore(String attrName, Double attrValue, Double charAttrWeight, int relicLvl,
            int position) {
        switch (position) {
            case POS_HEAD:
                return 0d;
            case POS_HANDS:
                return 0d;
            case POS_BODY:
                return (Math.min((5.83 * charAttrWeight) + relicLvl * 0.66, 5.83) + 10);
            case POS_SHOES:
                return (5.83 * charAttrWeight);
            case POS_BALL:
                return (Math.min((5.83 * charAttrWeight) + relicLvl * 0.66, 5.83) + 10);
            case POS_LINK:
                return (5.83 * charAttrWeight);
        }
        return 0d;
    }

    /**
     * Type轉換
     */
    public String getTypeBySourceName(String attrSourceName) {
        switch (attrSourceName) {
            case "AttackAddedRatio":
                return ATTR_ATKP;
            case "AttackDelta":
                return ATTR_ATK;
            case "DefenceAddedRatio":
                return ATTR_DEFP;
            case "DefenceDelta":
                return ATTR_DEF;
            case "HPAddedRatio":
                return ATTR_HPP;
            case "HPDelta":
                return ATTR_HP;
            case "SpeedDelta":
                return ATTR_SPD;
            case "PhysicalAddedRatio":
                return ATTR_PHYSICAL;
            case "FireAddedRatio":
                return ATTR_FIRE;
            case "IceAddedRatio":
                return ATTR_ICE;
            case "ThunderAddedRatio":
                return ATTR_THUNDER;
            case "WindAddedRatio":
                return ATTR_WIND;
            case "QuantumAddedRatio":
                return ATTR_QUANTUM;
            case "ImaginaryAddedRatio":
                return ATTR_IMAGINARY;

            case "CriticalChanceBase":
                return ATTR_CRIT_RATE;
            case "CriticalDamageBase":
                return ATTR_CRIT_DMG;
            case "BreakDamageAddedRatioBase":
                return ATTR_BREAK_EFFECT;
            case "StatusProbabilityBase":
                return ATTR_EFFECT_HIT;
            case "StatusResistanceBase":
                return ATTR_EFFECT_RES;
        }
        return "N/A";
    }
}
