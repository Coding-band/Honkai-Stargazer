import { Text, View } from "react-native";
import React, { useContext, useEffect, useMemo, useState } from "react";
import PopUpCard from "../../../../global/PopUpCard/PopUpCard";
import CharacterContext from "../../../../../context/CharacterContext";
import { HtmlText } from "@e-mine/react-native-html-text";
import FixedContext from "../../../../global/Fixed/FixedContext";
import formatDesc from "../../../../../utils/formatDesc";
import MaterialList from "../../../../global/MaterialList/MaterialList";
import Sliderbar from "../../../../global/Sliderbar/Sliderbar";
import { useNavigation, useRoute } from "@react-navigation/native";
import { getCharFullData } from "../../../../../utils/getDataFromMap";

type Props = {
  id: number;
  onClose: () => void;
};

export default React.memo(function TracePopUp({ id, onClose }: Props) {
  const charData = useContext(CharacterContext);
  const charId = charData?.id!;
  const charFullData = getCharFullData(charId);
  const charSkillGrouping = charFullData?.skillGrouping;
  const charSkill = useMemo(
    () =>
      charFullData?.skills?.filter(
        (skill) => skill.id === (id > 0 && charSkillGrouping?.[id - 1][0])
      )[0],
    [charFullData, id, charSkillGrouping]
  );

  const [skillLevel, setSkillLevel] = useState(0);

  const { setFixed } = useContext(FixedContext)!;
  const navigation = useNavigation();
  const currentRoute =
    navigation.getState().routes[navigation.getState().routes.length - 1];
  const route = useRoute();

  useEffect(() => {
    if (!charSkill || currentRoute.key !== route.key) {
      setFixed(null);
    } else {
      setFixed(
        <View className="w-[350px] mb-6">
          <PopUpCard
            onClose={onClose}
            title={charSkill.name}
            content={
              <View className="px-4 py-[12px]" style={{ gap: 6 }}>
                <View
                  className="w-[70px] h-[30px] bg-[#666] rounded-[40px]"
                  style={{ justifyContent: "center", alignItems: "center" }}
                >
                  <Text className="font-[HY65] text-[14px] text-white">
                    {charSkill.typeDescHash}
                  </Text>
                </View>
                <View
                  style={{
                    flexDirection: "row",
                    justifyContent: "space-between",
                  }}
                >
                  <Text className="text-[#DD8200] text-[14px] font-[HY65]">
                    {charSkill.tagHash}
                  </Text>
                  <Text className="text-[#666] text-[14px] font-[HY65]">
                    能量回复：{charSkill.energy}
                  </Text>
                </View>
                <View
                  style={{
                    flexDirection: "row",
                    justifyContent: "space-between",
                    alignItems:"center",
                  }}
                >
                  <Text className="text-[16px] text-[#222222]">
                    Lv.{skillLevel + 1}/{charSkill.levelData.length}
                  </Text>
                  <Sliderbar
                    point={charSkill.levelData.length}
                    hasDot={false}
                    width={250}
                    bgColor="#00000010"
                    value={skillLevel}
                    onChange={setSkillLevel}
                  />
                </View>
                <HtmlText
                  style={{ fontSize: 14, color: "#666", fontFamily: "HY65" }}
                >
                  {formatDesc(
                    charSkill.descHash,
                    charSkill.levelData[skillLevel]?.params
                  )}
                </HtmlText>
                <View className="mt-[-12px]">
                  <MaterialList />
                </View>
              </View>
            }
          />
        </View>
      );
    }
  }, [charSkill, charSkill?.levelData?.length, skillLevel, id]);

  return <></>;
});
