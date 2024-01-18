const scoreWeight = require("./data/charWeightList.json");
const demoCharData = require("./data/charDataDemo.json");

function getCharScore() {
  const charId = "1102";
  const schoolIndex = 0 //流派Index

  const lcAdviceScores = 10 //推薦光錐加分
  const lcSuitableScores = 5 //一般光錐加分
  /**
   * 初始化
   **/
  // @ts-ignore
  const charScoreWeight = scoreWeight[charId][schoolIndex];
  const charLightconeID = demoCharData.light_cone.id;
  const charSoulLvl = demoCharData.rank;
  const charTraceLvl = demoCharData.skills;
  let charAttrTMP = new Map();
  demoCharData.attributes.map((attrs) => {
    charAttrTMP.set(attrs.field,attrs.value)
  })
  const charAttrFinal = demoCharData.additions.map((attrs) => {
    return {[attrs.field] : attrs.value + (charAttrTMP.get(attrs.field) === undefined ? 0 : charAttrTMP.get(attrs.field))}
  })

  // 該角色還沒有權重
  if (!charScoreWeight) {
    return {
      eachScore: null,
      totalScore: 0,
    };
  }

  // 光錐分數 -> 加最多10分
  let lightconeScore = 0
  if(charScoreWeight.advice_lightcone.includes(charLightconeID)){
    lightconeScore = lcAdviceScores
  }else if(charScoreWeight.normal_lightcone.includes(charLightconeID)){
    lightconeScore = lcSuitableScores
  }else{
    lightconeScore = 0
  }

  // 星魂分數 -> 每級+5分, 最多20分
  let soulScore = 0
  for(let i = 0 ; i < charScoreWeight.soul.length ; i++){
    if(charSoulLvl >= charScoreWeight.soul[i]){
      soulScore += (soulScore >= 20 ? 0 : 5)
    }else{
      break;
    }
  }

  // 行跡分數 -> 最多36分(沒什麼可能達到)
  let traceScore = 0
  charTraceLvl.map((trace) => {
    switch(trace.type){
      case "Normal" : traceScore += charScoreWeight.trace.normal_atk * trace.level /3; //Max 9*2/3 = 6
      case "Ultra" : traceScore += charScoreWeight.trace.ultimate * trace.level /3; //Max 15*2/3 = 10
      case "Talent" : traceScore += charScoreWeight.trace.talent * trace.level /3;  //Max 15*2/3 = 10
      case "BPSkill" : traceScore += charScoreWeight.trace.skill * trace.level /3;  //Max 15*2/3 = 10
      default : traceScore += 0
    }
  })

  // 屬性分數 -> 最多??分
  let attrScore = 0
  charAttrFinal.map((attrs) => {
    const name = Object.keys(attrs)[0];
    const attrValue = attrs[name];
    const weightValue = charScoreWeight.attr[name];

    console.log(name + ":" + (weightValue === undefined ? 0 : weightValue) 
    * (attrValue < 3 ? attrValue * 10 : (attrValue > 200 ? attrValue / 100 : attrValue / 10)))

    attrScore += 
    (weightValue === undefined ? 0 : weightValue) 
    * (attrValue < 3 ? attrValue * 10 : (attrValue > 200 ? attrValue / 100 : attrValue / 10))
  })
  
  return lightconeScore + soulScore + traceScore + attrScore
}

console.log(getCharScore())