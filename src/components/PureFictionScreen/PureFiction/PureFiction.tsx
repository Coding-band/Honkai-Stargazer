import { View, Text, Dimensions } from "react-native";
import React, { useState } from "react";
import PureFictionHeader from "../PureFictionHeader/PureFictionHeader";
import Animated, {
  useAnimatedRef,
  useScrollViewOffset,
} from "react-native-reanimated";
import Listbox from "../../global/Listbox/Listbox";
import Button from "../../global/Button/Button";
import { Image } from "expo-image";
import useAppLanguage from "../../../language/AppLanguage/useAppLanguage";
import useTextLanguage from "../../../language/TextLanguage/useTextLanguage";
import { PFVersion } from "../../../constant/pf";
import PFDataMap from "../../../../map/pure_fiction_data_map";
import ReactNativeModal from "react-native-modal";
import PopUpCard from "../../global/PopUpCard/PopUpCard";
import { LOCALES } from "../../../../locales";
import { HtmlText } from "@e-mine/react-native-html-text";
import PFLevelInfo from "./MocLevelInfo/PFLevelInfo";

export default function PureFiction() {
  const { language } = useAppLanguage();
  const { language: textLanguage } = useTextLanguage();

  const [showDetail, setShowDetail] = useState(false);

  const pfVersion = PFVersion(textLanguage);

  const [selectedVersion, setSelectedVersion] = useState(pfVersion[0].id);
  // @ts-ignore
  const pfData = PFDataMap[selectedVersion];

  const aref = useAnimatedRef<Animated.ScrollView>();
  const scrollHandler = useScrollViewOffset(aref);

  return (
    <View className="h-screen">
      <PureFictionHeader scrollHandler={scrollHandler} />
      <Animated.ScrollView
        ref={aref}
        className="z-30 pt-[110px] pb-0"
        contentContainerStyle={{ alignItems: "center", gap: 14 }}
      >
        <View style={{ flexDirection: "row", gap: 14 }} className="z-30">
          <Listbox
            button={
              <Button
                width={Dimensions.get("screen").width - 92}
                height={46}
                withArrow
              >
                <Text className="text-[16px] font-[HY65] text-[#222]">
                  {pfVersion.filter((v) => v.id === selectedVersion)[0].name}
                </Text>
              </Button>
            }
            value={selectedVersion}
            onChange={(version) => {
              setSelectedVersion(version);
            }}
          >
            {pfVersion?.map((version) => (
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
            <Image
              source={require("../../../../assets/icons/Detail.svg")}
              className="w-6 h-3"
            />
          </Button>
        </View>
        <PFLevelInfo versionNumber={selectedVersion} />
      </Animated.ScrollView>
      <ReactNativeModal
        useNativeDriverForBackdrop
        isVisible={showDetail}
        statusBarTranslucent
        deviceHeight={Dimensions.get("screen").height}
      >
        <PopUpCard
          title={LOCALES[language].MOCEffect}
          content={
            <HtmlText
              style={{ fontFamily: "HY65", padding: 16, lineHeight: 20 }}
            >
              {pfData?.desc[textLanguage]}
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
