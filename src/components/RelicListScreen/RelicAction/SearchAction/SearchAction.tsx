import { View } from "react-native";
import React from "react";
import SearchBtn from "../../../global/Searchbar/SearchBtn/SearchBtn";
import Searchbar from "../../../global/Searchbar/Searchbar/Searchbar";
import useRelicIsSearching from "../../../../redux/relicIsSearching/useRelicIsSearching";
import useRelicSearch from "../../../../redux/relicSearch/useRelicSearch";

export default function SearchAction() {
  const { setIsSearching, isSearching } = useRelicIsSearching();
  const { searchValue, setSearchValue } = useRelicSearch();

  return (
    <View>
      {isSearching ? (
        <Searchbar
          placeholder="搜尋遺器"
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
