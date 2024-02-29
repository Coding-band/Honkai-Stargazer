import util from "util"
//三星常駐
export const itemRare3 = [
    "鋒鏑","物穰","天傾","琥珀",
    "幽邃","齊頌","智庫","離弦",
    "嘉果","樂圮","戍禦","輪契",
    "靈鑰","相抗","俱歿","開疆",
    "匿影","淵環","蕃息","調和",
    "睿見"
]

//四星光錐常駐
export const itemRare4LC = [
    "一場術後對話","晚安與睡顏","餘生的第一天","唯有沉默",
    "記憶中的模樣","鼴鼠黨歡迎你","「我」的誕生","同一種心情",
    "獵物的視線","朗道的選擇","論劍","與行星相會",
    "秘密誓心","別讓世界靜下來","此時恰好","決心如汗珠般閃耀",
    "宇宙市場趨勢","按個追蹤吧!","舞!舞!舞!","在藍天下",
    "天才們的休憩",
]

//四星角色常駐
export const itemRare4Char = [
    "素裳","黑塔","娜塔莎","希露瓦",
    "佩拉","桑博","虎克","艾絲妲",
    "青雀","阿蘭","馭空","盧卡",
    "丹恆","寒鴉","雪衣","玲可",
    "三月七","桂乃芬","停雲",
]

//五星角色常駐
export const itemRare5Char = [
    "姬子","瓦爾特","布洛妮婭","傑帕德",
    "克拉拉","彥卿","白露"
]

//五星光錐常駐
export const itemRare5LC = [
    "銀河鐵道之夜","無可取代的東西","但戰鬥還未結束","以世界之名",
    "制勝的瞬間","如泥酣眠","時節不居"
]

//限定角色
export const pullNormalRateChar = [0.943, 0.051, 0.006]
export const pullPityRateChar = [0.854, 0.13, 0.016]
export const pullPityOrNotChar = 0.5
export const pullPityMaxChar = 90

//限定光錐
export const pullNormalRateLC = [0.943, 0.051, 0.006]
export const pullPityRateLC = [0.854, 0.13, 0.016]
export const pullPityOrNotLC = 0.75
export const pullPityMaxLC = 80

//常駐
export const pullNormalRateStatic = [0.943, 0.051, 0.006]
export const pullPityRateStatic = [0.854, 0.13, 0.016]

export enum PullType{
    PULL_LIMIT_CHAR,  //限定角色池
    PULL_LIMIT_LIGHTCONE, //限定光錐池
    PULL_STATIC //常駐池
}

export class PullInfo{
    special_rare5: Array<string> = [];
    special_rare4: Array<string> = [];
  };

  export class PullConfig{
    pullsAfterRare4 : number = 0;
    pullsAfterRare5 : number = 0;
    isMustGetRare4 : boolean = false;
    isMustGetRare5 : boolean = false;
}

/**
 * 
 * @param pullInfo {"special_rare5": ["Ruan Mei"], "special_rare4": ["March 7th", "Tingyun", "Xueyi"]}
 * @param pullType Type of the pool u want to pull
 * @param pullCount number (mainly will have 1 and 10 only.)
 * @returns 
 */
