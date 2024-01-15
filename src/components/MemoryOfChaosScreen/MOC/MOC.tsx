import { View, Text, Dimensions } from "react-native";
import React, { useState } from "react";
import Animated, {
  useAnimatedRef,
  useScrollViewOffset,
} from "react-native-reanimated";
import MocHeader from "../MocHeader/MocHeader";
import useTextLanguage from "../../../language/TextLanguage/useTextLanguage";
import MocLevelInfo from "./MocLevelInfo/MocLevelInfo";
import Button from "../../global/Button/Button";
import MOCDataMap from "../../../../map/memory_of_chao_data_map";
import { Image } from "expo-image";
import ReactNativeModal from "react-native-modal";
import PopUpCard from "../../global/PopUpCard/PopUpCard";
import { HtmlText } from "@e-mine/react-native-html-text";

export default function MOC() {
  const { language: textLanguage } = useTextLanguage();

  const mocData = MOCDataMap[1009];

  const aref = useAnimatedRef<Animated.ScrollView>();
  const scrollHandler = useScrollViewOffset(aref);

  const [showDetail, setShowDetail] = useState(false);

  return (
    <View>
      <MocHeader scrollHandler={scrollHandler} />
      <Animated.ScrollView
        ref={aref}
        className="z-30 pt-[110px] pb-0"
        contentContainerStyle={{ alignItems: "center", gap: 14 }}
      >
        <View style={{ flexDirection: "row", gap: 14 }}>
          <Button width={300} height={46}>
            <Text className="text-[16px] font-[HY65] text-[#222]">
              {mocData?.name?.[textLanguage]}
            </Text>
          </Button>
          <Button
            onPress={() => {
              setShowDetail(true);
            }}
            width={46}
            height={46}
          >
            <Image source={require("./icons/Detail.svg")} className="w-6 h-3" />
          </Button>
        </View>
        <MocLevelInfo />
      </Animated.ScrollView>
      <ReactNativeModal
        useNativeDriverForBackdrop
        isVisible={showDetail}
        statusBarTranslucent
        deviceHeight={Dimensions.get("screen").height}
      >
        <PopUpCard
          title="效果"
          content={
            <HtmlText style={{ fontFamily: "HY65", padding: 16 ,lineHeight:20}}>
              {mocData?.desc[textLanguage]}
            </HtmlText>
          }
          onClose={() => {
            setShowDetail(false);
          }}
        />
      </ReactNativeModal>
    </View>
  );
}
