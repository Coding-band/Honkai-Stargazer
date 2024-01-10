import { KeyboardAvoidingView } from "react-native";
import React, { useEffect } from "react";
import useRelicIsSearching from "../../../redux/relicIsSearching/useRelicIsSearching";
import useRelicSearch from "../../../redux/relicSearch/useRelicSearch";
import SearchAction from "./SearchAction/SearchAction";

export default function RelicAction() {
  const { isSearching, setIsSearching } = useRelicIsSearching();
  const { setSearchValue } = useRelicSearch();

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
      className="w-full h-[46px] absolute bottom-0 mb-[37px] z-50"
      contentContainerStyle={{
        marginRight: isSearching ? 0 : 30,
        justifyContent: "flex-end",
        alignItems: "center",
        flexDirection: "row",
        gap: 11,
      }}
    >
      <SearchAction />
    </KeyboardAvoidingView>
  );
}
