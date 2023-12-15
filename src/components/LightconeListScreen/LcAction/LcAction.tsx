import { View, Text } from "react-native";
import React from "react";
import OrderAction from "./OrderAction/OrderAction";
import SearchBtn from "./SearchBtn/SearchBtn";
import FilterAction from "./FilterAction/FilterAction";

type Props = {
  onInstallOrderChange?: (order: boolean) => void;
};

export default function LcAction(props: Props) {
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
      <FilterAction />
      <OrderAction />
      <SearchBtn />
    </View>
  );
}
