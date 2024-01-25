import scoreWeight from "./data/charWeightList.json"

export default function getCharScore(charId, charData) {
  let maxSchoolDataIndex = 0
  let schoolData = []
  for (let index = 0; index < scoreWeight[charId].length; index++) {

    //const charId = "1217";
    const schoolIndex = index //流派Index

    const lcAdviceScores = 10 //推薦光錐加分
    const lcSuitableScores = 5 //一般光錐加分
    /**
     * 初始化
     **/
    // @ts-ignore
    const charScoreWeight = scoreWeight[charId][schoolIndex]; //對應角色流派内，該角色評分權重
    const charLightconeID = charData?.light_cone?.id; //角色使用中的光錐
    const charLightconeSuper = charData?.light_cone?.rank||0; //角色使用中的光錐疊影
    const charPromotion = charData.promotion; //角色突破等級
    const charLevel = charData.level; //角色等級
    const charSoulLvl = charData.rank; //角色的星魂等級
    const charTraceLvl = charData.skills;  //角色行跡内等級

    //合拼 attributes+additions [START] { _ / omit -> lodash}
    let charAttrTMP = new Map();
    charData.attributes.map((attrs) => {
      charAttrTMP.set(attrs.field, attrs.value)
    })
    const charAttrFinal = charData.additions.map((attrs) => { //存放final合拼 attributes+additions
      return { [attrs.field]: attrs.value + (charAttrTMP.get(attrs.field) === undefined ? 0 : charAttrTMP.get(attrs.field)) }
    })
    //合拼 attributes+additions [END]

    // 該角色還沒有權重
    if (!charScoreWeight) {
      return 0
    }

    // 光錐分數 -> 加最多10+4分 (10 : 推薦 || +4 : 疊影)
    let lightconeScore = 0
    if (charLightconeID) {
      if (charScoreWeight.advice_lightcone.includes(Number(charLightconeID))) {
        lightconeScore = lcAdviceScores
      } else if (charScoreWeight.normal_lightcone.includes(Number(charLightconeID))) {
        lightconeScore = lcSuitableScores
      } else {
        lightconeScore = 0
      }
    }
    lightconeScore += Math.max(charLightconeSuper - 1,0) //疊影 = [2,3,4,5] -> 每次加1分

    // 星魂分數 -> 最多6分
    let soulScore = 0
    for (let i = 0; i < charScoreWeight.soul.length; i++) {
      if(charScoreWeight.soul[i] < 0) continue;
      if (charSoulLvl >= charScoreWeight.soul[i]) {
        soulScore += (soulScore >= 6 ? 0 : (6 / charScoreWeight.soul.length))
      } else {
        break;
      }
    }

    // 行跡分數 -> 最多34分
    let traceScore = 0
    let isChecked = [false, false, false, false];
    charTraceLvl.map((trace) => {
      switch (trace.type) {
        case "Normal": if (isChecked[0]) return; isChecked[0] = true; traceScore += charScoreWeight.trace.normal_atk * trace.level / 3; break; //Max 9*2/3 = 6
        case "Ultra": if (isChecked[1]) return; isChecked[1] = true; traceScore += charScoreWeight.trace.ultimate * trace.level / 3; break; //Max 15*2/3 = 10
        case "Talent": if (isChecked[2]) return; isChecked[2] = true; traceScore += charScoreWeight.trace.talent * trace.level / 3; break;  //Max 15*2/3 = 10
        case "BPSkill": if (isChecked[3]) return; isChecked[3] = true; traceScore += charScoreWeight.trace.skill * trace.level / 3; break;  //Max 15*2/3 = 10
        default: traceScore += 0
      }
    })
    traceScore = (traceScore > 34 ? 34 : traceScore)

    //突破分數 -> 最多6分
    let promotionScore = charPromotion

    // 屬性分數 -> 最多60分
    let attrScore = 0
    let attrWeightSum = 0 //總權重淨值 (1.5+2+1+...)

    const attrGradKeys = Object.keys(charScoreWeight.grad); //獲取所有有畢業值的屬性
    const attrWeightValidKeys = Object.keys(charScoreWeight.attr); //獲取所有有畢業值的屬性
    for (let x = 0; x < attrWeightValidKeys.length; x++) {
      if (attrGradKeys.includes(attrWeightValidKeys[x])) {
        attrWeightSum += charScoreWeight.attr[attrWeightValidKeys[x]] //計算總權重淨值 (1.5+2+1+...)
      }
    }

    charAttrFinal.map((attrs) => {
      const name = Object.keys(attrs)[0];
      const attrValue = attrs[name] + (name === "sp_rate" ? 1 : 0);
      const weightValue = charScoreWeight.attr[name];
      const gradValue = charScoreWeight.grad[name];
      /*
      attrScore += 
      (weightValue === undefined ? 0 : weightValue) 
      * (attrValue < 3 ? attrValue * 10 : (attrValue > 200 ? attrValue / 100 : attrValue / 10))
      */

      if (gradValue === undefined || weightValue === undefined) {
        //...如果沒有畢業分，做甚麼？只能不算
      } else {
        attrScore += (attrValue / gradValue) //畢業比率
          * ((0.5 * Math.pow(charLevel, 2) / 80) / 40) //角色等級Curve
          * (weightValue / attrWeightSum) * 60 //滿分的佔比
        // console.log(name + " : " + (attrValue) + " / " + (gradValue) + " || " + ((attrValue) / (gradValue)) + " || " + ((attrValue) / (gradValue)) * (weightValue / attrWeightSum) * 58) //畢業比率

      }

    })

    //最大值 120 , 畢業100
    //console.log(lightconeScore+"||"+ soulScore+"||" + traceScore +"||"+ attrScore+"||"+promotionScore)

    schoolData.push((lightconeScore + soulScore + traceScore + attrScore + promotionScore))
    if (schoolData[index] > schoolData[maxSchoolDataIndex]) {
      maxSchoolDataIndex = index;
    }
  }
  return schoolData[0]
}

