import { View } from "react-native";
import React from "react";
import SearchBtn from "../../../global/Searchbar/SearchBtn/SearchBtn";
import Searchbar from "../../../global/Searchbar/Searchbar/Searchbar";
import useRelicIsSearching from "../../../../redux/relicIsSearching/useRelicIsSearching";
import useRelicSearch from "../../../../redux/relicSearch/useRelicSearch";
import useAppLanguage from "../../../../language/AppLanguage/useAppLanguage";
import { LOCALES } from "../../../../../locales";

export default function SearchAction() {
  const { setIsSearching, isSearching } = useRelicIsSearching();
  const { searchValue, setSearchValue } = useRelicSearch();
  const { language } = useAppLanguage();

  return (
    <View>
      {isSearching ? (
        <Searchbar
          placeholder={LOCALES[language].FilterFindRelic}
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
