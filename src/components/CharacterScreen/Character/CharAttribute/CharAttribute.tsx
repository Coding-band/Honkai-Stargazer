import React, { useEffect, useState } from "react";
import { View, Text, ScrollView } from "react-native";
import { Info } from "phosphor-react-native";
import AttrSliderbar from "../../../global/Sliderbar/Sliderbar";
import { Image } from "expo-image";
import CharPageHeading from "../../../global/PageHeading/PageHeading";
import useDelayLoad from "../../../../hooks/useDelayLoad";
import { getCharAttrData } from "../../../../utils/calculator/getAttrData";
import useCharData from "../../../../context/CharacterData/hooks/useCharData";
import MaterialCard from "../../../global/MaterialCard/MaterialCard";
import {
  MaterialCount,
  getCharMaterialData,
} from "../../../../utils/calculator/getMaterialData";
import { map } from "lodash";
import Material from "../../../../../assets/images/images_map/material";
import useAppLanguage from "../../../../language/AppLanguage/useAppLanguage";
import { LOCALES } from "../../../../../locales";
import AttributeImage from "../../../../../assets/images/images_map/attributeImage";

const AggroIcon = require("../../../../../assets/icons/Aggro.png");
const DownArrowIcon = require("../../../../../assets/icons/DownArrow.svg");

export default React.memo(function CharAttribute() {
  const loaded = useDelayLoad(100);
  const { language } = useAppLanguage();

  const { charId, charFullData } = useCharData();

  const [attrFromLevel, setAttrFromLevel] = useState(0);
  const [attrToLevel, setAttrToLevel] = useState(8);

  const [attributes, setAttributes] = useState({
    atk: 0,
    hp: 0,
    def: 0,
    speed: 0,
    energy: 0,
    aggro: 0,
  });

  const [materials, setMaterials] = useState<MaterialCount>();

  useEffect(() => {
    setTimeout(() => {
      setAttributes(
        getCharAttrData(charId, attrFromLevel === 0 ? 1 : attrFromLevel * 10)
      );
    });
  }, [charId, attrFromLevel]);

  useEffect(() => {
    setTimeout(() => {
      setMaterials(
        getCharMaterialData(
          charId,
          attrFromLevel === 0 ? 1 : attrFromLevel * 10,
          attrToLevel === 0 ? 1 : attrToLevel * 10
        )
      );
    });
  }, [charId, attrFromLevel, attrToLevel]);

  const handleFromLevelChange = (newLevel: number) => {
    if (newLevel >= attrToLevel) {
      setAttrFromLevel(attrToLevel);
      return;
    }
    setAttrFromLevel(newLevel);
  };

  const handleToLevelChange = (newLevel: number) => {
    if (newLevel <= attrFromLevel) {
      setAttrToLevel(attrFromLevel);
      return;
    }
    setAttrToLevel(newLevel);
  };

  return (
    <>
      <View className="px-6" style={{ alignItems: "center" }}>
        <CharPageHeading Icon={Info}>
          {LOCALES[language].BasicStatus}
        </CharPageHeading>
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
                Lv.{attrFromLevel === 0 ? 1 : attrFromLevel * 10}
              </Text>
              {/* 等級滑動欄 */}
              <AttrSliderbar
                point={9}
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
                Lv.{attrToLevel === 0 ? 1 : attrToLevel * 10}
              </Text>
              {/* 等級滑動欄 */}
              <AttrSliderbar
                point={9}
                value={attrToLevel}
                onChange={handleToLevelChange}
              />
            </View>
            {/* 屬性數值 */}
            <View className="mt-4" style={{ flexDirection: "row", gap: 12 }}>
              <View style={{ flexDirection: "row", alignItems: "center" }}>
                <Image className="w-6 h-6" source={AttributeImage.hp} />
                <Text className="text-white text-[16px] font-medium">
                  {attributes.hp.toFixed(0)}
                </Text>
              </View>
              <View style={{ flexDirection: "row", alignItems: "center" }}>
                <Image className="w-6 h-6" source={AttributeImage.atk} />
                <Text className="text-white text-[16px] font-medium">
                  {attributes.atk.toFixed(0)}
                </Text>
              </View>
              <View style={{ flexDirection: "row", alignItems: "center" }}>
                <Image className="w-6 h-6" source={AttributeImage.def} />
                <Text className="text-white text-[16px] font-medium">
                  {attributes.def.toFixed(0)}
                </Text>
              </View>
              <View style={{ flexDirection: "row", alignItems: "center" }}>
                <Image className="w-6 h-6" source={AttributeImage.spd} />
                <Text className="text-white text-[16px] font-medium">
                  {attributes.speed.toFixed(0)}
                </Text>
              </View>
              <View style={{ flexDirection: "row", alignItems: "center" }}>
                <Image className="w-6 h-6" source={AggroIcon} />
                <Text className="text-white text-[16px] font-medium">
                  {attributes.aggro.toFixed(0)}
                </Text>
              </View>
            </View>
          </>
        )}
      </View>
      <ScrollView horizontal className="mt-5 mx-6">
        <View style={{ flexDirection: "row", gap: 14 }}>
          {map(materials, (v, k) => {
            return (
              <MaterialCard
                key={k}
                count={v}
                // @ts-ignore
                stars={charFullData.itemReferences[k].rarity}
                // @ts-ignore
                image={Material[k]}
              />
            );
          })}
        </View>
      </ScrollView>
    </>
  );
});
