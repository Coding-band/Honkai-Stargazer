import { StyleSheet, View } from "react-native";
import React from "react";
import FilterBtn from "./FilterBtn/FilterBtn";
import SearchBtn from "./SearchBtn/SearchBtn";
import OrderBtn from "./OrderBtn/OrderBtn";
import Listbox from "../../global/Listbox/Listbox";
import useCharSorting from "../../../redux/characterSorting/useCharSorting";
import useCharSortingReverse from "../../../redux/characterSortingReverse/useCharSortingReverse";

export default function CharAction() {
  const { charSorting, setCharSorting, charSortingList } = useCharSorting();
  const { setCharSortingReverse, charSortingReverse } = useCharSortingReverse();

  const handleReverse = () => {
    setCharSortingReverse(!charSortingReverse);
  };

  const handleOrderChange = (v: any) => {
    setCharSorting(v);
  };

  return (
    <View
      className="w-full h-[46px] absolute bottom-0 mb-[37px] z-50"
      style={styles.container}
    >
      <FilterBtn />
      <Listbox
        button={
          <OrderBtn
            onPressReverse={handleReverse}
            reverse={!!charSortingReverse}
          >
            {charSorting?.name || ""}
          </OrderBtn>
        }
        value={charSorting?.id}
        onChange={handleOrderChange}
      >
        {charSortingList?.map((sorting) => (
          <Listbox.Item key={sorting.id} value={sorting.id}>
            {sorting.name}
          </Listbox.Item>
        )) || []}
      </Listbox>
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
