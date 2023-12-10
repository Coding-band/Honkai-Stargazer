import { View, Text, Pressable, Dimensions } from "react-native";
import React from "react";
import ReactNativeModal from "react-native-modal";
import PopUpCard from "../../../../../global/PopUpCard/PopUpCard";
import Button from "../../../../../global/Button/Button";
import useAcceptBindingPolicy from "../../../../../../hooks/useAcceptBindingPolicy";
import { HtmlText } from "@e-mine/react-native-html-text";
import loginPolicyText from "../../../../../../../data/login_policy.json";

export default function LoginPolicy() {
  const { isAcceptBindingPolicy, setIsAcceptBindingPolicy } =
    useAcceptBindingPolicy();

  const handleConfirmLogin = () => {
    setIsAcceptBindingPolicy(true);
  };
  const handleCancelLogin = () => {
    setIsAcceptBindingPolicy(false);
  };

  return (
    <ReactNativeModal
      useNativeDriverForBackdrop
      isVisible={!isAcceptBindingPolicy}
      statusBarTranslucent
      deviceHeight={Dimensions.get("screen").height}
    >
      <Pressable
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
          title="登录须知"
          content={
            <View className="p-4">
              <View style={{ gap: 0, alignItems: "center" }}>
                <HtmlText
                  style={{ color: "#000", fontFamily: "HY55", lineHeight: 20 }}
                >
                  {loginPolicyText.zh_cn}
                </HtmlText>
                <Button
                  onPress={handleConfirmLogin}
                  hasShadow={false}
                  width={140}
                  height={46}
                >
                  <Text>确定</Text>
                </Button>
              </View>
            </View>
          }
        />
      </Pressable>
    </ReactNativeModal>
  );
}
