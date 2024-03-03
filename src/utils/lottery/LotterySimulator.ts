import util from "util"
//三星常駐
/**
  "鋒鏑","物穰","天傾","琥珀",
  "幽邃","齊頌","智庫","離弦",
  "嘉果","樂圮","戍禦","輪契",
  "靈鑰","相抗","俱歿","開疆",
  "匿影","淵環","蕃息","調和",
  "睿見"
 */
export const itemRare3 = [
  "Arrows", "Cornucopia", "Collapsing Sky", "Amber",
  "Void", "Chorus", "Data Bank", "Darting Arrow",
  "Fine Fruit", "Shattered Home", "Defense", "Meshing Cogs",
  "Passkey", "Adversarial", "Mutual Demise", "Pioneering",
  "Hidden Shadow", "Loop", "Multiplication", "Mediation",
  "Sagacity"
]

//四星光錐常駐
/**
   "一場術後對話","晚安與睡顏","餘生的第一天","唯有沉默",
  "記憶中的模樣","鼴鼠黨歡迎你","「我」的誕生","同一種心情",
  "獵物的視線","朗道的選擇","論劍","與行星相會",
  "秘密誓心","別讓世界靜下來","此時恰好","決心如汗珠般閃耀",
  "宇宙市場趨勢","按個追蹤吧!","舞!舞!舞!","在藍天下",
  "天才們的休憩",
 */
export const itemRare4LC = [
  "Post-Op Conversation", "Good Night and Sleep Well", "Day One of My New Life", "Only Silence Remains",
  "Memories of the Past", "The Moles Welcome You", "The Birth of the Self", "Shared Feeling",
  "Eyes of the Prey", "Landau's Choice", "Swordplay", "Planetary Rendezvous",
  "A Secret Vow", "Make the World Clamor", "Perfect Timing", "Resolution Shines As Pearls of Sweat",
  "Trend of the Universal Market", "Subscribe for More!", "Dance! Dance! Dance!", "Under the Blue Sky",
  "Geniuses' Repose",
]

//四星角色常駐
/**
  "素裳","黑塔","娜塔莎","希露瓦",
  "佩拉","桑博","虎克","艾絲妲",
  "青雀","阿蘭","馭空","盧卡",
  "丹恆","寒鴉","雪衣","玲可",
  "三月七","桂乃芬","停雲",
 */
export const itemRare4Char = [
  "Sushang", "Herta", "Natasha", "Serval",
  "Pela", "Sampo", "Hook", "Asta",
  "Qingque", "Arlan", "Yukong", "Luka",
  "Dan Heng", "Hanya", "Xueyi", "Lynx",
  "March 7th", "Guinaifen", "Tingyun",
]

//五星角色常駐
/**
  "姬子","瓦爾特","布洛妮婭","傑帕德",
  "克拉拉","彥卿","白露"
 */
export const itemRare5Char = [
  "Himeko", "Welt", "Bronya", "Gepard",
  "Clara", "Yanqing", "Bailu"
]

//五星光錐常駐
/**
  "銀河鐵道之夜","無可取代的東西","但戰鬥還未結束","以世界之名",
  "制勝的瞬間","如泥酣眠","時節不居"
 */
