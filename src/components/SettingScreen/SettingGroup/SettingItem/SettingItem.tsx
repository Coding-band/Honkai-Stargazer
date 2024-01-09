import { View, Text, TouchableOpacity } from "react-native";
import React, { useState } from "react";
import BlurView from "../../../global/BlurView/BlurView";
import { Image } from "expo-image";
import Listbox from "../../../global/Listbox/Listbox";

const ArrowRight = require("./icons/ArrowRight.svg");
const ArrowDown = require("./icons/ArrowDown.svg");

type Props = {
  type: "navigation" | "list";
  title: string;
  content?: string;
  list?: { value: any; name: string }[];
  value?: any;
  onChange?: (v: any) => void;
  onNavigate?: () => void;
  onOpen?: (v: boolean) => void;
};

export default function SettingItem(props: Props) {
  const [zIndex, setZIndex] = useState(0);

  const handleListboxOpen = (isOpen: boolean) => {
    if (isOpen) {
      setZIndex(50);
    } else {
      setZIndex(0);
    }
  };

  return (
    // <BlurView tint="dark" intensity={30} className="w-full">
    <View
      className="w-full h-[41px] bg-[#CCD4DD] border-b border-[#A0A2A4] mb-[-20px]"
      style={{ flexDirection: "row", zIndex }}
    >
      <View className="flex-1 h-full px-3" style={{ justifyContent: "center" }}>
        <Text className="text-[14px] font-[HY65]">{props.title}</Text>
      </View>
      {props.type === "list" ? (
        // @ts-ignore
        <Listbox
          value={props.value}
          onChange={props.onChange}
          onOpen={(v) => {
            handleListboxOpen(v);
            props.onOpen && props.onOpen(v);
          }}
          button={
            <TouchableOpacity activeOpacity={0.35}>
              <View
                className="w-40 h-full bg-[#F3F9F990]"
                style={{ justifyContent: "center", alignItems: "center" }}
              >
                <Text className="text-[14px] font-[HY65]">
                  {
                    props.list?.filter((item) => item.value === props.value)[0]
                      .name
                  }
                </Text>
                <Image
                  source={ArrowDown}
                  className="w-3 h-[14px] absolute right-[14px]"
                />
              </View>
            </TouchableOpacity>
          }
          top={40}
        >
          {props.list?.map((item) => (
            <Listbox.Item key={item.value} value={item.value}>
              {item.name}
            </Listbox.Item>
          ))}
        </Listbox>
      ) : (
        <TouchableOpacity activeOpacity={0.35} onPress={props.onNavigate}>
          <View
            className="w-40 h-full bg-[#F3F9F990]"
            style={{ justifyContent: "center", alignItems: "center" }}
          >
            <Text className="text-[14px] font-[HY65]">
              {props.content || "前往"}
            </Text>
            <Image
              source={ArrowRight}
              className="w-3 h-[14px] absolute right-[14px]"
            />
          </View>
        </TouchableOpacity>
      )}
    </View>
    // </BlurView>
  );
}
