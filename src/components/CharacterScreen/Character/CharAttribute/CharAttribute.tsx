import React, { useState } from "react";
import { View, Text } from "react-native";
import { Info } from "phosphor-react-native";
import AttrSliderbar from "../../../global/Sliderbar/Sliderbar";
import { Image } from "expo-image";
import CharPageHeading from "../../../global/PageHeading/PageHeading";
import useDelayLoad from "../../../../hooks/useDelayLoad";
import MaterialList from "../../../global/MaterialList/MaterialList";

const HPIcon = require("../../../../../assets/icons/HP.png");
const STRIcon = require("../../../../../assets/icons/STR.png");
const DEFIcon = require("../../../../../assets/icons/DEF.png");
const DEXIcon = require("../../../../../assets/icons/DEX.png");
const ELIcon = require("../../../../../assets/icons/EL.png");

const DownArrowIcon = require("../../../../../assets/icons/DownArrow.svg");

export default function CharAttribute() {
  const loaded = useDelayLoad(100);

  const [attrFromLevel, setAttrFromLevel] = useState(0);
  const [attrToLevel, setAttrToLevel] = useState(8);

  const handleFromLevelChange = (newLevel: number) => {
    if (newLevel >= attrToLevel) {
      setAttrFromLevel(attrToLevel - 1);
      return;
    }
    setAttrFromLevel(newLevel);
  };

  const handleToLevelChange = (newLevel: number) => {
    if (newLevel <= attrFromLevel) {
      setAttrToLevel(attrFromLevel + 1);
      return;
    }
    setAttrToLevel(newLevel);
  };
  return (
    <>
      <View style={{ alignItems: "center" }}>
        <CharPageHeading Icon={Info}>基础属性</CharPageHeading>
        {loaded && (
          <>
            {/* 等級 - 起點 */}
            <View
              className="w-full"
              style={{
                flexDirection: "row",
                alignItems: "center",
                justifyContent: "space-between",
              }}
            >
              {/* 等級 */}
              <Text className="text-white text-[16px] font-medium">
                Lv.{attrFromLevel === 0 ? "1" : attrFromLevel * 10}
              </Text>
              {/* 等級滑動欄 */}
              <AttrSliderbar
                value={attrFromLevel}
                onChange={handleFromLevelChange}
              />
            </View>
            <Image
              className="w-[10px] h-[10px] my-[5px] ml-[5px]"
              style={{ alignSelf: "flex-start" }}
              source={DownArrowIcon}
            />

            {/* 等級 - 終點 */}
            <View
              className="w-full"
              style={{
                flexDirection: "row",
                alignItems: "center",
                justifyContent: "space-between",
              }}
            >
              {/* 等級 */}
              <Text className="text-white text-[16px] font-medium">
                Lv.{attrToLevel === 0 ? "1" : attrToLevel * 10}
              </Text>
              {/* 等級滑動欄 */}
              <AttrSliderbar
                value={attrToLevel}
                onChange={handleToLevelChange}
              />
            </View>
            {/* 屬性數值 */}
            <View className="mt-4" style={{ flexDirection: "row", gap: 12 }}>
              <View style={{ flexDirection: "row", alignItems: "center" }}>
                <Image className="w-6 h-6" source={HPIcon} />
                <Text className="text-white text-[16px] font-medium">817</Text>
              </View>
              <View style={{ flexDirection: "row", alignItems: "center" }}>
                <Image className="w-6 h-6" source={STRIcon} />
                <Text className="text-white text-[16px] font-medium">561</Text>
              </View>
              <View style={{ flexDirection: "row", alignItems: "center" }}>
                <Image className="w-6 h-6" source={DEFIcon} />
                <Text className="text-white text-[16px] font-medium">319</Text>
              </View>
              <View style={{ flexDirection: "row", alignItems: "center" }}>
                <Image className="w-6 h-6" source={DEXIcon} />
                <Text className="text-white text-[16px] font-medium">115</Text>
              </View>
              <View style={{ flexDirection: "row", alignItems: "center" }}>
                <Image className="w-6 h-6" source={ELIcon} />
                <Text className="text-white text-[16px] font-medium">120</Text>
              </View>
            </View>
          </>
        )}
      </View>
      <MaterialList />
    </>
  );
}