export function getCharRange(score) {
  if (score < 20) { return "D" }
  else if (score < 40) { return "C" }
  else if (score < 60) { return "B" }
  else if (score < 80) { return "A" }
  else if (score < 100) { return "S" }
  return "SS"

}

export function getCurrAndGradScore(charId, charData) {
  const returnValue = [] //返回變量
  for (let index = 0; index < scoreWeight[charId].length; index++) {
    const returnValueThisSchool = []
    const charScoreWeight = scoreWeight[charId][index]; //對應角色流派内，該角色評分權重
    //合拼 attributes+additions [START] { _ / omit -> lodash}
    let charAttrTMP = new Map();
    charData.attributes.map((attrs) => {
      charAttrTMP.set(attrs.field, attrs.value)
    })
    const charAttrFinal = charData.additions.map((attrs) => { //存放final合拼 attributes+additions
      return { [attrs.field]: attrs.value + (charAttrTMP.get(attrs.field) === undefined ? 0 : charAttrTMP.get(attrs.field)) + (attrs.field === "sp_rate" ? 1 : 0) }
    })
    //合拼 attributes+additions [END]
    charAttrFinal.map((attrs) => {
      const name = Object.keys(attrs)[0];
      const attrValue = attrs[name];
      const gradValue = charScoreWeight.grad[name];
      const intList = ["atk", "def", "hp", "spd", "sp"] //只有這些才是整數 -> value
      if (gradValue === undefined || attrValue === undefined) {
        //...如果沒有畢業分，做甚麼？只能不算
      } else {
        returnValueThisSchool.push({ [name]: [attrValue, gradValue, (intList.includes(name) ? "value" : "percent")] })
      }
    })
    returnValue.push(returnValueThisSchool) //已知問題，當有第2個或以上的流派時，無法正確把該些流派的數據push入去
  }
  return returnValue;
}

/**
 * 
 * @param {*} charId 角色ID
 * @param {*} charData 角色數據
 * @returns [{"行跡名稱" : [行跡現在等級,行跡畢業等級]},...]
 * E.g. [{"Normal": [4, 4]}, {"BPSkill": [7, 6]}, {"Ultra": [6, 8]}, {"Talent": [6, 7]}]
 * 當中Normal = 普攻 ; BPSkill = 戰技 ; Ultra = 終結技 ; Talent = 天賦
 */
export function getTraceInfo(charId, charData){
  /*
  目前定義如下 : 行跡權重 = [2,1.5,1] , 對應等級將會是 = [8,7,6] (普攻會是 6,5,4) 
  指定的行跡(E.g. 終結技) 按着行跡權重 暫時釐定的畢業等級
  i.e.
  角色的終結技的行跡權重 = 2 , 終結技畢業等級 = 8
  角色的普通攻擊的行跡權重 = 1.5 , 普通攻擊畢業等級 = 5
  角色的天賦的行跡權重 = 1 , 普通攻擊畢業等級 = 6
  */
  let returnValue = [];
  for (let index = 0; index < scoreWeight[charId].length; index++) {
    let returnValueThisSchool = []
    const charScoreWeight = scoreWeight[charId][index]; //對應角色流派内，該角色評分權重
    const charTraceLvl = charData.skills;  //角色行跡内等級
    const traceGradLvlList = [8,7,6]
    let isChecked = [false, false, false, false];
    //普通攻擊
    charTraceLvl.map((trace) => {
      switch (trace.type) {
        case "Normal": if (isChecked[0]) return; isChecked[0] = true; returnValueThisSchool.push({[trace.type] : [trace.level,traceGradLvlList[2-(charScoreWeight.trace.normal_atk*2-2)] - 2]}); break;
        case "Ultra": if (isChecked[1]) return; isChecked[1] = true; returnValueThisSchool.push({[trace.type] : [trace.level,traceGradLvlList[2-(charScoreWeight.trace.ultimate*2-2)]]}); break;
        case "Talent": if (isChecked[2]) return; isChecked[2] = true;returnValueThisSchool.push({[trace.type] : [trace.level,traceGradLvlList[2-(charScoreWeight.trace.talent*2-2)]]}); break;
        case "BPSkill": if (isChecked[3]) return; isChecked[3] = true;returnValueThisSchool.push({[trace.type] : [trace.level,traceGradLvlList[2-(charScoreWeight.trace.skill*2-2)]]}); break;
      }
    })
    returnValue.push(returnValueThisSchool) //已知問題，當有第2個或以上的流派時，無法正確把該些流派的數據push入去
  }
  return returnValue
}