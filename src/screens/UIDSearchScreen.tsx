import { ScrollView, Text, View } from "react-native";
import React from "react";
import { StatusBar } from "expo-status-bar";
import { LinearGradient } from "expo-linear-gradient";
import Header from "../components/global/Header/Header";
import { SCREENS } from "../constant/screens";
import WallPaper from "../components/global/WallPaper/WallPaper";
import useAppLanguage from "../language/AppLanguage/useAppLanguage";
import CodeList from "../components/CodeScreen/CodeList/CodeList";
import Searchbar from "../components/UIDSearchScreen/UIDSearch/UIDSearchbar/UIDSearchbar";
import { Image } from "expo-image";
import UIDSearch from "../components/UIDSearchScreen/UIDSearch/UIDSearchbar";

export default function UIDSearchScreen() {
    const { language } = useAppLanguage();

    return (
        <View style={{ flex: 1, backgroundColor: "white" }}>
            <StatusBar style="dark" />
            <WallPaper isBlur />
            <LinearGradient
                className="absolute w-full h-full"
                colors={["#00000080", "#00000020"]}
            />
            <Header leftBtn="back" Icon={SCREENS.UIDSearchPage.icon}>
                {SCREENS.UIDSearchPage.getName(language)}
            </Header>
            <UIDSearch />
            <LinearGradient
                className="w-full h-[600px] absolute bottom-0"
                colors={["#00000000", "#000000"]}
            />
        </View>
    );
}
