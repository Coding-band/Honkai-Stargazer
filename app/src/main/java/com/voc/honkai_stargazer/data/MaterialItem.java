/*
 * Project Honkai Stargazer (崩壞•星穹觀星者) was
 * Created & Develop by Voc-夜芷冰 , Programmer of Xectorda
 * Copyright © 2023 Xectorda 版權所有
 */

package com.voc.honkai_stargazer.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MaterialItem implements Serializable {

    /**
     * id : 29328
     * type : 101
     * purposeId : 11
     * name : 信用點
     * desc : 星際和平公司與客戶結算時使用的貨幣，如今已成為太空旅行的硬通貨。
     * lore : 「人們奔波、爭鬥、貿易，為的不過是個終端裡顯示的數字。但真正珍貴之物，公司的數字是買不到的。」
     * purpose : 通用貨幣
     * rarity : 3
     * comeFrom : ["擬造花萼【大礦區】","任務獎勵","委託獎勵"]
     */

    private int id;
    private int type;
    private int purposeId;
    private String name;
    private String desc;
    private String lore;
    private String purpose;
    private int rarity;
    private ArrayList<String> comeFrom;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPurposeId() {
        return purposeId;
    }

    public void setPurposeId(int purposeId) {
        this.purposeId = purposeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLore() {
        return lore;
    }

    public void setLore(String lore) {
        this.lore = lore;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public int getRarity() {
        return rarity;
    }

    public void setRarity(int rarity) {
        this.rarity = rarity;
    }

    public ArrayList<String> getComeFrom() {
        return comeFrom;
    }

    public void setComeFrom(ArrayList<String> comeFrom) {
        this.comeFrom = comeFrom;
    }
}
