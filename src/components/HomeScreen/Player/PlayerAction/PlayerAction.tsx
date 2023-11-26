import React, { useState } from "react";
import { View } from "react-native";
import MoreBtn from "../../../global/ui/MoreBtn/MoreBtn";
import List from "../../../global/ui/List/List";
import ListItem from "../../../global/ui/List/ListItem";

export default function PlayerAction() {
  const [isPress, setIsPress] = useState(false);

  return (
    <View className="z-50">
      <MoreBtn
        onPress={() => {
          setIsPress(!isPress);
        }}
      />
      {isPress && (
        <View className="absolute right-0 top-8">
          <List>
            <ListItem>账号管理</ListItem>
            <ListItem>编辑首页</ListItem>
            <ListItem>设置</ListItem>
          </List>
        </View>
      )}
    </View>
  );
}
