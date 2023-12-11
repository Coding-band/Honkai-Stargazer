import { View, Text } from "react-native";
import React from "react";
import { Server } from "../../../../../../types/hsrServer";
import useHsrServerChosen from "../../../../../../redux/hsrServerChosen/useHsrServerChosen";
import { useNavigation } from "@react-navigation/native";
import Button from "../../../../../global/Button/Button";
import { SCREENS } from "../../../../../../constant/screens";

const hsrServers: Server[] = [
  // { id: "asia", name: "星穹列车", platform: "miyoushe" },
  // { id: "asia", name: "无名客", platform: "miyoushe" },
  { id: "asia", name: "Asia", platform: "hoyolab" },
  { id: "europe", name: "Europe", platform: "hoyolab" },
  { id: "america", name: "America", platform: "hoyolab" },
  { id: "twhkmo", name: "TW HK MO", platform: "hoyolab" },
];

type Props = {
  onServerChosen?: (server: Server) => void;
  onCookieChosen?: () => void;
};

export default function ToLoginScreen(props: Props) {
  const navigation = useNavigation();
  const { setHsrServerChosen } = useHsrServerChosen();

  const handleChoseServer = (server: Server) => {
    props.onServerChosen && props.onServerChosen(server);
    setHsrServerChosen(server.id);
    // @ts-ignore
    navigation.navigate(SCREENS.LoginPage.id);
  };

  return (
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
          <Text className="font-[HY55]">{server.name}</Text>
        </Button>
      ))}
      <Button
        onPress={() => {
          props.onCookieChosen && props.onCookieChosen();
        }}
        hasShadow={false}
        width={"100%"}
        height={46}
      >
        <Text className="font-[HY55]">手动设置</Text>
      </Button>
    </View>
  );
}
