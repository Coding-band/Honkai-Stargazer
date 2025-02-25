import { View, Text, Dimensions } from "react-native";
import React, { useState } from "react";
import ReactNativeModal from "react-native-modal";
import PopUpCard from "../PopUpCard/PopUpCard";
import Button from "../Button/Button";
import {
  AppLanguage,
  Language,
  isGptTranslate,
} from "../../../language/language";
import useTextLanguage from "../../../language/TextLanguage/useTextLanguage";
import useAppLanguage from "../../../language/AppLanguage/useAppLanguage";
import useIsAcceptBindingPolicy from "../../../redux/isAcceptBindingPolicy/useIsAcceptBindingPolicy";
import Toast from "../../../utils/toast/Toast";
import { TextLanguage } from "../../../language/language.types";
import useLocalState from "../../../hooks/useLocalState";

export default function EndMessageForSG2() {
  const [isVisible, setIsVisible] = useLocalState<true | false>(
    "isStartMsgForSG3NotShow",
    true
  );
  const message = 
`· 我們已經在2024年7月31日關閉了Stargazer 2所屬資料庫伺服器，故部分功能（如排行榜）將無法使用
· 另外，Stargazer 3目前封測中，我們預計在三月中後期推出正式版本，請留意我們的Discord頻道
· 承蒙各位用戶的支持，我們會繼續延續【開拓】的精神！我們Stargazer 3 再見！

· We have shut down the database server of Stargazer 2 on July 31, so some functions (such as rankings) will not be available.
· Beside, Stargazer 3 is currently in closed beta test. We plan to launch the official version in mid-March. Please keep looking in our Discord channel.
· Thanks to the support of all users, we will continue to carry forward the spirit of [pioneering]! See you in Stargazer 3!`;
  const title = 
  `有關Stargazer 2後續安排
Regarding the follow-up arrangements for Stargazer 2`

  return (
    <ReactNativeModal
      useNativeDriverForBackdrop
      isVisible={isVisible || false}
      statusBarTranslucent
      deviceHeight={Dimensions.get("screen").height}
    >
      <PopUpCard
        title={title}
        content={
          <View className="p-4" style={{ gap: 12 }}>
            <Text>{message}</Text>
            <Button
              onPress={() => {
                setIsVisible(false);
              }}
              hasShadow={false}
              width={"100%"}
              height={46}
            >
              OK
            </Button>
          </View>
        }
      />
    </ReactNativeModal>
  );
}
