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
import { TouchableOpacity } from "react-native-gesture-handler";
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
import { getCharAttrData } from "../utils/calculator/getAttrData";
import useTextLanguage from "../language/TextLanguage/useTextLanguage";
import { CharacterName } from "../types/character";
import { CombatType } from "../types/combatType";
import { ExpoImage } from "../types/image";
import Button from "../components/global/Button/Button";
import { LOCALES } from "../../locales";
import Toast from "../utils/toast/Toast";
import makePulls, { PullConfig, PullInfo, PullType } from "../utils/lottery/LotterySimulator";
import useLocalState from "../hooks/useLocalState";
import { dynamicHeightBottomBar } from "../constant/ui";
import LotteryListBox from "../components/LotteryScreen/LotteryListbox/LotteryListbox";
import Lightcone from "../../assets/images/images_map/lightcone";
import DropDownMenuButton from "../components/LotteryScreen/DropDownMenuButton";
import LightConeCard from "../components/global/LightConeCard/LightConeCard";
import { LightconeName } from "../types/lightcone";

export default function LotteryScreen() {
    const { language: appLanguage } = useAppLanguage();
    const { language: textLanguage } = useTextLanguage();
    const navigation = useNavigation();

    return(
        <View style={{ flex: 1 }} className="overflow-hidden">
            <StatusBar style="dark" />
            <WallPaper isBlur />
            <LinearGradient
                className="w-full h-[600px] absolute bottom-0"
                colors={["#00000000", "#000000"]}
            />
            <HeaderAlpha
            />
        </View>
    )
}