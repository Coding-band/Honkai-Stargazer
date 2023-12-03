import { View, Text } from "react-native";
import React from "react";
import { IconProps } from "phosphor-react-native";

type Props = {
  Icon: (i: IconProps) => React.JSX.Element;
  children: string;
};

export default function PageHeading({ Icon, children }: Props) {
  return (
    <View className="py-[30px]" style={{ alignItems: "center" }}>
      <Icon size={32} color="white" />
      <View style={{ flexDirection: "row", alignItems: "center", gap: 13 }}>
        <View
          style={{ backgroundColor: "#ffffff40", height: 2, width: 50 }}
        ></View>
        <Text className="text-white text-[16px]" style={{ fontFamily: "HY65" }}>
          {children}
        </Text>
        <View
          style={{ backgroundColor: "#ffffff40", height: 2, width: 50 }}
        ></View>
      </View>
    </View>
  );
}
