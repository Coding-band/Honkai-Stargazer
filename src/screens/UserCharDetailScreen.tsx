import { View, Text } from "react-native";
import React, { createContext } from "react";
import useAppLanguage from "../language/AppLanguage/useAppLanguage";
import { RouteProp, useRoute } from "@react-navigation/native";
import { ParamList } from "../types/navigation";
import { StatusBar } from "expo-status-bar";
import WallPaper from "../components/global/WallPaper/WallPaper";
import { LinearGradient } from "expo-linear-gradient";
import UserCharDetail from "../components/UserCharDetailScreen/UserCharDetail/UserCharDetail";
import { CharacterName } from "../types/character";
import UserCharDetailContext from "../context/UserCharDetailData/UserCharDetailContext";
import useUserByUUID from "../firebase/hooks/User/useUserByUUID";

export default function UserCharDetailScreen() {
  const route = useRoute<RouteProp<ParamList, "UserCharDetail">>();
  const uuid = route.params.uuid;
  const charId = route.params.charId;

  return (
    <View style={{ flex: 1, backgroundColor: "white" }}>
      <StatusBar style="dark" />
      <WallPaper isBlur />
      <LinearGradient
        className="absolute w-full h-full"
        colors={["#00000080", "#00000020"]}
      />
      <UserCharDetailContext.Provider value={{ uuid, charId }}>
        <UserCharDetail />
      </UserCharDetailContext.Provider>

      <LinearGradient
        className="w-full h-[600px] absolute bottom-0"
        colors={["#00000000", "#000000"]}
      />
    </View>
  );
}
