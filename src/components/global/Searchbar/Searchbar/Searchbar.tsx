import React, { useEffect, useRef } from "react";
import Button from "../../Button/Button";
import { Image } from "expo-image";
import { Dimensions, TextInput, TouchableOpacity, View } from "react-native";
import { animated, useSpring } from "@react-spring/native";

const SearchIcon = require("../../../../../assets/icons/Search.svg");
const CloseIcon = require("../../../../../assets/icons/CloseBlack.svg");

type Props = {
  value: string | undefined;
  onChangeText: ((text: string) => void) | undefined;
  onClear: () => void;
  placeholder: string;
};

export default function Searchbar(props: Props) {
  const animation = useSpring({
    from: { width: 46 },
    to: { width: Dimensions.get("window").width - 60 },
  });

  const inputRef = useRef();
  useEffect(() => {
    // @ts-ignore
    inputRef.current.focus();
  }, []);

  return (
    <AnimatedButton
      style={[{ marginHorizontal: 30 }, animation]}
      disable
      width={310}
      height={46}
    >
      <View
        className="w-full h-full px-3"
        style={{ flexDirection: "row", alignItems: "center", gap: 9 }}
      >
        <Image style={{ width: 18, height: 18 }} source={SearchIcon} />
        <TextInput
          // @ts-ignore
          ref={inputRef}
          value={props.value}
          onChangeText={props.onChangeText}
          className="w-full font-[HY65] text-[16px]"
          placeholder={props.placeholder}
        />
        <TouchableOpacity
          onPress={props.onClear}
          className="absolute right-1"
          activeOpacity={0}
        >
          <Image style={{ width: 30, height: 30 }} source={CloseIcon} />
        </TouchableOpacity>
      </View>
    </AnimatedButton>
  );
}

const AnimatedButton = animated(Button);
