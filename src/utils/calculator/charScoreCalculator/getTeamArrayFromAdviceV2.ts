/**
 * 之後再寫
 * @param teamStr E.g. ["嘲諷&&追加攻擊","增傷||停雲","豐饒||存護"] , 當中 "&&" 表示兩個條件必須均符合, "||" 則是任一均可
 * @returns 
 */
export function getTeamArrayFromAdviceV2(teamStr: string): number[] {
  //獲取該隊伍成員名字
  const teamArr = teamStr.split(",");
  return [getCharIdByZHName(teamArr[0]), getCharIdByZHName(teamArr[1]), getCharIdByZHName(teamArr[2])];
}

function getCharIdByZHName(zhName: string): number {
  switch (zhName) {
    case "體系核心":
      return -344;
    case "護盾":
      return -343;
    case "爆擊傷害加成":
      return -342;
    case "爆擊傷害":
      return -341;
    case "爆擊率加成":
      return -340;
    case "爆擊加成":
      return -339;
    case "爆擊":
      return -338;
    case "擴散攻擊":
      return -337;
    case "增傷":
      return -336;
    case "嘲諷":
      return -335;
    case "解除狀態":
      return -334;
    case "減傷":
      return -333;
    case "減速":
      return -332;
    case "減防":
      return -331;
    case "提前行動":
      return -330;
    case "復活":
      return -329;
    case "終結技":
      return -328;
    case "產點":
      return -327;
    case "淨化":
      return -326;
    case "逃課大師":
      return -325;
    case "追加攻擊":
      return -324;
    case "能量回復":
      return -323;
    case "特殊機制":
      return -322;
    case "弱點擊破":
      return -321;
    case "弱點植入":
      return -320;
    case "降抗":
      return -319;
    case "持續恢復":
      return -318;
    case "治療":
      return -317;
    case "易傷":
      return -316;
    case "攻擊加成":
      return -315;
    case "自我恢復":
      return -314;
    case "回復":
      return -313;
    case "再現":
      return -312;
    case "再動":
      return -311;
    case "全體傷害":
      return -310;
    case "全體治療":
      return -309;
    case "全體攻擊":
      return -308;
    case "加速":
      return -307;
    case "充能":
      return -306;
    case "代價":
      return -305;
    case "分擔傷害":
      return -304;
    case "KuruKuru":
      return -303;
    case "DOT易傷":
      return -302;
    case "DOT":
      return -301;
    case "豐饒":
      return -207;
    case "同諧":
      return -206;
    case "毀滅":
      return -205;
    case "虛無":
      return -204;
    case "智識":
      return -203;
    case "巡獵":
      return -202;
    case "存護":
      return -201;
    case "量子":
      return -107;
    case "雷":
      return -106;
    case "物理":
      return -105;
    case "虛數":
      return -104;
    case "火":
      return -103;
    case "風":
      return -102;
    case "冰":
      return -101;
    default:
      return -1;
  }
}
