import { Image } from "expo-image";
import React from "react";
import {
  DimensionValue,
  StyleProp,
  Text,
  TouchableOpacity,
  View,
  ViewStyle,
} from "react-native";
import { GestureResponderEvent } from "react-native-modal";
import { Shadow } from "react-native-shadow-2";

export default function DropDownMenuButton({
  children,
  width,
  height,
  onPress,
  style,
  withArrow,
  activeOpacity,
}: {
  children: any;
  width: DimensionValue;
  height: DimensionValue;
  onPress?: (e: GestureResponderEvent) => void;
  withArrow?: boolean;
  style?: StyleProp<ViewStyle>;
  activeOpacity?: number;
}) {
  return(
    <View
      className="rounded-[8px] border width-[8px] border-solid border-[#F3F9FF66] w-full h-full"
      style={[
        {
          width,
          height,
          justifyContent: "flex-start",
          alignItems: "center",
          flexDirection : "row",
        },
        style,
      ]}
    >
      {typeof children === "string" ? (
        <Text className="text-[#222] font-[HY65]">{children}</Text>
      ) : (
        children
      )}

      {withArrow && (
        <Image cachePolicy="none"
          className="w-3 h-2 right-2"
          source={require("../../../assets/icons/UpArrowSingleGray.svg")}
        />
      )}
    </View>
  );
}
