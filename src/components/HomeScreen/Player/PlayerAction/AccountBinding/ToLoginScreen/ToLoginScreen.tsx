import { View, Text, Platform, Linking } from "react-native";
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
import {
  cookieURLs,
  getHoyolabCookieFromCookieManager,
} from "../../../../../../utils/hoyolab/cookie/hoyolabCookie";
import { InAppBrowser } from 'react-native-inappbrowser-reborn'
import { isHoyolabPlatform } from "../../../../../../utils/hoyolab/utils";
import CookieManager from "@react-native-cookies/cookies";
import useHoyolabCookie from "../../../../../../redux/hoyolabCookie/useHoyolabCookie";
import useHsrServerChosen from "../../../../../../redux/hsrServerChosen/useHsrServerChosen";

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

  const { setHoyolabCookie } = useHoyolabCookie();
  const { setHsrServerChosen } = useHsrServerChosen();

  const handleChoseServer = (server: Server) => {
    props.onServerChosen && props.onServerChosen(server);
    // @ts-ignore
    
    
    navigation.navigate(SCREENS.LoginPage.id, {
      serverId: server.id,
      platform: server.platform,
    });
    

    //openInAppBrowser(server);
  };

  async function openInAppBrowser(server : Server){
    try{
      const url =  (server.platform === "hoyolab" ? cookieURLs.hoyolab : cookieURLs.mihoyo)
      const serverId = server.id;
      if (await InAppBrowser.isAvailable()) {
        const result = await InAppBrowser.open(url, {
        }).then(async (response : any) => {
          console.log(response)

          const cookie = await getHoyolabCookieFromCookieManager(
            isHoyolabPlatform(serverId) ? "hoyolab" : "mihoyo"
          );
        })
        console.log(result)
      }
    }catch(error : any){
      console.error(error)
    }
  }
  
  return (
    <View style={{ gap: 12 }}>
      <Text className="text-[14px] font-[HY65] text-black leading-5">
        {LOCALES[language].SelectAccountInServer}
      </Text>
      
      <Text className="text-[16px] font-[HY65] text-black leading-5">
        {LOCALES[language].LoginHint}
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
          <Text className="text-[16px] font-[HY65] text-black leading-5">
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
        fontSize={20}
      >
        {LOCALES[language].UseCookiesToLogin}
      </Button>
    </View>
  );
}
