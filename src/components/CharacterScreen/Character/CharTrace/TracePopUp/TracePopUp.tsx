import { Text, View } from "react-native";
import React, { useContext, useEffect, useMemo, useState } from "react";
import PopUpCard from "../../../../global/PopUpCard/PopUpCard";
import CharacterContext from "../../../../../context/CharacterData/CharacterContext";
import { HtmlText } from "@e-mine/react-native-html-text";
import FixedContext from "../../../../global/Fixed/FixedContext";
import formatDesc from "../../../../../utils/format/formatDesc";
import MaterialList from "../../../../global/MaterialList/MaterialList";
import Sliderbar from "../../../../global/Sliderbar/Sliderbar";
import { useNavigation, useRoute } from "@react-navigation/native";
import { getCharFullData } from "../../../../../utils/data/getDataFromMap";
import useCharData from "../../../../../context/CharacterData/hooks/useCharData";
import useAppLanguage from "../../../../../language/AppLanguage/useAppLanguage";
import { LOCALES } from "../../../../../../locales";

type Props = {
  type?: "inner" | "outer" | "edge";
  data: any;
  onClose: () => void;
};

export default React.memo(function TracePopUp({ type, data, onClose }: Props) {
  const navigation = useNavigation();
  const { language } = useAppLanguage();

  const { setFixed } = useContext(FixedContext)!;
  const currentRoute =
    navigation.getState().routes[navigation.getState().routes.length - 1];
  const route = useRoute();

  let skillData = null;
  if (type === "edge") {
    skillData = data?.embedBuff;
  } else if (type === "outer") {
    skillData = data?.embedBonusSkill;
  } else {
    skillData = data;
  }

  const [skillLevel, setSkillLevel] = useState(0);
  useEffect(() => {
    if (!skillData?.levelData) {
      setSkillLevel(0);
    } else if (skillLevel + 1 >= skillData?.levelData?.length) {
      setSkillLevel(skillData?.levelData?.length - 1);
    } else {
      setSkillLevel(skillLevel);
    }
  }, [skillLevel, skillData?.levelData?.length]);

  useEffect(() => {
    if (!data || currentRoute.key !== route.key) {
      setFixed(null);
    } else {
      setFixed(
        <View className="w-[350px] mb-6">
          <PopUpCard
            onClose={onClose}
            title={skillData?.name}
            content={
              <View className="px-4 py-[12px]" style={{ gap: 6 }}>
                {skillData?.typeDescHash && (
                  <View
                    className="px-3 h-[30px] bg-[#666] rounded-[40px]"
                    style={{
                      justifyContent: "center",
                      alignItems: "center",
                      alignSelf: "flex-start",
                    }}
                  >
                    <Text className="font-[HY65] text-[14px] text-white leading-5">
                      {skillData?.typeDescHash}
                    </Text>
                  </View>
                )}
                <View
                  style={{
                    flexDirection: "row",
                    justifyContent: "space-between",
                  }}
                >
                  {skillData?.tagHash && (
                    <Text className="text-[#DD8200] text-[14px] font-[HY65] leading-5">
                      {skillData?.tagHash}
                    </Text>
                  )}
                  {skillData?.energy && (
                    <Text className="text-[#666] text-[14px] font-[HY65]">
                      {LOCALES[language].TraceEnergyEarn}
                      {skillData?.energy}
                    </Text>
                  )}
                </View>
                <View
                  style={{
                    flexDirection: "row",
                    justifyContent: "space-between",
                    alignItems: "center",
                  }}
                >
                  {skillData?.levelData?.length &&
                  skillData?.levelData?.length !== 1 ? (
                    <Text className="text-[16px] text-[#222222] font-[HY65]">
                      Lv.{skillLevel + 1}/{skillData?.levelData?.length}
                    </Text>
                  ) : (
                    <Text className="text-[16px] text-[#222222] font-[HY65]">
                      Lv.1/1
                    </Text>
                  )}
                  <Sliderbar
                    point={skillData?.levelData?.length}
                    hasDot={false}
                    width={250}
                    bgColor="#00000010"
                    value={skillLevel}
                    onChange={setSkillLevel}
                  />
                </View>
                <Text
                  style={{
                    fontSize: 14,
                    color: "#666",
                    fontFamily: "HY65",
                    lineHeight: 20,
                  }}
                >
                  {skillData?.descHash && (
                    <HtmlText>
                      {formatDesc(
                        skillData?.descHash,
                        skillData?.levelData?.[skillLevel]?.params
                      )}
                    </HtmlText>
                  )}
                  {skillData?.statusList && (
                    <Text>
                      <HtmlText>{skillData?.statusList[0].key}</HtmlText>
                      <Text>{LOCALES[language].Upgrade}</Text>
                      <Text>
                        {skillData?.statusList[0].value < 1
                          ? `${(skillData?.statusList[0].value * 100).toFixed(
                              1
                            )}%`
                          : `${skillData?.statusList[0].value}點`}
                        。
                      </Text>
                    </Text>
                  )}
                </Text>
                {/* <View className="mt-[-12px]">
                  <MaterialList />
                </View> */}
              </View>
            }
          />
        </View>
      );
    }
  }, [data, skillData?.levelData?.length, skillLevel]);

  return <></>;
});
