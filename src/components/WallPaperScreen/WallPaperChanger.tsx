import { Text, View } from "react-native";
import React, { useState } from "react";
import Button from "../global/Button/Button";
import WallPaperSwiper from "./WallPaperSwiper/WallPaperSwiper";
import { wallPapers } from "../../redux/wallPaper/wallpapers";
import useWallPaper from "../../redux/wallPaper/useWallPaper";
import { useNavigation } from "@react-navigation/native";
import { SCREENS } from "../../constant/screens";
import Toast from "../../utils/toast/Toast";
import * as MediaLibrary from "expo-media-library";
import useHsrPlayerName from "../../hooks/hoyolab/useHsrPlayerName";
import { LOCALES } from "../../../locales";
import useAppLanguage from "../../language/AppLanguage/useAppLanguage";
import useDoUseHomePageBlurEffect from "../../redux/doUseHomePageBlurEffect/useDoUseHomePageBlurEffect";
import useTextLanguage from "../../language/TextLanguage/useTextLanguage";
import { getCharFullData } from "../../utils/dataMap/getDataFromMap";
import officalCharId from "../../../map/character_offical_id_map";
import useHsrCharList from "../../hooks/hoyolab/useHsrCharList";
import { hasIn, includes } from "lodash";

export default function WallPaperChanger() {
  const navigation = useNavigation();
  const { language: textLanguage } = useTextLanguage();
  const { language } = useAppLanguage();

  const playerName = useHsrPlayerName();
  const playerCharIdList = useHsrCharList().data?.map((char: any) => char.id);

  const { wallPaper, setWallPaper } = useWallPaper();
  const [currentWallPaperIndex, setCurrentWallPaperIndex] = useState(
    // @ts-ignore
    wallPapers.findIndex((w) => w.id === wallPaper?.id) || 0
  );
  const currentWallPaperId = wallPapers[currentWallPaperIndex]?.id.toString();

  const wallPaperName = officalCharId[currentWallPaperId.split("-")?.[0]]
    ? getCharFullData(
        officalCharId[currentWallPaperId.split("-")?.[0]],
        textLanguage
      )?.name + "壁紙"
    : wallPapers[currentWallPaperIndex]?.name;

  const playerHasCharacter =
    currentWallPaperId.length === 6 ||
    currentWallPaperId.length === 3 ||
    includes(playerCharIdList, Number(currentWallPaperId.split("-")[0]));

  const handleSetWallPaper = () => {
    // @ts-ignore
    setWallPaper(wallPapers[currentWallPaperIndex].id);
    // @ts-ignore
    navigation.navigate(SCREENS.HomePage.id);
    Toast(`已切換成壁紙 ${wallPaperName}`);
  };

  const handleSaveWallPaper = async (uri: string) => {
    // try {
    //   // Request device storage access permission
    //   const { status } = await MediaLibrary.requestPermissionsAsync();
    //   // Save image to media library
    //   await MediaLibrary.saveToLibraryAsync(uri);
    //   Toast(`已儲存壁紙 ${wallPapers?.[currentWallPaperIndex]?.name}`);
    // } catch (error) {
    //   Toast(`壁紙儲存失敗 ` + error);
    //    }
    Toast.StillDevelopingToast();
  };

  const { setDoHomePageUseBlurEffect, doUseHomePageBlurEffect } =
    useDoUseHomePageBlurEffect();

  return (
    <View className="w-full h-full z-30 mt-[110px]">
      <View
        style={{ flexDirection: "row", gap: 10, justifyContent: "center" }}
        className="pt-[20px] pb-[35px]"
      >
        {/*  */}
        <OptionBtn
          onPress={() => {
            Toast.StillDevelopingToast();
          }}
        >
          {LOCALES[language].UnLockAll}
        </OptionBtn>
        {/* 模糊效果 */}
        <OptionBtn
          onPress={() => {
            setDoHomePageUseBlurEffect(!doUseHomePageBlurEffect);
          }}
        >
          {LOCALES[language][doUseHomePageBlurEffect ? "BlurOn" : "BlurOff"]}
        </OptionBtn>
      </View>
      <WallPaperSwiper
        wallPapers={wallPapers}
        // @ts-ignore
        index={wallPapers.findIndex((w) => w.id === wallPaper?.id)}
        onIndexChange={setCurrentWallPaperIndex}
        // lock={!includes(playerCharIdList, wallPapers[currentWallPaperIndex].id)}
      />
      <View>
        {/* 壁紙名稱 */}
        <View className="mt-5" style={{ alignItems: "center" }}>
          <Text className="text-[16px] font-[HY65] text-[#FFF] leading-5">
            {wallPaperName}
          </Text>
        </View>
        <View
          style={{ flexDirection: "row", gap: 10, justifyContent: "center" }}
          className="pt-[35px]"
        >
          {/* 保存 */}
          <OptionBtn
            disabled={!playerHasCharacter}
            onPress={() => {
              handleSaveWallPaper(wallPapers[currentWallPaperIndex].url);
            }}
          >
            {LOCALES[language].SaveWallPaper}
          </OptionBtn>
          {/* 設置 */}
          <OptionBtn
            disabled={!playerHasCharacter}
            onPress={handleSetWallPaper}
          >
            {LOCALES[language].SetWallPaper}
          </OptionBtn>
        </View>
      </View>
    </View>
  );
}

const OptionBtn = ({
  onPress,
  children,
  disabled,
}: {
  onPress: () => void;
  children: string;
  disabled?: boolean;
}) => {
  return (
    <Button
      activeOpacity={disabled ? 1 : 0.65}
      onPress={disabled ? () => {} : onPress}
      width={170}
      height={46}
    >
      <Text
        style={{ color: disabled ? "#777" : "#222" }}
        className="text-[16px] text-[#222] font-[HY65] leading-5"
      >
        {children}
      </Text>
    </Button>
  );
};
