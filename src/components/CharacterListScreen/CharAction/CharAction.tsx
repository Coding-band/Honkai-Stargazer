import { View, Text } from "react-native";
import React from "react";
import Button from "../../global/ui/Button/Button";
import { Image } from "expo-image";

const SearchIcon = require("../../../../assets/icons/Search.svg");
const FilterIcon = require("../../../../assets/icons/Filter.svg");
const BothSideArrowIcon = require("../../../../assets/icons/BothSideArrow.svg");

export default function CharAction() {
  return (
    <View
      className="w-full h-[46px] absolute bottom-0 mb-[37px] z-50"
      style={{
        justifyContent: "center",
        alignItems: "center",
        flexDirection: "row",
        gap: 11,
      }}
    >
      <Button width={46} height={46}>
        <Image style={{ width: 16, height: 16 }} source={FilterIcon} />
      </Button>
      <Button width={212} height={46}>
        <Text className="font-[HY65] text-[16px]">实装日期</Text>
        <Image
          style={{ width: 18, height: 18, position: "absolute", right: 12 }}
          source={BothSideArrowIcon}
        />
      </Button>
      <Button width={46} height={46}>
        <Image style={{ width: 16, height: 15 }} source={SearchIcon} />
      </Button>
    </View>
  );
}
