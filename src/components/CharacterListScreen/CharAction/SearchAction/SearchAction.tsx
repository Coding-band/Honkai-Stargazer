import { View } from "react-native";
import React from "react";
import SearchBtn from "../../../global/Searchbar/SearchBtn/SearchBtn";
import Searchbar from "../../../global/Searchbar/Searchbar/Searchbar";
import useCharacterSearch from "../../../../redux/characterSearch/useCharacterSearch";
import useCharacterIsSearching from "../../../../redux/characterIsSearching/useCharacterIsSearching";

export default function SearchAction() {
  const { setIsSearching, isSearching } = useCharacterIsSearching();
  const { searchValue, setSearchValue } = useCharacterSearch();

  return (
    <View>
      {isSearching ? (
        <Searchbar
          placeholder="搜尋角色"
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
