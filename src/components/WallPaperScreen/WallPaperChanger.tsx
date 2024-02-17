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
import { getCharFullData } from "../../utils/data/getDataFromMap";
import officalCharId from "../../../map/character_offical_id_map";
import useHsrCharList from "../../hooks/hoyolab/useHsrCharList";
import { includes } from "lodash";
import useDelayLoad from "../../hooks/useDelayLoad";
import useIsAdmin from "../../firebase/hooks/Role/useIsAdmin";
import useIsTester from "../../firebase/hooks/Role/useIsTester";
import { dynamicHeightWallpaperChangerView } from "../../constant/ui";

export default function WallPaperChanger() {
  const loaded = useDelayLoad(100);

  const navigation = useNavigation();
  const { language: textLanguage } = useTextLanguage();
  const { language: appLanguage } = useAppLanguage();

  const playerCharIdList = useHsrCharList().data?.map((char: any) => char.id);
  const isAdmin = useIsAdmin();
  const isTester = useIsTester();

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
      )?.name
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
    Toast(
      LOCALES[appLanguage].SettingChangedWallpaper.replace(
        "${1}",
        wallPaperName
      )
    );
  };

  const handleSaveWallPaper = async (uri: string) => {
    // try {
    //   // Request device storage access permission
    //   const { status } = await MediaLibrary.requestPermissionsAsync();
    //   // Save image to media library
    //   await MediaLibrary.saveToLibraryAsync(uri);
    //   Toast(LOCALES[appLanguage].SettingSaveWallpaperSuccess); //`${wallPapers?.[currentWallPaperIndex]?.name}`
    // } catch (error) {
    //   Toast(LOCALES[appLanguage].SettingSaveWallpaperError + error);
    //    }
    Toast.StillDevelopingToast();
  };

  const { setDoHomePageUseBlurEffect, doUseHomePageBlurEffect } =
    useDoUseHomePageBlurEffect();

  return (
    <View className={dynamicHeightWallpaperChangerView}>
      <View
        style={{ flexDirection: "row", gap: 10, justifyContent: "center" }}
        className="pt-[20px] pb-[35px]"
      >
        {/* 解鎖全部 */}
        <OptionBtn
          onPress={() => {
            Toast.StillDevelopingToast();
          }}
        >
          {LOCALES[appLanguage].UnLockAll}
        </OptionBtn>
        {/* 模糊效果 */}
        <OptionBtn
          onPress={() => {
            setDoHomePageUseBlurEffect(!doUseHomePageBlurEffect);
          }}
        >
          {LOCALES[appLanguage][doUseHomePageBlurEffect ? "BlurOn" : "BlurOff"]}
        </OptionBtn>
      </View>
      {/* 壁紙切換器 */}
      <View style={{ opacity: loaded ? 1 : 0 }}>
        <WallPaperSwiper
          wallPapers={wallPapers}
          // @ts-ignore
          index={wallPapers.findIndex((w) => w.id === wallPaper?.id)}
          onIndexChange={setCurrentWallPaperIndex}
        />
      </View>
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
            disabled={!(isAdmin || isTester || playerHasCharacter)}
            onPress={() => {
              handleSaveWallPaper(wallPapers[currentWallPaperIndex].url);
            }}
          >
            {LOCALES[appLanguage].SaveWallPaper}
          </OptionBtn>
          {/* 設置 */}
          <OptionBtn
            disabled={!(isAdmin || isTester || playerHasCharacter)}
            onPress={handleSetWallPaper}
          >
            {LOCALES[appLanguage].SetWallPaper}
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
