import React, { useState } from "react";
import { View } from "react-native";
import MoreBtn from "../../../global/MoreBtn/MoreBtn";
import List from "../../../global/List/List";
import ListItem from "../../../global/List/ListItem";
import { useClickOutside } from "react-native-click-outside";
import AccountBinding from "./AccountBinding/AccountBinding";
import { useNavigation } from "@react-navigation/native";
import { SCREENS } from "../../../../constant/screens";

export default function PlayerAction() {
  const navigation = useNavigation();

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
            账号綁定
          </ListItem>
          <ListItem>编辑首页</ListItem>
          <ListItem
            onPress={() => {
              // @ts-ignore
              navigation.navigate(SCREENS.SettingPage.id);
            }}
          >
            设置
          </ListItem>
        </List>
      </View>
      {isBindingAccount && <AccountBinding />}
    </View>
  );
}
