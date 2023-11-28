import { View, Text, Dimensions, ScrollView } from "react-native";
import React from "react";
import { Image } from "expo-image";
import CharAction from "./CharAction/CharAction";
import CharStars from "./CharStars/CharStars";

const testCharFullImg = require("../../../../assets/images/test-char-full-img.png");
const testPath = require("../../../../assets/images/test-path.png");
const testCompatType = require("../../../../assets/images/test-combat-type.png");

export default function Character() {
  return (
    <View className="absolute bottom-0 w-full" style={{ alignItems: "center" }}>
      <Image
        transition={200}
        style={{ width: 300, height: 692 }}
        source={testCharFullImg}
      />
      <View
        className="absolute w-full p-[30px] z-50"
        style={{
          height: Dimensions.get("window").height,
        }}
      >
        <ScrollView>
          <View
            style={{
              paddingTop: Dimensions.get("window").height - 350,
              gap: 8,
            }}
          >
            <View style={{ gap: 12 }}>
              <Text className="text-[32px] font-[HY65] text-white">希儿</Text>
              <CharStars count={5} />
            </View>
            <View
              style={{
                flexDirection: "row",
                justifyContent: "space-between",
                alignItems: "center",
              }}
            >
              <View style={{ flexDirection: "row", gap: 26 }}>
                <View style={{ flexDirection: "row", gap: 8 }}>
                  <Image source={testPath} style={{ width: 24, height: 24 }} />
                  <Text className="text-[16px] text-white font-[HY65]">
                    巡猎
                  </Text>
                </View>
                <View
                  style={{ flexDirection: "row", gap: 5, alignItems: "center" }}
                >
                  <Image
                    source={testCompatType}
                    style={{ width: 24, height: 24 }}
                  />
                  <Text className="text-[16px] text-white font-[HY65]">
                    量子
                  </Text>
                </View>
              </View>
              <View>
                <Text className="text-[16px] text-white font-[HY65]">
                  贝洛伯格
                </Text>
              </View>
            </View>
          </View>
        </ScrollView>
      </View>
      <CharAction />
    </View>
  );
}
