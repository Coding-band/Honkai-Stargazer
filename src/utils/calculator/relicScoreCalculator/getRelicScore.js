const scoreWeight = require("./data/relicWeightList.json");
const demoCharData = require("./data/charDataDemo.json");

function getRelicScore() {
  /**
  * 初始化
  **/
  const charId = "1102";
  const charScoreWeight = scoreWeight[charId];
  const charRelicsData = demoCharData.relics;
  
  //遺器的次序
  const relicOrder = ["Head","Hands","Body","Shoes","Ball","Link"]

  // 主詞條數據
  const relicMainValue = charRelicsData.map((relic) => ({
    [relic.main_affix.type]: relic.main_affix.value,
  }));
  // 副詞條數據
  const relicSubValue = charRelicsData.map((relic) =>
    relic.sub_affix.map((sub) => ({
      [sub.type]: sub.value,
    }))
  );
  // 遺器等級
  const relicLevelValue = charRelicsData.map((relic) => relic.level);

  // 主詞條權重
  const relicMainScoreWeight = charScoreWeight["main"];
  // 副詞條權重
  const relicSubScoreWeight = charScoreWeight["weight"];

  /**
  * 主詞條積分
  **/

  //這個位置要留意一下，因爲我當時沒有判斷到是否有齊6個遺器，假如資料只有0-5個遺器，一定出問題

  //主詞條分數詳情
  const relicMainScore = relicMainValue.map((main, i) => {
    // 主詞條名稱
    const name = Object.keys(main)[0];
    // 主詞條數值 -- 算式不需使用
    const value = main[name];
    // 主詞條權重
    const weight = relicMainScoreWeight[`${i + 1}`][name];

    //按遺器位置内、主詞條加成和 [等級] (特定情況下) 計算積分
    switch (i) {
      case 0:
      case 1:
        //return { [name[i]]: 0 };
        return { [relicOrder[i]]: 0 };

      case 2:
      case 4:
        return { [relicOrder[i]]: Math.min((5.83 * weight) + relicLevelValue[i]*0.66 , 5.83)+10 };

      case 3:
      case 5 : 
        return { [relicOrder[i]]: (5.83 * weight)}
        
      default : 
        return {[relicOrder[i]]:0}
    }
  });

  /**
  * 副詞條積分
  **/

  //各個遺器内所有副詞條總分
  const relicSubFinalScore = [0,0,0,0,0,0]

  //這個位置要留意一下，因爲我當時沒有判斷到是否有齊6個遺器，假如資料只有0-5個遺器，一定出問題
  //副詞條分數詳情
  const relicSubScore = relicSubValue.map((main, i) => {
    return main.map((sub,j) => {
      // 副詞條名稱
      const name = Object.keys(sub)[0];
      // 副詞條數值
      const attrValue = sub[name];
      // 副詞條權重 - 四捨五入補償
      const charAttrWeight = ((relicSubScoreWeight[name] == 0.3 || relicSubScoreWeight[name] == 0.8) ? relicSubScoreWeight[name] - 0.05 : relicSubScoreWeight[name]);
      // 臨時計分用
      let tmpScore = 0

      //按副詞條加成和特定倍率計算積分
      //AddedRatio -> % (要乘100) || Delta -> Integer
      switch(name){
        case "AttackAddedRatio" : 	{tmpScore =  attrValue *100 * 1.5 * charAttrWeight; break;}
        case "AttackDelta" : 	{tmpScore =  attrValue * 0.3*0.5 * charAttrWeight; break;}
        case "DefenceAddedRatio" : 	{tmpScore =  attrValue*100 * 1.19 * charAttrWeight; break;}
        case "DefenceDelta" : 	{tmpScore =  attrValue * 0.3*0.5 * charAttrWeight; break;}
        case "HPAddedRatio" : 	{tmpScore =  attrValue*100 * 1.5 * charAttrWeight; break;}
        case "HPDelta" : 	{tmpScore =  attrValue * 0.153*0.5 * charAttrWeight; break;}
        case "SpeedDelta" : 	{tmpScore =  attrValue * 2.53 * charAttrWeight; break;}
        case "PhysicalAddedRatio" : 	{tmpScore =  attrValue*100 *  charAttrWeight; break;}
        case "FireAddedRatio" : 	{tmpScore =  attrValue*100 *  charAttrWeight; break;}
        case "IceAddedRatio" : 	{tmpScore =  attrValue*100 *  charAttrWeight; break;}
        case "ThunderAddedRatio" : 	{tmpScore =  attrValue*100 *  charAttrWeight; break;}
        case "WindAddedRatio" : 	{tmpScore =  attrValue*100 *  charAttrWeight; break;}
        case "QuantumAddedRatio" : 	{tmpScore =  attrValue *  charAttrWeight; break;}
        case "ImaginaryAddedRatio" : 	{tmpScore =  attrValue*100 *  charAttrWeight; break;}
        case "CriticalChanceBase" : 	{tmpScore =  attrValue*100 * 2 * charAttrWeight; break;}
        case "CriticalDamageBase" : 	{tmpScore =  attrValue*100 * 1 * charAttrWeight; break;}
        case "BreakDamageAddedRatioBase" : 	{tmpScore =  attrValue*100 * 1 * charAttrWeight; break;}
        case "StatusProbabilityBase" : 	{tmpScore =  attrValue*100 * 1.49 * charAttrWeight; break;}
        case "StatusResistanceBase" : 	{tmpScore =  attrValue*100 * 1.49 * charAttrWeight; break;}

      }
      //把得分放入指定遺器内副詞條總分
      relicSubFinalScore[i] += tmpScore
      return {[relicOrder[i]+"_sub"+j] : tmpScore}
    })
  });

  //單一遺器的總分
  const relicEachFinalScore = relicSubFinalScore.map((val,i) => {
    return {[relicOrder[i]]:(val+relicMainScore[i][relicOrder[i]])}
  })
  
  //所有遺器合共的總分 !!!
  let relicAllFinalScore = 0

  //計算遺器們合共的總分
  relicEachFinalScore.map((val,i) => {
    relicAllFinalScore += val[relicOrder[i]]
  })

  //你只需要relicAllFinalScore 去計算評價等級 (等級範圍明天聊)
  //return relicAllFinalScore
}

getRelicScore();
