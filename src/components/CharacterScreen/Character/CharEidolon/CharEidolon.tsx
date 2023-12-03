import { View } from "react-native";
import React, { useContext } from "react";
import CharPageHeading from "../../../global/PageHeading/PageHeading";
import { StarHalf } from "phosphor-react-native";
import { Image } from "expo-image";
import CharacterContext from "../../../../context/CharacterContext";
import Chacracter from "../../../../../assets/images/images_map/chacracter";
import { CharacterName } from "../../../../types/character";

export default function CharEidolon() {
  const charData = useContext(CharacterContext);

  //這部分要localeName as CharacterName
  let charEidolon1 = Chacracter[charData?.id as CharacterName]?.eidolon1;
  let charEidolon2 = Chacracter[charData?.id as CharacterName]?.eidolon2;
  let charEidolon3 = Chacracter[charData?.id as CharacterName]?.eidolon3;
  let charEidolon4 = Chacracter[charData?.id as CharacterName]?.eidolon4;
  let charEidolon5 = Chacracter[charData?.id as CharacterName]?.eidolon5;
  let charEidolon6 = Chacracter[charData?.id as CharacterName]?.eidolon6;

  return (
    <View style={{ alignItems: "center" }}>
      <CharPageHeading Icon={StarHalf}>星魂</CharPageHeading>
      <View className="w-full h-[280px]">
        <Image
          source={charEidolon1}
          className="absolute w-[150px] h-[150px] left-[3px] top-[22px] z-30"
        />
        <Image
          source={charEidolon2}
          className="absolute w-[150px] h-[150px] left-[100px]"
        />
        <Image
          source={charEidolon3}
          className="absolute w-[150px] h-[150px] left-[185px]"
        />
        <Image
          source={charEidolon6}
          className="absolute w-[150px] h-[150px] top-[120px] z-20"
        />
        <Image
          source={charEidolon5}
          className="absolute w-[150px] h-[150px] top-[140px] left-[100px] z-10"
        />
        <Image
          source={charEidolon4}
          className="absolute w-[150px] h-[150px] top-[114px] left-[200px]"
        />
      </View>
    </View>
  );
}
