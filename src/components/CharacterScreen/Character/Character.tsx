import { View, Text, Dimensions, ScrollView } from "react-native";
import React, { useContext } from "react";
import { Image } from "expo-image";
import CharAction from "./CharAction/CharAction";
import CharacterContext from "../../../context/CharacterContext";
import CharInfo from "./CharInfo/CharInfo";
import CharAttribute from "./CharAttribute/CharAttribute";

export default function Character() {
  const charData = useContext(CharacterContext);

  return (
    <View className="absolute bottom-0 w-full" style={{ alignItems: "center" }}>
      <Image
        transition={200}
        style={{ width: 300, height: 692 }}
        source={charData?.imageFull}
      />
      <View
        className="absolute w-full p-[30px] z-50"
        style={{
          height: Dimensions.get("window").height - 40,
        }}
      >
        <ScrollView>
          <CharInfo />
          <CharAttribute />
        </ScrollView>
      </View>
      <CharAction />
    </View>
  );
}