export default function makePulls(pullInfo : PullInfo, pullConfig : PullConfig, pullType : PullType, pullCount : number) : object{
    /* 暫存本次抽卡的紀錄 : 
        [
            // 距離上次抽到五星後，過了78抽，第79抽保底抽到阮．梅
            {"rare" : 5 , "itemId" : 1303, isMustGetRare : true, pullsAfterRare : 78}, 

            // 距離上次抽到四星後，過了3抽，第4抽抽到米沙
            {"rare" : 4 , "itemId" : 1312, isMustGetRare : false, pullsAfterRare : 3}, 

            // 抽到鋒鏑
            {"rare" : 3 , "itemId" : 20000, isMustGetRare : false, pullsAfterRare : -1}, 
            ...
        ]
    */
    let pullArray = []

    //抽卡基礎機率 & 抽卡保底機率
    let pullNormalRate = pullNormalRateChar, pullPityRate = pullPityRateChar

    //當前使用的抽卡機率 & 4/5星歪不歪機率
    let pullRate = pullNormalRate, pullPityOrNot = pullPityOrNotChar

    //常駐物品組合 & 限定UP物品組合 (待確認是抽到哪個星級再賦予對應組合) & 本次抽到哪個星級
    let pullNormalSet = itemRare3, pullUpSet = itemRare3, pulledItemType = "THREE"

    //常駐物品組合 & 限定UP物品組合 (待確認是抽到哪個星級再賦予對應組合)
    let pullPityMax = (pullType === PullType.PULL_LIMIT_LIGHTCONE ? pullPityMaxLC : pullPityMaxChar)

    //距上次抽到四星後多少抽 & 距上次抽到五星後多少抽
    let pullsAfterRare4 = pullConfig?.pullsAfterRare4, pullsAfterRare5 = pullConfig?.pullsAfterRare5

    //接下來必定出限定UP四星 & 接下來必定出限定UP五星
    let isMustGetRare4  = pullConfig?.isMustGetRare4, isMustGetRare5  = pullConfig?.isMustGetRare5

    //限定UP四星 & 限定UP五星 & 常駐五星判定
    const itemUpRare4 = pullInfo?.special_rare4
    const itemUpRare5 = pullInfo?.special_rare5
    const itemNormalRare5 = (pullType === PullType.PULL_LIMIT_LIGHTCONE ? itemRare5LC : itemRare5Char)

    for(let x = 0 ; x < pullCount ; x++){
        const randNum = Math.random()
        //75 + Math.random()* 15 不應該出現 但爲了避免過高機率是89
        if(randNum <= pullRate[0] && pullsAfterRare4 < 9 && pullsAfterRare5 < pullPityMax - 15 + Math.random()* 15 ){ 
            //本次抽到三星
            pulledItemType = "THREE"; //紀錄本次抽到三星
            pullNormalSet = itemRare3  //設定三星常駐部分的組合
            pullUpSet = itemRare3 //設定三星限定部分的組合
        }else if(randNum <= pullRate[0] + pullRate[1] && pullsAfterRare5 < pullPityMax - 15 + Math.random()* 15){ 
            //本次抽到四星
            pulledItemType = "FOUR"; //紀錄本次抽到哪個星級
            pullNormalSet = itemRare4Char.concat(itemRare4LC).filter((item) => !itemUpRare4.includes(item)) //扣除限定UP四星後的常駐組合
            pullUpSet = itemUpRare4 //設定限定部分的組合
            isMustGetRare4 = !isMustGetRare4 //轉換一下 - 本次是保低？
        }else{ 
            //本次抽到五星
            pulledItemType = "FIVE"; //紀錄本次抽到哪個星級
            pullNormalSet = itemNormalRare5.filter((item) => !itemUpRare5.includes(item))  //扣除限定UP五星後的常駐組合
            pullUpSet = itemUpRare5 //設定限定部分的組合
            isMustGetRare5 = !isMustGetRare5 //轉換一下 - 本次是保低？
            pullRate = (isMustGetRare5 ? pullPityRate : pullNormalRate)
        }

        const randPityOrNot = Math.random()
        const randIndex = Math.trunc(Math.random() * (
            (isMustGetRare4 && pulledItemType === "FOUR" || isMustGetRare5 && pulledItemType === "FIVE" ? pullUpSet.length : (randPityOrNot < pullPityOrNot ? pullNormalSet.length : pullUpSet.length))
        ));

        pullArray.push(
            {
                "rare" : (pulledItemType === "THREE" ? 3 : pulledItemType === "FOUR" ? 4 : 5),
                "itemId" : ((randPityOrNot < pullPityOrNot) ? ((isMustGetRare4 && pulledItemType === "FOUR" || isMustGetRare5 && pulledItemType === "FIVE") ? pullUpSet : pullNormalSet) : pullUpSet)[randIndex],
                "isMustGetRare": (pulledItemType === "THREE" ? false : pulledItemType === "FOUR" ? isMustGetRare4 : isMustGetRare5),
                "pullsAfterRare" :(pulledItemType === "THREE" ? -1 : pulledItemType === "FOUR" ? pullsAfterRare4 : pullsAfterRare5),
            }
        )
        
        pullsAfterRare4 = (pulledItemType === "FOUR" ? 0 : pullsAfterRare4 + 1)
        pullsAfterRare5 = (pulledItemType === "FIVE" ? 0 : pullsAfterRare5 + 1)
        
    }

    let pullConfigFinal = new PullConfig();
    pullConfigFinal.pullsAfterRare4 = pullsAfterRare4;
    pullConfigFinal.pullsAfterRare5 = pullsAfterRare5;
    pullConfigFinal.isMustGetRare4 = isMustGetRare4;
    pullConfigFinal.isMustGetRare5 = isMustGetRare5;
    
    return {
        pullArray,
        "pullConfig" : pullConfigFinal
    }
}