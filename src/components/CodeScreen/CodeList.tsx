import { View, Text, ScrollView } from "react-native";
import React from "react";
import { RefreshControl } from "react-native";
import { LinearGradient } from "expo-linear-gradient";
import { Shadow } from "react-native-neomorph-shadows";

export default function CodeList() {
  const onRefresh = React.useCallback(() => {
    //
  }, []);

  return (
    <View style={{ width: "100%" }} className="z-30">
      <ScrollView
        className="h-screen p-4 pb-0 pt-[126px]"
        refreshControl={
          <RefreshControl refreshing={false} onRefresh={onRefresh} />
        }
      >
        <View style={{ gap: 16, alignItems: "center" }} className="mb-16">
          <View className="w-full h-[90px] bg-[#D2DBE3] rounded-[4px] overflow-hidden">
            <Shadow
              inner // <- enable inner shadow
              useArt // <- set this prop to use non-native shadow on ios
              style={{
                shadowOffset: { width: 10, height: 10 },
                shadowOpacity: 1,
                shadowColor: "grey",
                shadowRadius: 10,
                borderRadius: 20,
                backgroundColor: "white",
                width: 100,
                height: 100,
                // ...include most of View/Layout styles
              }}
            >
              <LinearGradient
                className="w-full h-[30px]"
                colors={["#020510", "#454C65"]}
                start={[0, 0]}
                end={[1, 1]}
                style={{ opacity: 0.8 }}
              ></LinearGradient>
            </Shadow>
          </View>
        </View>
      </ScrollView>
    </View>
  );
}
