import { View, Text, TouchableOpacity, Dimensions } from "react-native";
import React, { useEffect, useState } from "react";
import { Image } from "expo-image";
import { useClickOutside } from "react-native-click-outside";
import { ScrollView } from "react-native-gesture-handler";

const Options = ({
  values,
  value,
  onChange,
}: {
  onChange: (id: { id: string; name: string }) => void;
  values: { id: string; name: string }[];
  value: { id: string; name: string };
}) => {
  const [current, setCurrent] = useState(value || values[0]);
  useEffect(() => {
    setCurrent(value);
  }, [value]);
  const [open, setOpen] = useState(false);

  useEffect(() => {
    onChange(current);
  }, [current]);

  const ref = useClickOutside(() => {
    setOpen(false);
  });

  return (
    <View ref={ref} className="w-[110px] z-30">
      <TouchableOpacity
        onPress={() => {
          setOpen(!open);
        }}
        activeOpacity={0.35}
        style={{ flexDirection: "row", alignItems: "center", gap: 8 }}
      >
        <Text className="text-text2 text-[16px] font-[HY65] leading-5">
          {values.filter((v) => v.id === current.id)[0]?.name}
        </Text>
        <Image
          source={require("../../../../assets/icons/More.svg")}
          className="w-3 h-1.5"
        />
      </TouchableOpacity>
      <ScrollView
        style={{
          display: open ? "flex" : "none",
          height: Dimensions.get("screen").height - 232,
        }}
        contentContainerStyle={{
          alignItems: "center",
          paddingBottom: 12,
        }}
        className="absolute top-[24px] left-0 bg-[#3E3E47] px-3 py-1.5 rounded-[4px]"
      >
        {values.map((value, i) => (
          <TouchableOpacity
            className="w-full py-1.5"
            key={value.id}
            activeOpacity={0.35}
            onPress={() => {
              setOpen(false);
              setCurrent(value);
            }}
            style={{ alignItems: "center", justifyContent: "center" }}
          >
            <Text className="text-text text-[16px] text-center font-[HY65] leading-5">
              {value?.name}
            </Text>
          </TouchableOpacity>
        ))}
      </ScrollView>
    </View>
  );
};

export default Options;
