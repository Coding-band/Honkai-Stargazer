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
import Listbox from "../../global/Listbox/Listbox";

export default function MOC() {
  const { language: textLanguage } = useTextLanguage();

  const [showDetail, setShowDetail] = useState(false);

  const mocVersion = [
    {
      id: 1008,
      name: `${MOCDataMap[1008].time.versionBegin} - ${MOCDataMap[1008].time.versionEnd} ${MOCDataMap[1008].name[textLanguage]}`,
    },
    {
      id: 1009,
      name: `${MOCDataMap[1009].time.versionBegin} - ${MOCDataMap[1009].time.versionEnd} ${MOCDataMap[1009].name[textLanguage]}`,
    },
  ];
  const [selectedVersion, setSelectedVersion] = useState(mocVersion[0].id);
  const mocData = MOCDataMap[selectedVersion];

  const aref = useAnimatedRef<Animated.ScrollView>();
  const scrollHandler = useScrollViewOffset(aref);

  return (
    <View>
      <MocHeader scrollHandler={scrollHandler} />
      <Animated.ScrollView
        ref={aref}
        className="z-30 pt-[110px] pb-0"
        contentContainerStyle={{ alignItems: "center", gap: 14 }}
      >
        <View style={{ flexDirection: "row", gap: 14 }} className="z-30">
          <Listbox
            button={
              <Button width={300} height={46} withArrow>
                <Text className="text-[16px] font-[HY65] text-[#222]">
                  {mocVersion.filter((v) => v.id === selectedVersion)[0].name}
                </Text>
              </Button>
            }
            value={selectedVersion}
            onChange={(version) => {
              setSelectedVersion(version);
            }}
          >
            {mocVersion?.map((version) => (
              <Listbox.Item key={version.id} value={version.id}>
                {/* @ts-ignore */}
                {version.name}
              </Listbox.Item>
            )) || []}
          </Listbox>
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
        <MocLevelInfo versionNumber={selectedVersion} />
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
            <HtmlText
              style={{ fontFamily: "HY65", padding: 16, lineHeight: 20 }}
            >
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
