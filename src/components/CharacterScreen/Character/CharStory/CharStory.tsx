import { View, Text } from "react-native";
import React from "react";
import CharPageHeading from "../../../global/layout/CharPageHeading";
import { ChatsCircle } from "phosphor-react-native";

export default function CharStory() {
  return (
    <View style={{ alignItems: "center" }}>
      <CharPageHeading Icon={ChatsCircle}>角色故事</CharPageHeading>
      <Text className="text-white font-[HY65]" style={{ lineHeight: 24}}>
        飒爽俊逸的「地火」成员，成长于地底危险混乱的环境，习惯独来独往。保护与被保护，压迫与被压迫，世界向希儿展示的始终是非黑即白的那一面——直至「那名少女」的出现。
      </Text>
    </View>
  );
}
