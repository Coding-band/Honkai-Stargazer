import scoreWeight from "./data/relicWeightList.json";
import demoCharData from "./data/charDataDemo.json";

export default function getRelicScore() {
  const charId = "1102";
  const charScoreWeight = scoreWeight[charId];
  const charRelicsData = demoCharData.relics;

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

  // 主詞條積分
  const relicMainScore = relicMainValue.map((main, i) => {
    // 主詞條名稱
    const name = Object.keys(main)[0];
    // 主詞條數值
    const value = main[name];
    // 主詞條權重
    const weight = relicMainScoreWeight[`${i + 1}`][name];
    return {
      [name]: weight * value,
    };
  });
  console.log(relicMainScore);
}

[
  { HPDelta: 705.6000000101048 },
  { AttackDelta: 352.8000000116881 },
  { CriticalChanceBase: 0.32399999862536205 },
  { AttackAddedRatio: 0.34560000579804484 },
  { QuantumAddedRatio: 0.38880301429889397 },
  { AttackAddedRatio: 0.43200000724755605 },
];
