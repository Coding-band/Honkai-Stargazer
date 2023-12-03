import { View } from "react-native";
import React, { useContext, useState } from "react";
import CharPageHeading from "../../../global/PageHeading/PageHeading";
import { StarHalf } from "phosphor-react-native";
import { Image } from "expo-image";
import CharacterContext from "../../../../context/CharacterContext";
import Chacracter from "../../../../../assets/images/images_map/chacracter";
import { CharacterName } from "../../../../types/character";
import Eidolon1 from "./Eidolons/Eidolon1";
import Eidolon2 from "./Eidolons/Eidolon2";
import Eidolon3 from "./Eidolons/Eidolon3";
import Eidolon4 from "./Eidolons/Eidolon4";
import Eidolon5 from "./Eidolons/Eidolon5";
import Eidolon6 from "./Eidolons/Eidolon6";

export default function CharEidolon() {
  const [selectedEidolon, setSelectedEidolon] = useState(0);
  return (
    <View style={{ alignItems: "center" }}>
      <CharPageHeading Icon={StarHalf}>星魂</CharPageHeading>
      <View className="w-full h-[280px]">
        <Eidolon1
          selected={selectedEidolon === 0}
          onPress={() => {
            setSelectedEidolon(0);
          }}
        />
        <Eidolon2
          selected={selectedEidolon === 1}
          onPress={() => {
            setSelectedEidolon(1);
          }}
        />
        <Eidolon3
          selected={selectedEidolon === 2}
          onPress={() => {
            setSelectedEidolon(2);
          }}
        />
        <Eidolon4
          selected={selectedEidolon === 3}
          onPress={() => {
            setSelectedEidolon(3);
          }}
        />
        <Eidolon5
          selected={selectedEidolon === 4}
          onPress={() => {
            setSelectedEidolon(4);
          }}
        />
        <Eidolon6
          selected={selectedEidolon === 5}
          onPress={() => {
            setSelectedEidolon(5);
          }}
        />
      </View>
    </View>
  );
}
