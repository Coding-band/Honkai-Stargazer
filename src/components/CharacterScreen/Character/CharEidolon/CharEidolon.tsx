import { View, Text } from "react-native";
import React from "react";
import CharPageHeading from "../../../global/layout/CharPageHeading";
import { StarHalf } from "phosphor-react-native";
import { Image } from "expo-image";

const testEidolon1 = require("../../../../../assets/images/character_eidolon/seele_eidolon1.webp");
const testEidolon2 = require("../../../../../assets/images/character_eidolon/seele_eidolon2.webp");
const testEidolon3 = require("../../../../../assets/images/character_eidolon/seele_eidolon3.webp");
const testEidolon4 = require("../../../../../assets/images/character_eidolon/seele_eidolon4.webp");
const testEidolon5 = require("../../../../../assets/images/character_eidolon/seele_eidolon5.webp");
const testEidolon6 = require("../../../../../assets/images/character_eidolon/seele_eidolon6.webp");

export default function CharEidolon() {
  return (
    <View style={{ alignItems: "center" }}>
      <CharPageHeading Icon={StarHalf}>星魂</CharPageHeading>
      <View className="w-full h-[280px]">
        <Image
          source={testEidolon1}
          className="absolute w-[150px] h-[150px] left-[3px] top-[22px] z-30"
        />
        <Image
          source={testEidolon2}
          className="absolute w-[150px] h-[150px] left-[100px]"
        />
        <Image
          source={testEidolon3}
          className="absolute w-[150px] h-[150px] left-[185px]"
        />
        <Image
          source={testEidolon6}
          className="absolute w-[150px] h-[150px] top-[120px] z-20"
        />
        <Image
          source={testEidolon5}
          className="absolute w-[150px] h-[150px] top-[140px] left-[100px] z-10"
        />
        <Image
          source={testEidolon4}
          className="absolute w-[150px] h-[150px] top-[114px] left-[200px]"
        />
      </View>
    </View>
  );
}
