import scoreWeight from "./data/charWeightList.json";

export default function getCharScore(
  charId: string,
  charData: {
    id: string;
    name: string;
    level: number;
    lightconeId: number;
    traceLvl: {
      talent: number;
      normal_atk: number;
      skill: number;
      ultimate: number;
    };
    attr: {
    }[];
  }[]
) {
  /**
   * 初始化
   **/
  // @ts-ignore
  const charScoreWeight = scoreWeight[charId];

  // 該角色還沒有權重
  if (!charScoreWeight) {
    return {
      eachScore: null,
      totalScore: 0,
    };
  }

  // 光錐
  const charLightconeData = charData.map((char) => {
    return char.lightconeId
  });
}
