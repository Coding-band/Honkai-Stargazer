import { View } from "react-native";
import React from "react";
import PageHeading from "../../../global/PageHeading/PageHeading";
import { DiceFour, DiceTwo } from "phosphor-react-native";
import { getRelicPcData } from "../../../../utils/dataMap/getDataFromMap";
import formatDesc from "../../../../utils/format/formatDesc";
import { HtmlText } from "@e-mine/react-native-html-text";
import useRelicId from "../../../../context/RelicData/useRelicId";
import useTextLanguage from "../../../../context/TextLanguage/useTextLanguage";

export default function RelicDescription() {
  const { language: textLanguage } = useTextLanguage();
  const relicId = useRelicId();

  const relic2Pc = getRelicPcData(relicId, textLanguage).filter(
    (r) => r.useNum === 2
  )[0];

  const relic4Pc = getRelicPcData(relicId, textLanguage).filter(
    (r) => r.useNum === 4
  )[0];

  return (
    <View>
      {relic2Pc && (
        <>
          <PageHeading Icon={DiceTwo}>两件套</PageHeading>
          <HtmlText
            style={{ color: "#DDD", fontFamily: "HY65", fontSize: 14 }}
            // className="text-text2 font-[HY65] text-[14px]"
          >
            {formatDesc(relic2Pc.desc, relic2Pc?.params)}
            {/* {formatDesc(
          lcFullData.skill.descHash,
          lcFullData.skill.levelData[skillLevel].params
        )} */}
          </HtmlText>
        </>
      )}
      {relic4Pc && (
        <>
          <PageHeading Icon={DiceFour}>四件套</PageHeading>
          <HtmlText
            style={{ color: "#DDD", fontFamily: "HY65", fontSize: 14 }}
            // className="text-text2 font-[HY65] text-[14px]"
          >
            {formatDesc(relic4Pc.desc, relic4Pc?.params)}
            {/* {formatDesc(
          lcFullData.skill.descHash,
          lcFullData.skill.levelData[skillLevel].params
        )} */}
          </HtmlText>
        </>
      )}
    </View>
  );
}
