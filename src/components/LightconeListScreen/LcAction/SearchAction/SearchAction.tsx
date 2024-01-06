import { View } from "react-native";
import React from "react";
import SearchBtn from "../../../global/Searchbar/SearchBtn/SearchBtn";
import Searchbar from "../../../global/Searchbar/Searchbar/Searchbar";
import useLightconeIsSearching from "../../../../redux/lightconeIsSearching/useLightconeIsSearching";
import useLightconeSearch from "../../../../redux/lightconeSearch/useLightconeSearch";
import useAppLanguage from "../../../../language/AppLanguage/useAppLanguage";
import { LOCALES } from "../../../../../locales";

export default function SearchAction() {
  const { setIsSearching, isSearching } = useLightconeIsSearching();
  const { searchValue, setSearchValue } = useLightconeSearch();
  const { language } = useAppLanguage();

  return (
    <View>
      {isSearching ? (
        <Searchbar
          placeholder={LOCALES[language].FilterFindLightcone}
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
