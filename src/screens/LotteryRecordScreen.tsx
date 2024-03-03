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
import { getCharAtData } from "../utils/calculator/getAttrData";
import useTextLanguage from "../language/TextLanguage/useTextLanguage";
import { CharacterName } from "../types/character";
import { CombatType } from "../types/combatType";
import { ExpoImage } from "../types/image";
import Button from "../components/global/Button/Button";
import { LOCALES } from "../../locales";
import Toast from "../utils/toast/Toast";
import makePulls, { PullConfig, PullInfo, PullResult, PullType } from "../utils/lottery/LotterySimulator";
import useLocalState from "../hooks/useLocalState";
import { dynamicHeightBottomBar } from "../constant/ui";
import LotteryListBox from "../components/LotteryScreen/LotteryListbox/LotteryListbox";
import Lightcone from "../../assets/images/images_map/lightcone";
import DropDownMenuButton from "../components/LotteryScreen/DropDownMenuButton";
import LightConeCard from "../components/global/LightConeCard/LightConeCard";
import { LightconeName } from "../types/lightcone";
import { Table } from "phosphor-react-native";
import dayjs from "dayjs";
import { typeWithPageIndex } from "./LotteryScreen";


export default function LotteryScreen() {
  const route = useRoute<RouteProp<ParamList, "LotteryRecord">>();
  const { language: appLanguage } = useAppLanguage();
  const { language: textLanguage } = useTextLanguage();
  const navigation = useNavigation();

  //紀錄當前選取了哪個卡池
  const [selectedPage, setSelectedPage] = useState<number>(
    //"user-pull-simulator-select-page",
    route.params.currPage
  );

  //抽卡紀錄
  const [pullRecord, setPullRecord] = useLocalState<Array<PullResult>>(
    "user-pull-simulator-record",
    []
  )

  //列表文字基礎Style
  const listItemTextStyle = {
    fontFamily: "HY65",
    justifyContent: 'center',
    alignItems: 'center',
    color: "#FFFFFF",
    fontSize: 18,
    paddingTop: 9,
    paddingBottom: 9,
    flexWrap: "wrap",
    paddingLeft: 0,
    paddingRight: 8,
  }

  //列表中間文字Style
  const listItemCenterTextStyle = [listItemTextStyle, { flexWrap: 'nowrap', flex: 1, paddingLeft: 8, paddingRight: 8 }]

  //列表右方文字Style
  const listItemRightTextStyle = [listItemTextStyle, { textAlign: 'right', paddingLeft: 8 }]

  //四星名字Style
  const listItemRare4TextStyle = { color: "#A256E1", }

  //五星名字Style
  const listItemRare5TextStyle = { color: "#D1A96A", }

  return (
    <View style={{ flex: 1 }} className="overflow-hidden">
      <StatusBar style="dark" />
      <WallPaper isBlur />
      <LinearGradient
        className="w-full h-[600px] absolute bottom-0"
        colors={["#00000000", "#000000"]}
      />

      {/* 躍遷紀錄 */}
      <View >
        {/* 頂部導航欄 - 躍遷標題*/}
        <HeaderAlpha
          canOverLay={false}
          children={
            (<Text
              className="text-[20px] font-[HY65] text-[#FFFFFF] pb-[5px]"
              style={{
                justifyContent: "center",
                alignSelf: "center",
              }}
            >
              {LOCALES[appLanguage].LotterySimulatorRecord}
            </Text>)
          }
        />

        {/* 躍遷注意事項*/}
        <Text
          className="text-[14px] font-[HY65] text-[#FFFFFF]"
          style={{
            justifyContent: "center",
            alignSelf: "center",
          }}
        >
          {LOCALES[appLanguage].LotterySimulatorRecordNotice}
        </Text>

        {/* 卡池選擇 */}
        {false && (<View
          style={{
            justifyContent: "center",
            alignSelf: "center",
            margin: 8,
          }}
        >
          <LotteryHeaderChild
            selectedChild={selectedPage}
            setSelectedChild={setSelectedPage}
          />
        </View>)}
      </View>

      {/* 列表標題欄 */}
      <View
        style={{
          flexDirection: "row",
          width: "100%",
          paddingLeft: 16,
          paddingRight: 16,
          alignSelf: "center",
        }}>
        <Text style={listItemTextStyle}>{LOCALES[appLanguage].LotteryRecordType}</Text>
        <Text style={listItemCenterTextStyle}>{LOCALES[appLanguage].LotteryRecordName}</Text>
        <Text style={listItemRightTextStyle}>{LOCALES[appLanguage].LotteryRecordTime}</Text>
      </View>

      {/* 列表 */}
      <ScrollView
        style={{
          width: "100%",
          height: "100%",
        }}
      >

        {/* 列表主體 */}
        <View style={{ width: "100%", flex: 1, paddingBottom: 60 }}>
          {
            pullRecord.filter((pull: PullResult) => pull.pullType === typeWithPageIndex[selectedPage])
              .sort((a, b) => b.unixTime - a.unixTime)
              .map((pull: PullResult, index: number) => {
                const fullData = (getCharFullData(pull.itemId as CharacterName, textLanguage) || getLcFullData(pull.itemId as LightconeName, textLanguage))
                return (
                  <View style={{ width: "100%", paddingLeft: 16, paddingRight: 16 }}>
                    <View style={{ width: "100%", flex: 1, flexDirection: "row" }}>
                      <Text style={listItemTextStyle}>{fullData.spRequirement === undefined ? LOCALES[appLanguage].Lightcone : LOCALES[appLanguage].Character}</Text>
                      <Text style={[listItemCenterTextStyle, (fullData.rarity === 5 ? listItemRare5TextStyle : fullData.rarity === 4 ? listItemRare4TextStyle : {})]}>{fullData.name}</Text>
                      <Text style={listItemRightTextStyle}>{dayjs.unix(pull.unixTime / 1000).format("YYYY-MM-DD HH:mm")}</Text>
                    </View>

                    {/* 每十個項目加一條分隔綫 */}
                    {false && (index + 1) % 10 === 0 && (
                      <View style={{ marginLeft: 16, marginRight: 16, height: 2, backgroundColor: "#CCCCCC", borderColor: "#CCCCCC", borderRadius: 60 }}></View>
                    )}
                  </View>
                )
              })
          }
        </View>
      </ScrollView>
    </View>
  )
}