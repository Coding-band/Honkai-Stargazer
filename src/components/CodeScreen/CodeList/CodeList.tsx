import { View, ScrollView } from "react-native";
import React, { useState } from "react";
import { RefreshControl } from "react-native";
import CodeItem from "./CodeItem/CodeItem";

export default function CodeList() {
  const [codes, setCodes] = useState([
    {
      code: "JAFYDN472FQQ",
    },
    {
      code: "GHF4GFS24TGC",
    },
    {
      code: "ADGDB4625232",
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
      >
        <View style={{ gap: 16, alignItems: "center" }} className="mb-16">
          {codes.map((code) => (
            <CodeItem key={code.code} code={code.code} />
          ))}
        </View>
      </ScrollView>
    </View>
  );
}
