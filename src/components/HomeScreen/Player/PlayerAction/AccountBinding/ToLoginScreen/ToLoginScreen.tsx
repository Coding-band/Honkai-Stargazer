import { View, Text, Platform } from "react-native";
import React from "react";
import { useNavigation } from "@react-navigation/native";
import { SCREENS } from "../../../../../../constant/screens";
import TextButton from "../../../../../global/TextButton/TextButton";
import useAppLanguage from "../../../../../../language/AppLanguage/useAppLanguage";
import { LOCALES } from "../../../../../../../locales";
import {
  hsrPlatform,
  hsrServerId,
} from "../../../../../../utils/hoyolab/servers/hsrServer.types";
import Button from "../../../../../global/Button/Button";
import Toast from "../../../../../../utils/toast/Toast";

type Server = {
  id: hsrServerId;
  name: string;
  platform: hsrPlatform;
};

const hsrServers: Server[] = [
  { id: "cn1", name: "星穹列车", platform: "mihoyo" },
  { id: "cn2", name: "无名客", platform: "mihoyo" },
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
  const { language } = useAppLanguage();
  const navigation = useNavigation();

  const handleChoseServer = (server: Server) => {
    props.onServerChosen && props.onServerChosen(server);
    // @ts-ignore
    navigation.navigate(SCREENS.LoginPage.id, {
      serverId: server.id,
      platform: server.platform,
    });
  };

  return (
    <View style={{ gap: 12 }}>
      <Text className="text-[14px] font-[HY65] text-black leading-5">
        {LOCALES[language].SelectAccountInServer}
      </Text>
      {hsrServers.map((server) => (
        <Button
          onPress={() => {
            if (Platform.OS === "ios") {
              Toast(LOCALES[language].IOSCantLoginYet);
            } else {
              handleChoseServer(server);
            }
          }}
          key={server.id}
          hasShadow={false}
          width={"100%"}
          height={46}
        >
          <Text className={Platform.OS === "ios" ? "line-through" : ""}>
            {server.name}
          </Text>
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
        {LOCALES[language].ManuallySetup}
      </Button>
    </View>
  );
}
