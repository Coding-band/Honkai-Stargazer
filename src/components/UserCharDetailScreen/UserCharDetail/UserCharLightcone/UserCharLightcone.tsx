import { View, Text, TouchableOpacity } from "react-native";
import React from "react";
import useProfileHsrInGameInfo from "../../../../context/UserCharDetailData/hooks/useProfileHsrInGameInfo";
import UserCharDetailStars from "../UserCharDetailStars/UserCharDetailStars";
import { Image } from "expo-image";
import officalLightconeId from "../../../../../map/lightcone_offical_id_map";
import Lightcone from "../../../../../assets/images/images_map/lightcone";
import { LightconeName } from "../../../../types/lightcone";
import useTextLanguage from "../../../../language/TextLanguage/useTextLanguage";
import {
  getLcFullData,
  getLcJsonData,
} from "../../../../utils/dataMap/getDataFromMap";
import LightconePath from "./LightconePath/LightconePath";
import LightconeAttribute from "./LightconeAttribute/LightconeAttribute";
import LightconeLevel from "./LightconeLevel/LightconeLevel";
import { HtmlText } from "@e-mine/react-native-html-text";
import formatDesc from "../../../../utils/format/formatDesc";
import useLocalState from "../../../../hooks/useLocalState";
import { animated, useSpring } from "@react-spring/native";

export default function UserCharLightcone() {
  const { language } = useTextLanguage();

  const { inGameCharData } = useProfileHsrInGameInfo();
  const lightconeId = officalLightconeId[
    inGameCharData.light_cone.id
  ] as LightconeName;
  const lcJsonData = getLcJsonData(lightconeId);
  const lcFullData = getLcFullData(lightconeId, language);
  const lcInGameData = inGameCharData.light_cone;

  const [displayMode, setDisplayMode] = useLocalState<"light" | "normal">(
    "user-char-detail-page-lightcone-display-mode",
    "normal"
  );

  const lightconeImageAnimationRotate = useSpring({
    rotate: displayMode === "normal" ? "5deg" : "0deg",
  });
  const lightconeImageAnimationScale = useSpring({
    scale: displayMode === "normal" ? 1 : 1.25,
  });

  return (
    <View style={{ alignItems: "center" }}>
      <View className="w-[135px] h-[1px] bg-[#F3F9FF40]"></View>
      <TouchableOpacity
        activeOpacity={0.35}
        onPress={() => {
          setDisplayMode(displayMode === "light" ? "normal" : "light");
        }}
      >
        <View
          className="py-[18px]"
          style={{
            flexDirection: "row",
            alignItems: "center",

            gap: 40,
          }}
        >
          <AnimatedImage
            className="w-[72px] h-[106px] border-4 border-[#FFF]"
            style={{
              transform: [
                lightconeImageAnimationRotate,
                lightconeImageAnimationScale,
              ],
            }}
            source={Lightcone[lightconeId].imageFull}
            contentFit="contain"
          />
          <View style={{ gap: 2, alignItems: "flex-start" }}>
            <Text className="text-[20px] font-[HY65] text-text">
              {lcFullData.name}
            </Text>
            <LightconeLevel
              lcId={lightconeId}
              lcFullData={lcFullData}
              lcInGameData={lcInGameData}
            />
            {displayMode === "light" ? (
              <>
                <HtmlText
                  style={{
                    color: "#DDD",
                    fontFamily: "HY65",
                    width: 220,
                    lineHeight:20,
                  }}
                >
                  {formatDesc(
                    lcFullData.skill.descHash,
                    lcFullData.skill.levelData[lcInGameData.rank - 1].params
                  )}
                </HtmlText>
              </>
            ) : (
              <>
                <UserCharDetailStars count={lcInGameData.rarity} />
                <LightconePath lcId={lightconeId} lcJsonData={lcJsonData} />
                <LightconeAttribute
                  lcId={lightconeId}
                  lcFullData={lcFullData}
                  lcInGameData={lcInGameData}
                />
              </>
            )}
          </View>
        </View>
      </TouchableOpacity>
      <View className="w-[135px] h-[1px] bg-[#F3F9FF40]"></View>
    </View>
  );
}

const AnimatedImage = animated(Image);
