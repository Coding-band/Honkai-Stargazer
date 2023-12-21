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

export default function WallPaperChanger() {
  const navigation = useNavigation();
  const { wallPaper, setWallPaper } = useWallPaper();

  const [currentWallPaperIndex, setCurrentWallPaperIndex] = useState(
    // @ts-ignore
    wallPaper?.id - 1
  );

  const handleSetWallPaper = () => {
    setWallPaper(wallPapers[currentWallPaperIndex].id);
    // @ts-ignore
    navigation.navigate(SCREENS.HomePage.id);
    Toast(`已切換成壁紙 ${wallPapers[currentWallPaperIndex].name}`);
  };
  const handleSaveWallPaper = async (uri: string) => {
    try {
      // Request device storage access permission
      const { status } = await MediaLibrary.requestPermissionsAsync();
      // Save image to media library
      await MediaLibrary.saveToLibraryAsync(uri);
      Toast(`已儲存壁紙 ${wallPapers[currentWallPaperIndex].name}`);
    } catch (error) {
      console.log(error);
      Toast(`壁紙儲存失敗`);
    }
  };

  return (
    <View className="w-full h-full z-30 mt-[110px]">
      <View
        style={{ flexDirection: "row", gap: 10, justifyContent: "center" }}
        className="pt-[20px] pb-[35px]"
      >
        <Button
          onPress={() => {
            Toast.StillDevelopingToast();
          }}
          width={170}
          height={46}
        >
          <Text className="text-[16px] text-[#222] font-[HY65]">Dalufishe</Text>
        </Button>
        <Button
          onPress={() => {
            Toast.StillDevelopingToast();
          }}
          width={170}
          height={46}
        >
          <Text className="text-[16px] text-[#222] font-[HY65]">
            模糊：开启
          </Text>
        </Button>
      </View>
      <WallPaperSwiper
        wallPapers={wallPapers}
        // Index number of initial slide.
        // @ts-ignore
        index={wallPaper?.id - 1}
        onIndexChange={setCurrentWallPaperIndex}
      />
      <View>
        <View className="mt-5" style={{ alignItems: "center" }}>
          <Text className="text-[16px] font-[HY65] text-[#FFF]">
            {wallPapers[currentWallPaperIndex].name}
          </Text>
        </View>
        <View
          style={{ flexDirection: "row", gap: 10, justifyContent: "center" }}
          className="pt-[35px]"
        >
          <Button
            onPress={() => {
              handleSaveWallPaper(wallPapers[currentWallPaperIndex].url);
            }}
            width={170}
            height={46}
          >
            <Text className="text-[16px] text-[#222] font-[HY65]">
              保存壁纸
            </Text>
          </Button>
          <Button onPress={handleSetWallPaper} width={170} height={46}>
            <Text className="text-[16px] text-[#222] font-[HY65]">设置</Text>
          </Button>
        </View>
      </View>
    </View>
  );
}
