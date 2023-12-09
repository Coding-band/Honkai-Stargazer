import { View, Text, Pressable, TextInput, Keyboard } from "react-native";
import React, { useEffect, useState } from "react";
import ReactNativeModal from "react-native-modal";
import PopUpCard from "../../../../../global/PopUpCard/PopUpCard";
import Button from "../../../../../global/Button/Button";
import useAcceptBindingPolicy from "../../../../../../hooks/useAcceptBindingPolicy";
import { useNavigation } from "@react-navigation/native";
import useHsrServerChosen from "../../../../../../redux/hsrServerChosen/useHsrServerChosen";
import { hsrServerId } from "../../../../../../constant/hsrServer";
import { SCREENS } from "../../../../../../constant/screens";
import { TouchableWithoutFeedback } from "react-native-gesture-handler";

type Server = {
  id: hsrServerId;
  name: string;
  platform: string;
};

const hsrServers: Server[] = [
  { id: "asia", name: "Asia", platform: "hoyolab" },
  { id: "europe", name: "Europe", platform: "hoyolab" },
  { id: "america", name: "America", platform: "hoyolab" },
  { id: "twhkmo", name: "TW HK MO", platform: "hoyolab" },
];

export default function ServerChosen() {
  const navigation = useNavigation();

  const { isAcceptBindingPolicy } = useAcceptBindingPolicy();
  const { setHsrServerChosen } = useHsrServerChosen();

  const [isSelected, setIsSelected] = useState(isAcceptBindingPolicy);
  useEffect(() => {
    setIsSelected(isAcceptBindingPolicy);
  }, [isAcceptBindingPolicy]);

  const [isManualEnterCookie, setIsManualEnterCookie] = useState(false);

  const handleCancelLogin = () => {
    setIsSelected(false);
  };

  const handleChoseServer = (server: Server) => {
    setIsSelected(false);
    setHsrServerChosen(server.id);
    // @ts-ignore
    navigation.navigate(SCREENS.LoginPage.id);
  };

  return (
    <ReactNativeModal
      useNativeDriverForBackdrop
      isVisible={isSelected}
      statusBarTranslucent
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
                <View style={{ gap: 12 }}>
                  <Text className="text-[14px] font-[HY55] text-black leading-5">
                    请选择账号所在服务器。
                  </Text>
                  {hsrServers.map((server) => (
                    <Button
                      onPress={() => {
                        handleChoseServer(server);
                      }}
                      key={server.id}
                      hasShadow={false}
                      width={"100%"}
                      height={46}
                    >
                      <Text>{server.name}</Text>
                    </Button>
                  ))}
                  <Button
                    onPress={() => {
                      setIsManualEnterCookie(true);
                    }}
                    hasShadow={false}
                    width={"100%"}
                    height={46}
                  >
                    <Text className="font-[HY55]">手动设置</Text>
                  </Button>
                </View>
              ) : (
                <View style={{ gap: 12 }}>
                  <Text className="text-[14px] font-[HY55] text-black leading-5">
                    请选择服务器并粘贴Cookies。
                  </Text>
                  <Button hasShadow={false} width={"100%"} height={46}>
                    <Text className="font-[HY55]">Asia</Text>
                  </Button>
                  <TextInput
                    textAlignVertical="top"
                    className="w-full h-[280px] bg-[#ffffff50] rounded-[4px] p-4"
                  />
                  <Button hasShadow={false} width={"100%"} height={46}>
                    <Text className="font-[HY55]">確定</Text>
                  </Button>
                </View>
              )}
            </View>
          }
        />
      </Pressable>
    </ReactNativeModal>
  );
}
