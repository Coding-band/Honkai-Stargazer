import { StyleSheet, View } from "react-native";
import React from "react";
import FilterBtn from "./FilterAction/FilterBtn/FilterBtn";
import SearchBtn from "./SearchBtn/SearchBtn";
import OrderAction from "./OrderAction/OrderAction";
import FilterAction from "./FilterAction/FilterAction";

export default function CharAction() {
  return (
    <View
      className="w-full h-[46px] absolute bottom-0 mb-[37px] z-50"
      style={styles.container}
    >
      <FilterAction />
      <OrderAction />
      <SearchBtn />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    justifyContent: "center",
    alignItems: "center",
    flexDirection: "row",
    gap: 11,
  },
});
