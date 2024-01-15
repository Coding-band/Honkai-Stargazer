import { View, Text } from "react-native";
import React, { useContext, useState } from "react";
import PageHeading from "../../../global/PageHeading/PageHeading";
import { Info } from "phosphor-react-native";
import Sliderbar from "../../../global/Sliderbar/Sliderbar";
import LightconeContext from "../../../../context/LightconeData/LightconeContext";
import { getLcFullData } from "../../../../utils/dataMap/getDataFromMap";
import formatDesc from "../../../../utils/format/formatDesc";
import { HtmlText } from "@e-mine/react-native-html-text";
import useLcData from "../../../../context/LightconeData/hooks/useLcData";
import useAppLanguage from "../../../../language/AppLanguage/useAppLanguage";
import { LOCALES } from "../../../../../locales";

export default function LcDescription() {
  const { lcFullData } = useLcData();
  const [skillLevel, setSkillLevel] = useState(0);
  const { language } = useAppLanguage();

  return (
    <View style={{ alignItems: "center" }}>
      <PageHeading Icon={Info}>{LOCALES[language].LightconeEffect}</PageHeading>
      <View className="w-full">
        <Text className="text-text font-[HY65] text-[20px] leading-[40px]">
          {lcFullData.skill.name}
        </Text>
        <View
          className="pb-3"
          style={{
            flexDirection: "row",
            justifyContent: "space-between",
            alignItems: "center",
          }}
        >
          <Text className="text-text font-[HY65] text-[16px]">
            Lv.{skillLevel + 1}/5
          </Text>
          <Sliderbar
            point={5}
            hasDot={false}
            value={skillLevel}
            onChange={setSkillLevel}
          />
        </View>
        <HtmlText
          style={{
            color: "#FFF",
            fontFamily: "HY65",
            fontSize: 14,
            lineHeight: 20,
          }}
          // className="text-text2 font-[HY65] text-[14px]"
        >
          {formatDesc(
            lcFullData.skill.descHash,
            lcFullData.skill.levelData[skillLevel].params
          )}
        </HtmlText>
      </View>
    </View>
  );
}
