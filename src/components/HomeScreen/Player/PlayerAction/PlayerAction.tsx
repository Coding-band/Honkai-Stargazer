import React, { useState } from "react";
import { TouchableOpacity, View } from "react-native";
import MoreBtn from "../../../global/MoreBtn/MoreBtn";
import List from "../../../global/List/List";
import ListItem from "../../../global/List/ListItem";
import { useClickOutside } from "react-native-click-outside";
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

  return (
    <View className="z-50" ref={containerRef}>
      <MoreBtn
        onPress={() => {
          setIsPress(!isPress);
        }}
      />
      <View
        style={{ display: isPress ? "flex" : "none" }}
        className="absolute right-0 top-8"
      >
        <List>
          <ListItem
            onPress={() => {
              // @ts-ignore
              navigation.navigate(SCREENS.LoginPage.id);
            }}
          >
            账号管理
          </ListItem>
          <ListItem>编辑首页</ListItem>
          <ListItem>设置</ListItem>
        </List>
      </View>
    </View>
  );
}
