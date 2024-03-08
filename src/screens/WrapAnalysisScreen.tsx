import { View, Text, Dimensions } from "react-native";
import React, { useCallback, useEffect, useState } from "react";
import Header from "../components/global/Header/Header";
import { StatusBar } from "expo-status-bar";
import { SCREENS } from "../constant/screens";
import { Image } from "expo-image";
import WallPaper from "../components/global/WallPaper/WallPaper";
import { LinearGradient } from "expo-linear-gradient";
import useAppLanguage from "../language/AppLanguage/useAppLanguage";
import HeaderAlpha from "../components/global/HeaderAlpha/HeaderAlpha";
import { ScrollView, TouchableOpacity } from "react-native-gesture-handler";
import LotteryHeaderChild from "../components/LotteryScreen/LotteryHeaderChild";
import LotteryRecordBtn from "../components/LotteryScreen/LotteryRecordBtn";
import { RouteProp, useNavigation, useRoute } from "@react-navigation/native";
import { ParamList } from "../types/navigation";
import useCharData from "../context/CharacterData/hooks/useCharData";
import CharCard from "../components/global/CharCard/CharCard";
import { Path } from "../types/path";
import characterList from "../../data/character_data/character_list.json";
import lightconeList from "../../data/lightcone_data/lightcone_list.json";
import CharacterImage from "../../assets/images/images_map/chacracterImage";
import LightconeImage from "../../assets/images/images_map/lightcone";
import { getCharFullData, getLcFullData } from "../utils/data/getDataFromMap";
import useTextLanguage from "../language/TextLanguage/useTextLanguage";
import useLocalState from "../hooks/useLocalState";

/**
 * 本功能確認將依照SRGF (https://uigf.org/zh/standards/SRGF.html) 
 * 設計SRGF制式的匯入及匯出功能
 * 以及預計將來會進一步擴展自適配/轉譯其他標準
 * 
 * 本功能默認使用「躍遷紀錄連結」（從官方抽卡紀錄截取）
 * 不排除以後添加其他更加便利的方案（https://github.com/UIGF-org/mihoyo-api-collect/issues/40#issuecomment-1983776127）
 */

export default function WrapAnalysisScreen() {
  const route = useRoute<RouteProp<ParamList, "WrapAnalysis">>();
  const { language: appLanguage } = useAppLanguage();
  const { language: textLanguage } = useTextLanguage();
  const navigation = useNavigation();

  return (
    <View style={{ flex: 1 }} className="overflow-hidden">
      <StatusBar style="dark" />
      <WallPaper isBlur />
      <LinearGradient
        className="w-full h-[600px] absolute bottom-0"
        colors={["#00000000", "#000000"]}
      />

    </View>
  )
}