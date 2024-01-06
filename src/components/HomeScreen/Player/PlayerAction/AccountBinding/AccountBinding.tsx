import { View, Pressable, Dimensions, Keyboard } from "react-native";
import React, { useEffect, useState } from "react";
import LoginPolicy from "./LoginPolicy/LoginPolicy";
import ReactNativeModal from "react-native-modal";
import PopUpCard from "../../../../global/PopUpCard/PopUpCard";
import ToLoginScreen from "./ToLoginScreen/ToLoginScreen";
import ManualEnterCookie from "./ManualEnterCookie/ManualEnterCookie";
import useIsAcceptBindingPolicy from "../../../../../redux/isAcceptBindingPolicy/useIsAcceptBindingPolicy";
import useAppLanguage from "../../../../../language/AppLanguage/useAppLanguage";
import { LOCALES } from "../../../../../../locales";

export default function AccountBinding() {
  const [isVisable, setIsVisable] = useState(true);
  const { language } = useAppLanguage();

  const { isAcceptBindingPolicy, setIsAcceptBindingPolicy } =
    useIsAcceptBindingPolicy();
  const [isManualEnterCookie, setIsManualEnterCookie] = useState(false);

  return (
    <ReactNativeModal
      useNativeDriverForBackdrop
      isVisible={isVisable}
      statusBarTranslucent
      deviceHeight={Dimensions.get("screen").height}
    >
      <Pressable
        onPress={Keyboard.dismiss}
        style={{
          flex: 1,
          justifyContent: "center",
          alignItems: "center",
          gap: 24,
          transform: [{ translateY: 24 }],
        }}
      >
        <PopUpCard
          onClose={() => {
            setIsVisable(false);
          }}
          title={
            isAcceptBindingPolicy
              ? LOCALES[language].SelectServerTitle
              : LOCALES[language].RemarksInLogin
          }
          content={
            <View className="p-4">
              {isAcceptBindingPolicy ? (
                !isManualEnterCookie ? (
                  <ToLoginScreen
                    onServerChosen={() => {
                      setIsVisable(false);
                    }}
                    onCookieChosen={() => {
                      setIsManualEnterCookie(true);
                    }}
                  />
                ) : (
                  <ManualEnterCookie
                    onCookieSave={() => {
                      setIsVisable(false);
                    }}
                  />
                )
              ) : (
                <LoginPolicy
                  onAcceptPolicy={() => {
                    setIsAcceptBindingPolicy(true);
                  }}
                />
              )}
            </View>
          }
        />
      </Pressable>
    </ReactNativeModal>
  );
}