export const itemRare5LC = [
  "Night on the Milky Way", "Something Irreplaceable", "But the Battle Isn't Over", "In the Name of the World",
  "Moment of Victory", "Sleep Like the Dead", "Time Waits for No One"
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

export enum PullType {
  PULL_LIMIT_CHAR = "CHAR",  //限定角色池
  PULL_LIMIT_LIGHTCONE = "LIGHTCONE", //限定光錐池
  PULL_STATIC = "STATIC" //常駐池
}

export class PullInfo {
  version: string = "1.0";
  phase: number = 1;
  versionCode: number = 1001;
  type: string = "STATIC";
  special_rare5: Array<string> = [];
  special_rare4: Array<string> = [];
  title: object = { "en": "常駐", "zh_hk": "常駐" };
};

export class PullConfig {
  pullsAfterRare4: number = 0;
  pullsAfterRare5: number = 0;
  isMustGetRare4: boolean = false;
  isMustGetRare5: boolean = false;
}

export class PullResult {
  rare: number = 3;
  itemId: string = "Void";
  isMustGetRare: boolean = false;
  pullsAfterRare: number = 0;
  pullType: PullType = PullType.PULL_LIMIT_CHAR;
  unixTime: number = 0;
}

/**
 * 
 * @param pullInfo {"special_rare5": ["Ruan Mei"], "special_rare4": ["March 7th", "Tingyun", "Xueyi"]}
 * @param pullType Type of the pool u want to pull
 * @param pullCount number (mainly will have 1 and 10 only.)
 * @returns 
 */
export default function makePulls(pullInfo: PullInfo, pullConfig: PullConfig, pullCount: number): object {
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
  let pullType = pullInfo?.type as PullType
  let pullArray: Array<PullResult> = []

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
  let isMustGetRare4 = pullConfig?.isMustGetRare4, isMustGetRare5 = pullConfig?.isMustGetRare5

  //限定UP四星 & 限定UP五星 & 常駐五星判定
  const itemUpRare4 = (pullType === PullType.PULL_STATIC ? itemRare4Char.concat(itemRare4LC) : pullInfo?.special_rare4)
  const itemUpRare5 = (pullType === PullType.PULL_STATIC ? itemRare5Char.concat(itemRare5LC) : pullInfo?.special_rare5)
  const itemNormalRare5 = (pullType === PullType.PULL_STATIC ? itemRare5Char.concat(itemRare5LC) : pullType === PullType.PULL_LIMIT_LIGHTCONE ? itemRare5LC : itemRare5Char)

  for (let x = 0; x < pullCount; x++) {
    const randNum = Math.random()
    //75 + Math.random()* 15 不應該出現 但爲了避免過高機率是89
    if (randNum <= pullRate[0] && pullsAfterRare4 < 9 && pullsAfterRare5 < pullPityMax - 15 + Math.random() * 15) {
      //本次抽到三星
      pulledItemType = "THREE"; //紀錄本次抽到三星
      pullNormalSet = itemRare3  //設定三星常駐部分的組合
      pullUpSet = itemRare3 //設定三星限定部分的組合
    } else if (randNum <= pullRate[0] + pullRate[1] && pullsAfterRare5 < pullPityMax - 15 + Math.random() * 15) {
      //本次抽到四星
      pulledItemType = "FOUR"; //紀錄本次抽到哪個星級
      pullNormalSet = itemRare4Char.concat(itemRare4LC).filter((item) => !(pullType === PullType.PULL_STATIC ? false : itemUpRare4.includes(item))) //扣除限定UP四星後的常駐組合
      pullUpSet = itemUpRare4 //設定限定部分的組合
      isMustGetRare4 = !isMustGetRare4 //轉換一下 - 本次是保低？
    } else {
      //本次抽到五星
      pulledItemType = "FIVE"; //紀錄本次抽到哪個星級
      pullNormalSet = itemNormalRare5.filter((item) => !(pullType === PullType.PULL_STATIC ? false : itemUpRare5.includes(item)))  //扣除限定UP五星後的常駐組合
      pullUpSet = itemUpRare5 //設定限定部分的組合
      isMustGetRare5 = !isMustGetRare5 //轉換一下 - 本次是保低？
      pullRate = (isMustGetRare5 ? pullPityRate : pullNormalRate)
    }

    const randPityOrNot = Math.random()
    const randIndex = Math.trunc(Math.random() * (
      (isMustGetRare4 && pulledItemType === "FOUR" || isMustGetRare5 && pulledItemType === "FIVE" ? pullUpSet.length : (randPityOrNot < pullPityOrNot ? pullNormalSet.length : pullUpSet.length))
    ));

    //console.log(((randPityOrNot < pullPityOrNot) ? ((isMustGetRare4 && pulledItemType === "FOUR" || isMustGetRare5 && pulledItemType === "FIVE") ? pullUpSet : pullNormalSet) : pullUpSet))

    let pullResult = new PullResult();
    pullResult.rare = (pulledItemType === "THREE" ? 3 : pulledItemType === "FOUR" ? 4 : 5)
    pullResult.itemId = ((randPityOrNot < pullPityOrNot) ? ((isMustGetRare4 && pulledItemType === "FOUR" || isMustGetRare5 && pulledItemType === "FIVE") ? pullUpSet : pullNormalSet) : pullUpSet)[randIndex]
    pullResult.isMustGetRare = (pulledItemType === "THREE" ? false : pulledItemType === "FOUR" ? isMustGetRare4 : isMustGetRare5)
    pullResult.pullsAfterRare = (pulledItemType === "THREE" ? -1 : pulledItemType === "FOUR" ? pullsAfterRare4 : pullsAfterRare5)
    pullResult.pullType = pullType
    pullResult.unixTime = Date.now()
    pullArray.push(pullResult)

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
    "pullConfig": pullConfigFinal
  }
}