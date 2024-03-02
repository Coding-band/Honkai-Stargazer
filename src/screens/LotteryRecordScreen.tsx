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
  const [pullRecord, setPullRecord] = useLocalState<Array<String>>(
    "user-pull-simulator-record",
    []
  )


  const listItemTextStyle = {
    fontFamily: "HY65",
    justifyContent: 'center',
    alignItems: 'center',
    color: "#FFFFFF",
    fontSize: 16,
    paddingTop: 9,
    paddingBottom: 9,
  }


  const listItemRare4TextStyle = {
    color: "#A256E1",
  }
  const listItemRare5TextStyle = {
    color: "#D1A96A",
  }

  const listItemRTextStyle = [listItemTextStyle, { textAlign: 'right' }]

  let recordViewArray: Array<Array<React.JSX.Element>> = [
    [<Text style={listItemTextStyle}>{LOCALES[appLanguage].LotteryRecordType}</Text>],
    [<Text style={listItemTextStyle}>{LOCALES[appLanguage].LotteryRecordName}</Text>],
    [<Text style={listItemRTextStyle}>{LOCALES[appLanguage].LotteryRecordTime}</Text>],
  ];
  pullRecord.filter((pull) => pull.pullType === typeWithPageIndex[selectedPage]).map((data, index) => {
    const fullData = (getCharFullData(data.itemId, textLanguage) || getLcFullData(data.itemId, textLanguage))

    recordViewArray[0][index + 1] =
      <Text style={listItemTextStyle}>
        {(fullData).spRequirement === undefined ? LOCALES[appLanguage].Lightcone : LOCALES[appLanguage].Character}
      </Text>

    recordViewArray[1][index + 1] =
      <Text style={[listItemTextStyle, (fullData.rarity === 5 ? listItemRare5TextStyle : fullData.rarity === 4 ? listItemRare4TextStyle : {})]}>
        {(fullData).name}
      </Text>

    recordViewArray[2][index + 1] =
      <Text style={listItemRTextStyle}>
        {dayjs.unix(data.unixTime / 1000).format("YYYY-MM-DD HH:mm")}
      </Text>


  })

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
        {true && (<View
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

      {/* 列表 */}
      <ScrollView
        style={{
          width: "100%",
          height: "100%",
        }}
      >
        {/* 列表主體 */}
        <View
          style={{
            flex: 1,
            flexDirection: "row",
            width: "100%",
            height: "100%",
            //backgroundColor: "#FFFFFF",
            paddingLeft: 16,
            paddingRight: 16,
            alignSelf: "center",
          }}
        >
          {/* 類型 */}
          <View style={{
            flexWrap: "wrap",
            paddingRight: 8,
            //backgroundColor: "#8A63C8",
          }}>
            {recordViewArray[0]}
          </View>

          {/* 名稱 */}
          <View style={{
            flex: 1,
            paddingLeft: 8,
            paddingRight: 8,
            //backgroundColor: "#313131",
          }}>
            {recordViewArray[1]}
          </View>

          {/* 時間 */}
          <View style={{
            flexWrap: "wrap",
            //backgroundColor: "#45CC23",
            paddingLeft: 8,
          }}>
            {recordViewArray[2]}
          </View>
        </View>
      </ScrollView>
    </View>
  )
}