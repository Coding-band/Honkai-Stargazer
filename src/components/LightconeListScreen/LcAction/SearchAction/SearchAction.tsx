import { View } from "react-native";
import React from "react";
import SearchBtn from "../../../global/Searchbar/SearchBtn/SearchBtn";
import Searchbar from "../../../global/Searchbar/Searchbar/Searchbar";
import useLightconeIsSearching from "../../../../redux/lightconeIsSearching/useLightconeIsSearching";
import useLightconeSearch from "../../../../redux/lightconeSearch/useLightconeSearch";

export default function SearchAction() {
  const { setIsSearching, isSearching } = useLightconeIsSearching();
  const { searchValue, setSearchValue } = useLightconeSearch();

  return (
    <View>
      {isSearching ? (
        <Searchbar
          placeholder="搜尋光錐"
          value={searchValue}
          onChangeText={setSearchValue}
          onClear={() => {
            if (searchValue) {
              setSearchValue("");
            } else {
              setIsSearching(false);
            }
          }}
        />
      ) : (
        <SearchBtn
          onPress={() => {
            setIsSearching(true);
          }}
        />
      )}
    </View>
  );
}
