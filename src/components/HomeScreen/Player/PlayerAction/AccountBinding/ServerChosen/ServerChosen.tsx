import { View, Pressable, Keyboard, Dimensions } from "react-native";
import React, { useEffect, useState } from "react";
import ReactNativeModal from "react-native-modal";
import PopUpCard from "../../../../../global/PopUpCard/PopUpCard";
import useAcceptBindingPolicy from "../../../../../../hooks/useAcceptBindingPolicy";
import ToLoginScreen from "./ToLoginScreen/ToLoginScreen";
import ManualEnterCookie from "./ManualEnterCookie/ManualEnterCookie";

export default function ServerChosen() {
  const { isAcceptBindingPolicy } = useAcceptBindingPolicy();

  const [isSelected, setIsSelected] = useState(true);
  useEffect(() => {
    setIsSelected(isAcceptBindingPolicy);
  }, [isAcceptBindingPolicy]);

  const [isManualEnterCookie, setIsManualEnterCookie] = useState(false);

  const handleCancelLogin = () => {
    setIsSelected(false);
  };

  return (
    <ReactNativeModal
      useNativeDriverForBackdrop
      isVisible={isSelected}
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
          onClose={handleCancelLogin}
          title="选择服务器"
          content={
            <View className="p-4">
              {!isManualEnterCookie ? (
                <ToLoginScreen
                  onServerChosen={() => {
                    setIsSelected(false);
                  }}
                  onCookieChosen={() => {
                    setIsManualEnterCookie(true);
                  }}
                />
              ) : (
                <ManualEnterCookie
                  onCookieSave={() => {
                    setIsSelected(false);
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
