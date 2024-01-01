import React, { useState } from "react";
import { View } from "react-native";
import MoreBtn from "../../../global/MoreBtn/MoreBtn";
import List from "../../../global/List/List";
import ListItem from "../../../global/List/ListItem";
import { useClickOutside } from "react-native-click-outside";
import AccountBinding from "./AccountBinding/AccountBinding";
import { useNavigation } from "@react-navigation/native";
import { SCREENS } from "../../../../constant/screens";
import useAppLanguage from "../../../../context/AppLanguage/useAppLanguage";
import { LOCALES } from "../../../../../locales";

export default function PlayerAction() {
  const navigation = useNavigation();
  const {language} = useAppLanguage();

  const [isPress, setIsPress] = useState(false);
  const containerRef = useClickOutside<View>(() => {
    if (isPress) {
      setIsPress(false);
    }
  });

  const [isBindingAccount, setIsBindingAccount] = useState(false);

  return (
    <View className="z-50" ref={containerRef}>
      <MoreBtn
        onPress={() => {
          setIsPress(!isPress);
          setIsBindingAccount(false);
        }}
      />
      <View
        style={{ display: isPress ? "flex" : "none" }}
        className="absolute right-0 top-8"
      >
        <List>
          <ListItem
            onPress={() => {
              setIsBindingAccount(true);
            }}
          >
          {LOCALES[language].AccountLogin}
          </ListItem>
          <ListItem>
            {LOCALES[language].ModifyHomePage}</ListItem>
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
