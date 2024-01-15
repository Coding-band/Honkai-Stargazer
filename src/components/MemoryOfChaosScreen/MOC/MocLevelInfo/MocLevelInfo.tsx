import { View, Text, TouchableOpacity, Dimensions } from "react-native";
import React, { useEffect, useState } from "react";
import { Image } from "expo-image";
import CombatType from "../../../../../assets/images/images_map/combatType";
import useTextLanguage from "../../../../language/TextLanguage/useTextLanguage";
import { LinearGradient } from "expo-linear-gradient";
import MonsterImage from "../../../../../assets/images/images_map/monsterImage";
import MOCDataMap from "../../../../../map/memory_of_chao_data_map";

export default function MocLevelInfo() {
  const mocData = MOCDataMap[1009];
  const [floor, setFloor] = useState(1);

  return (
    <View
      className="border border-[#DDDDDD20] rounded-[4px] p-2.5 w-[360px]"
      style={{ gap: 8 }}
    >
      {/* Top */}
      <View style={{ flexDirection: "row", justifyContent: "space-between" }}>
        <Text className="text-text text-[16px] font-[HY65]">關卡資訊</Text>
        <FloorOptions
          onChange={(f) => {
            setFloor(f);
          }}
        />
      </View>
      <View>
        <View style={{ gap: 8 }}>
          {/* 1-1 */}
          <Layer data={mocData?.info?.[floor - 1]} part={1} wave={1} />
          {/* 1-2 */}
          <Layer data={mocData?.info?.[floor - 1]} part={1} wave={2} />
          <View className="w-full h-[1px] bg-[#FFFFFF20]" />
          {/* 2-1 */}
          <Layer data={mocData?.info?.[floor - 1]} part={2} wave={1} />
          {/* 2-2 */}
          <Layer data={mocData?.info?.[floor - 1]} part={2} wave={2} />
        </View>
        <View></View>
      </View>
    </View>
  );
}

const Layer = ({
  data,
  part,
  wave,
}: {
  data: any;
  part: number;
  wave: number;
}) => {
  return (
    data && (
      <View
        className="h-[80px]"
        style={{ flexDirection: "row", alignItems: "center" }}
      >
        {/*  */}
        <View
          className="w-[100px]"
          style={{ alignItems: "center", justifyContent: "center" }}
        >
          <Text className="text-text text-[16px] font-[HY65]">
            {part}-{wave}
          </Text>
          <View style={{ flexDirection: "row" }}>
            <Image
              className="w-4 h-4"
              source={
                // @ts-ignore
                CombatType[data["part" + part].weakness_suggest[0]]?.icon
              }
            />
            <Image
              className="w-4 h-4"
              source={
                // @ts-ignore
                CombatType[data["part" + part].weakness_suggest[1]]?.icon
              }
            />
          </View>
        </View>
        {/*  */}
        <View style={{ flex: 1, flexDirection: "row", gap: 20 }}>
          {data["part" + part]["wave" + wave].map((monster: any) => (
            <Mob>{monster}</Mob>
          ))}
        </View>
      </View>
    )
  );
};

const Mob = ({ children }: { children: any }) => (
  <>
    <View style={{ gap: 2 }}>
      <TouchableOpacity activeOpacity={0.65}>
        <LinearGradient
          className="w-12 h-12 rounded-[4px]"
          style={{ justifyContent: "center", alignItems: "center" }}
          colors={["#78767D", "#9F9FAA"]}
        >
          <Image
          cachePolicy="none"
            transition={200}
            // @ts-ignore
            source={MonsterImage[children.monster_name]?.icon}
            className="w-9 h-9"
          />
        </LinearGradient>
      </TouchableOpacity>
      <View style={{ flexDirection: "row" }}>
        {children.monster_weakness.map((w: any, i: number) => (
          // @ts-ignore
          <Image key={i} className="w-4 h-4" source={CombatType[w]?.icon} />
        ))}
      </View>
    </View>
    {/* <ReactNativeModal
      useNativeDriverForBackdrop
      hasBackdrop={false}
      isVisible={openQuestionPopUp}
      statusBarTranslucent
      deviceHeight={Dimensions.get("screen").height}
    >
      <PopUpCard
        title={children.monster_name}
        content="為了保障安全性和用戶權益，我們通過用戶的設備（客戶端），而非透過伺服器來更新 hoyolab 角色數據。這意味著如果用戶一段時間不使用 Stargazer，可能會導致他們的角色數量、活躍天數、達成成就和忘卻之庭數據存在差異。請注意，這些數據僅供參考，不具有絕對準確性。"
        onClose={() => {
          setOpenQuestionPopUp(false);
        }}
      />
    </ReactNativeModal> */}
  </>
);

const FloorOptions = ({ onChange }: { onChange: (floor: number) => void }) => {
  const floors = [
    "其一",
    "其二",
    "其三",
    "其四",
    "其五",
    "其六",
    "其七",
    "其八",
    "其九",
    "其十",
    "其十一",
    "其十二",
  ];
  const [currentFloor, setCurrentFloor] = useState(0);
  const [open, setOpen] = useState(false);

  useEffect(() => {
    onChange(currentFloor + 1);
  }, [currentFloor]);

  return (
    <View className="z-50 w-[90px]" style={{ alignItems: "flex-end" }}>
      <TouchableOpacity
        onPress={() => {
          setOpen(!open);
        }}
        activeOpacity={0.35}
        style={{ flexDirection: "row", alignItems: "center", gap: 8 }}
      >
        <Text className="text-text text-[16px] font-[HY65]">
          {floors[currentFloor]}
        </Text>
        <Image source={require("./icons/More.svg")} className="w-3 h-1.5" />
      </TouchableOpacity>
      <View
        style={{ display: open ? "flex" : "none", gap: 8 }}
        className="absolute top-[32px] right-2 bg-[#1B0314] px-3"
      >
        {floors.map((floor, i) => (
          <TouchableOpacity
            key={i}
            activeOpacity={0.35}
            onPress={() => {
              setOpen(false);
              setCurrentFloor(i);
            }}
            style={{ alignItems: "flex-end" }}
          >
            <Text className="text-text text-[16px] font-[HY65]">{floor}</Text>
          </TouchableOpacity>
        ))}
      </View>
    </View>
  );
};
