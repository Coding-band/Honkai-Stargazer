import { KeyboardAvoidingView, StyleSheet } from "react-native";
import React, { useEffect } from "react";
import OrderAction from "./OrderAction/OrderAction";
import FilterAction from "./FilterAction/FilterAction";
import SearchAction from "./SearchAction/SearchAction";
import useCharacterIsSearching from "../../../redux/characterIsSearching/useCharacterIsSearching";
import useCharacterSearch from "../../../redux/characterSearch/useCharacterSearch";
import { dynamicHeightListAction } from "../../../constant/ui";

export default function CharAction() {
  const { isSearching, setIsSearching } = useCharacterIsSearching();
  const { setSearchValue } = useCharacterSearch();

  useEffect(() => {
    return () => {
      setIsSearching(false);
      setSearchValue("");
    };
  }, []);

  return (
    <KeyboardAvoidingView
      behavior="position"
      keyboardVerticalOffset={25}
      className={dynamicHeightListAction}
      contentContainerStyle={{
        justifyContent: isSearching ? "flex-end" : "center",
        alignItems: "center",
        flexDirection: "row",
        gap: 11,
      }}
    >
      {isSearching || (
        <>
          <FilterAction />
          <OrderAction />
        </>
      )}
      <SearchAction />
    </KeyboardAvoidingView>
  );
}
