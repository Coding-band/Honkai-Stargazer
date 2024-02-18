import { View, Text, Dimensions } from "react-native";
import React, { useState } from "react";
import ReactNativeModal from "react-native-modal";
import PopUpCard from "../PopUpCard/PopUpCard";
import Button from "../Button/Button";
import { AppLanguage, Language, isGptTranslate } from "../../../language/language";
import useTextLanguage from "../../../language/TextLanguage/useTextLanguage";
import useAppLanguage from "../../../language/AppLanguage/useAppLanguage";
import useIsAcceptBindingPolicy from "../../../redux/isAcceptBindingPolicy/useIsAcceptBindingPolicy";
import Toast from "../../../utils/toast/Toast";
import { TextLanguage } from "../../../language/language.types";

const appLanguages = AppLanguage.filter(
  (lang) => lang !== "vocchinese" && lang !== "jyu_yam"
).map((lan) => ({
  name: Language[lan]+ (isGptTranslate[lan] ? "（GPT）" : ""),
  value: lan,
}));

export default function SelectLanguageAtFirstTime() {
  const { isAcceptBindingPolicy, setIsAcceptBindingPolicy } =
    useIsAcceptBindingPolicy();

  const [isVisible, setIsVisible] = useState(true);

  const { setLanguage: setTextLanguage } = useTextLanguage();
  const { setLanguage: setAppLanguage } = useAppLanguage();

  return (
    <ReactNativeModal
      useNativeDriverForBackdrop
      isVisible={!isAcceptBindingPolicy}
      statusBarTranslucent
      deviceHeight={Dimensions.get("screen").height}
    >
      <PopUpCard
        title="Select Language"
        content={
          <View className="p-4" style={{ gap: 12 }}>
            {appLanguages.map((lang) => (
              <Button key={lang.name}
                onPress={() => {
                  if((lang.name !== "vocchinese" && lang.name !== "jyu_yam") && (lang.value !== undefined)&& (lang !== undefined)){
                    setTextLanguage!(lang.value as TextLanguage);
                  }
                  setAppLanguage(lang.value);
                  setIsAcceptBindingPolicy(true);
                }}
                hasShadow={false}
                width={"100%"}
                height={46}
              >
                {lang.name}
              </Button>
            ))}
          </View>
        }
      />
    </ReactNativeModal>
  );
}
