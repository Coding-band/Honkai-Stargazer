import { View, Text } from "react-native";
import React from "react";
import { TouchableOpacity } from "react-native-gesture-handler";
import { cn } from "../../../../utils/css/cn";
import ListSelectedIcon from "../ListSelectedIcon/ListSelectedIcon";

type Props = {
  onPress: () => void;
  selected: boolean;
  children: any;
};

export default function ListboxItem(props: Props) {
  return (
    <TouchableOpacity
      activeOpacity={0.65}
      onPress={props.onPress}
      className={cn("p-[8px]", props.selected ? "bg-[#00000010]" : "")}
      style={{
        flexDirection: "row",
        justifyContent: "center",
        alignItems: "center",
      }}
    >
      <Text className="text-[#F3F9FF66] text-[14px] font-[HY65] leading-5">
        {props.children}
      </Text>
      {props.selected && <ListSelectedIcon />}
    </TouchableOpacity>
  );
}
