import React, { useState } from "react";
import { View } from "react-native";
import MoreBtn from "../../../global/MoreBtn/MoreBtn";
import List from "../../../global/List/List";
import ListItem from "../../../global/List/ListItem";
import { useClickOutside } from "react-native-click-outside";
import AccountBinding from "./AccountBinding/AccountBinding";
import { useNavigation } from "@react-navigation/native";
import { SCREENS } from "../../../../constant/screens";
import useAppLanguage from "../../../../language/AppLanguage/useAppLanguage";
import { LOCALES } from "../../../../../locales";
import Toast from "../../../../utils/toast/Toast";
import useHsrUUID from "../../../../hooks/hoyolab/useHsrUUID";
import auth from "@react-native-firebase/auth";
import useHoyolabCookie from "../../../../redux/hoyolabCookie/useHoyolabCookie";

export default function PlayerAction() {
  const navigation = useNavigation();
  const { language } = useAppLanguage();

  const [isPress, setIsPress] = useState(false);
  const [isBindingAccount, setIsBindingAccount] = useState(false);
  const containerRef = useClickOutside<View>(() => {
    if (isPress) {
      setIsPress(false);
    }
  });

  const hsrUUID = useHsrUUID();
  const { setHoyolabCookie } = useHoyolabCookie();

  const handleCloseBindingPopUp = () => {
    setIsPress(!isPress);
    setIsBindingAccount(false);
  };
  const handleOpenBindingPopUp = () => {
    setIsBindingAccount(true);
  };
  const handleLogout = () => {
    auth().signOut();
    setHoyolabCookie("");
  };

  return (
    <View className="z-50" ref={containerRef}>
      <MoreBtn onPress={handleCloseBindingPopUp} />
      <View
        style={{ display: isPress ? "flex" : "none" }}
        className="absolute right-0 top-8"
      >
        <List>
          <ListItem onPress={hsrUUID ? handleLogout : handleOpenBindingPopUp}>
            {LOCALES[language][hsrUUID ? "Logout" : "AccountLogin"]}
          </ListItem>
          <ListItem
            onPress={() => {
              Toast.StillDevelopingToast();
            }}
          >
            {LOCALES[language].ModifyHomePage}
          </ListItem>
          <ListItem
            onPress={() => {
              // @ts-ignore
              navigation.navigate(SCREENS.SettingPage.id);
            }}
          >
            {LOCALES[language].Setting}
          </ListItem>
        </List>
      </View>
      {isBindingAccount && <AccountBinding />}
    </View>
  );
}
