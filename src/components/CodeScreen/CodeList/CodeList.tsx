import { View, ScrollView } from "react-native";
import React, { useState } from "react";
import { RefreshControl } from "react-native";
import CodeItem from "./CodeItem/CodeItem";
import useAppLanguage from "../../../language/AppLanguage/useAppLanguage";
import { LOCALES } from "../../../../locales";

export default function CodeList() {
  const { language } = useAppLanguage()

  //Also can select RedeemCodeUntil, RedeemCodeExpired
  const [codes, setCodes] = useState([
    {
      time: LOCALES[language].RedeemCodeForever, 
      code: "STARRAILGIFT",
    },
  ]);

  const onRefresh = React.useCallback(() => {
    //
  }, []);

  return (
    <View style={{ width: "100%" }} className="z-30">
      <ScrollView
        className="h-screen p-4 pb-0 mt-[110px]"
        refreshControl={
          <RefreshControl refreshing={false} onRefresh={onRefresh} />
        }
        scrollEnabled={false}
      >
        <View style={{ gap: 16, alignItems: "center" }} className="mb-16">
          {codes.map((code) => (
            <CodeItem key={code.code} {...code} />
          ))}
        </View>
      </ScrollView>
    </View>
  );
}
