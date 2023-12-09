import { View, Text, Pressable } from "react-native";
import React, { useState } from "react";
import ReactNativeModal from "react-native-modal";
import PopUpCard from "../../global/PopUpCard/PopUpCard";
import Button from "../../global/Button/Button";
import useIsLogin from "../../../hooks/useIsLogin";
import { useNavigation } from "@react-navigation/native";

export default function LoginPolicy() {
  const navigation = useNavigation();

  const [isSelected, setIsSelected] = useState(true);
  const { setIsLogin } = useIsLogin();

  const handleConfirmLogin = () => {
    setIsLogin(true);
    setIsSelected(false);
  };
  const handleCancelLogin = () => {
    navigation.goBack();
  };

  return (
    <ReactNativeModal
      useNativeDriverForBackdrop
      isVisible={isSelected}
      statusBarTranslucent
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
              <View style={{ gap: 4, alignItems: "center" }}>
                <Text className="text-[14px] font-[HY55] text-black leading-5">
                  亲爱的用户您好，登录miHoYo/Hoyoverse账号功能需要于设备端存储您的cookies，因此我们必须获得您的授权才可以进行。但我们保证您的cookies仅会保存在本地并仅于您和miHoYo/Hoyoverse间使用。
                </Text>
                <Text className="text-[14px] font-[HY55] text-black leading-5">
                  点击“确定”即代表您同意我们使用您的cookies并打开登录页面，否则请关闭本窗口。
                </Text>
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
