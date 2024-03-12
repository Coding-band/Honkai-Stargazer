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
import Header2 from "../components/global/Header2/Header2";
import { LOCALES } from "../../locales";
import { dynamicHeightBottomBar, dynamicHeightScrollView, dynamicHeightTopHeader } from "../constant/ui";
import { cn } from "../utils/css/cn";
import PageStars from "../components/global/PageStars/PageStars";
import CheckBox from '@react-native-community/checkbox';
import officalCharId from "../../map/character_offical_id_map";
import Animated, {
  SharedValue,
  useAnimatedStyle,
  withSpring,
} from "react-native-reanimated";
import Button from "../components/global/Button/Button";
import ListboxUp from "../components/global/ListboxUp/ListboxUp";
import UpButton from "../components/global/UpButton/UpButton";
import DropDownMenuButton from "../components/LotteryScreen/DropDownMenuButton";
import WrapPopUp from "../components/WrapAnalysisScreen/WrapPopUp"
import GachaHandler from "../utils/gacha/GachaHandler";
import { LanguageEnum } from "../utils/hoyolab/language/language.interface";

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

  //躍遷頁面List
  const pageList = [
    {page:1, title:LOCALES[appLanguage].WrapStaticPool}, //常駐躍遷
    {page:2, title:LOCALES[appLanguage].WrapNewbiePool}, //新手躍遷
    {page:11, title:LOCALES[appLanguage].WrapCharPool}, //角色活動躍遷
    {page:12, title:LOCALES[appLanguage].WrapLcPool}, //光錐活動躍遷
  ]

  //躍遷數據URL
  const [wrapURL, setWrapURL] = useState("")

  //檢查是否已經導入了躍遷數據
  const [haveData, setHaveData] = useState(false)

  //檢查是否選取展示五星和四星
  const [showRare4and5, setShowRare4and5] = useState(false)

  //選取了哪個躍遷頁面？
  const [selectedPage, setSelectedPage] = useState(2)

  //開啟了匯入頁面？
  const [openedImportPage, setOpenedImportPage] = useState(false)

  //Scale of your screen
  const dpScale = Dimensions.get('screen').scale

  const barMaxLength = (Dimensions.get('window').width) - (36 + 10) * dpScale

  const tmpData = [
    { "title": LOCALES[appLanguage].WrapInfoPity, "data": "30%" },
    { "title": LOCALES[appLanguage].WrapAvgGetUP, "data": 40 },
    { "title": LOCALES[appLanguage].WrapTotalPull, "data": 734 },
  ]

  const avgGetUP = 40;
  const goodRateColor = ["#FFD0709A", "#FFD070"];
  const midRateColor = ["#F3F9FF9A", "#F3F9FF"];
  const poorRateColor = ["#AB6F669A", "#AB6F66"];

  const tmpDataDetails = [
    { "charId": 1306, "rare": 5, "pulled": 36, "isPity": false },
    { "charId": 1302, "rare": 5, "pulled": 72, "isPity": false },
    { "charId": 1215, "rare": 4, "pulled": 9, "isPity": true },
    { "charId": 1210, "rare": 4, "pulled": 1, "isPity": false },
    { "charId": 1208, "rare": 5, "pulled": 48, "isPity": false },
    { "charId": 1205, "rare": 5, "pulled": 24, "isPity": false },
    { "charId": 1110, "rare": 4, "pulled": 82, "isPity": false },
    { "charId": 1107, "rare": 5, "pulled": 1, "isPity": true },
    { "charId": 1009, "rare": 4, "pulled": 6, "isPity": false },
    { "charId": 1008, "rare": 4, "pulled": 5, "isPity": false },
    { "charId": 1013, "rare": 4, "pulled": 8, "isPity": false },
    { "charId": 1002, "rare": 5, "pulled": 15, "isPity": false },
    { "charId": 1009, "rare": 4, "pulled": 3, "isPity": false },
    { "charId": 1003, "rare": 5, "pulled": 3, "isPity": false },
  ]

  console.log(wrapURL)

  const minDpOfText = 14

  return (
    <View style={{ flex: 1 }} className="overflow-hidden">
      <StatusBar style="dark" />
      <WallPaper isBlur />
      <LinearGradient
        className="w-full h-[600px] absolute bottom-0"
        colors={["#00000000", "#000000"]}
      />

      {/* 頂部導航欄 */}
      <Header leftBtn="close" Icon={SCREENS.WrapAnalysisPage.icon}>
        {LOCALES[appLanguage].WrapAnalysis}
      </Header>

      <View
        style={{
          justifyContent: "center",
          padding: 16,
          width: "100%",
          flexDirection: "column",
          flex: 1
        }}
        className={dynamicHeightTopHeader}
      >

        {/* 内容摘要 - 卡片 */}
        <View style={{
          backgroundColor: "#AAAAAA66",
          borderRadius: 10,
          borderWidth: 0,
        }}>
          {/* 内容摘要 - 未/已導入資料 */}
          {false ? (<Text className="text-[13px] font-[HY65] text-[#FFFFFF] pl-[48px] pr-[48px] pt-[60px] pb-[60px]"
            style={{
              justifyContent: "center",
              alignSelf: "center",
            }}
          >
            {LOCALES[appLanguage].WrapNeedInit}
          </Text>
          ) : (
            <View>
              <View
                style={{
                  padding: 20,
                  justifyContent: 'center',
                  alignContent: 'center',
                  alignItems: 'center'
                }}
              >
                {/* 運氣評價 - 星星 */}
                <PageStars
                  count={5}
                />

                {/* 運氣評價 - 標題 */}
                <Text className="text-[13px] font-[HY65] text-[#FFFFFF] p-[4px]"
                  style={{
                    justifyContent: "center",
                    alignSelf: "center",
                  }}
                >
                  {LOCALES[appLanguage].WrapLuckTitle}
                </Text>

                {/* 運氣評價 - 細節 */}
                <View
                  style={{ flexDirection: 'row', width: '100%', }}
                >
                  {tmpData.map((data, index) => (
                    <View key={"wrapinfo_" + index} style={{ flex: 1 }}>
                      <Text className="text-[24px] font-[HY65] text-[#FFFFFF] p-[4px]"
                        style={{
                          justifyContent: "center",
                          alignSelf: "center",
                        }}
                      >
                        {data.data}
                      </Text>
                      <Text className="text-[13px] font-[HY65] text-[#FFFFFF] p-[4px]"
                        style={{
                          justifyContent: "center",
                          alignSelf: "center",
                        }}
                      >
                        {data.title}
                      </Text>
                    </View>
                  ))}
                </View>
              </View>
            </View>
          )}
        </View>

        {/* 詳細記錄 - 篩選*/}
        <View
          style={{
            paddingTop: 15,
            paddingBottom: 15,
            flexDirection: 'row',
            justifyContent: 'space-between',
            alignItems: 'center',
            width: "100%"
          }}
        >
          <Text className="text-[15px] font-[HY65] text-[#FFFFFF]">
            {LOCALES[appLanguage].WrapDetails}
          </Text>
          <View style={{ flexDirection: 'row', alignItems: 'center' }}>
            <Text className="text-[15px] font-[HY65] text-[#FFFFFF]">
              {LOCALES[appLanguage].WrapFourFiveStarRecord}
            </Text>
            <CheckBox
              style={{ height: 24, width: 24 }}
              tintColor="#FFFFFFF40"
              tintColors={{ true: "#FFFFFF40", false: "#FFFFFF40" }}
              value={showRare4and5}
              onValueChange={setShowRare4and5}
            >
            </CheckBox>
          </View>
        </View>

        {/* 詳細記錄 - 列表*/}
        <ScrollView 
          style={{
            backgroundColor: "#AAAAAA66",
            borderRadius: 10,
            borderWidth: 0,
            flex: 1,
            flexWrap: 'wrap',
            marginBottom : 21
          }}
        >
          <View style={{ padding: 8 }}>
            {/* 詳細記錄 - 列表物件*/}
            {tmpDataDetails.filter((data) => (data.rare === 4 && showRare4and5) || data.rare === 5).map((data) => {
              const dataFull = getCharFullData(officalCharId[data.charId])
              return (
                <View style={{ height: 36, margin: 8, flexDirection: 'row', backgroundColor: "#31313100" }}>
                  {/* 角色圖片*/}
                  <Image cachePolicy="none"
                    transition={200}
                    className="rounded-full"
                    style={{ height: 36, width: 36 }}
                    source={CharacterImage[officalCharId[data.charId]]?.icon}
                  />

                  {/* 角色名字 & 出貨抽數*/}
                  <View style={{ height: 36, width: "100%", paddingLeft: 10 }}>
                    <Text className="text-[11px] font-[HY65] text-[#FFFFFF]"
                      style={{
                        justifyContent: "center",
                        alignSelf: 'flex-start',
                        paddingBottom: 2
                      }}
                    >
                      {dataFull?.name}
                    </Text>
                    <LinearGradient
                      colors={(data?.pulled <= avgGetUP ? goodRateColor : data?.pulled <= 70 ? midRateColor : poorRateColor)}
                      start={{ x: 0, y: 0 }}
                      end={{ x: 0.58, y: 0 }}
                      style={{
                        flexDirection: 'row',
                        justifyContent: "space-between",
                        padding: 3,
                        width: (barMaxLength * data?.pulled / 90 < (minDpOfText * (data?.isPity ? 2 : 1)) ? minDpOfText * (data?.isPity ? 2 : 1) : barMaxLength * data?.pulled / 90),
                      }}
                    >
                      <Text className="text-[12px] font-[HY65] text-[#000000]">
                        {data?.pulled}
                      </Text>
                      {data?.isPity && (
                        <Text className="text-[12px] font-[HY65] text-[#000000]">
                          歪
                        </Text>
                      )}
                    </LinearGradient>
                  </View>
                </View>
              )
            })}

          </View>
        </ScrollView>

        {/* 底部功能區 */}
        <Animated.View
          style={{
            justifyContent: "center",
            alignItems: "center",
            alignSelf: "center",
            flexDirection: "row",
            width: "100%",
            gap: 8,
            paddingBottom: (dynamicHeightBottomBar) / 1.5
          }}
        >
          <Button width={46} height={46} onPress={() => setOpenedImportPage(true)}>
            <Image cachePolicy="none" className="w-6 h-6" source={require("../../assets/images/ui_icon/WrapImport.svg")} />
          </Button>
          <View style={{flex: 1,flexWrap:'wrap'}}>
            
          <ListboxUp style={{alignSelf: "center" }}
            //top={8}
            bottom={46 + 4}
            button={
              <UpButton
                width={"100%"}
                style={{flexWrap:'wrap',flexDirection:"row"}}
                height={46}
                withArrow
              >
                <Text className="text-[14px] font-[HY65] text-[#000000] leading-5 pr-5 pl-5" numberOfLines={1}>
                  {pageList[selectedPage].title}
                </Text>
              </UpButton>
            }
            value={selectedPage}
                onChange={(index) => {
                  setSelectedPage(index);
                }}
          >
            {pageList.map((pages,index) => (
                    <ListboxUp.Item key={pages.page} value={index}>
                      {/* @ts-ignore */}
                      {pages.title}
                    </ListboxUp.Item>
                  ))
            }
          </ListboxUp>
          </View>
          <Button width={46} height={46} onPress={async() => {
            let arr = [] 
            
            console.log("XXX : "+
              JSON.stringify(await new GachaHandler().gachaCombineHandler(
                "h6pFKYpWfg7QwO9VaYLFUmYUeAF%2F99RDqsGPDt63fdeAJUgaZzJ7RQ9PYGWSuOcMwPG1vo5GVK2XqnNvU%2FmdAa2vwwjN13FD5qKEHmEvV6kS%2BijC6icgq%2BOvHDlP3S2DzyPbmvvCoRKCvfslBrEYwxVGBIfXPw9w39bNKBnGu7C3cvbUWF9Eu4iLkaZLUm2C9OkKcBhgoj6YTs4koXNwWdmmjFTd8TwPBwyRZppSyDC4Keg8DYgJy%2FLttqm9BTG0m80xWL9jIl4NP59qk4DKowADPKJQn51rZkU3AXkEqTQAMSPgDB3mw%2FYuQbGw5vtxcdvfJHD5f1tph21yDTYJj240Jv83cYtgpjwg%2FPM8X6jkVT7SQHyOogY6pzZPZIKrqTQQMQCo6K1KuaSRZLyn6aKGiZ%2B903PuKn1OZeMPOqf%2F4OWK%2BM3iBbHdlx%2BgdVqOUwj0Sn9nBPxiVS4NLm02XnYVQwGfo4Hht62ErGIgyua1DyngZ3Dct1gkeLyQoq4dY2SdzovUK7z14TtDx7QNJ%2FLQ7nUQyW99CPEoTt7PUyWFPJEemRTjWZyDjGH%2BEsS%2FCl1NzNuQAmb1%2ByfjbC57%2FaOrX7%2FOf6GoQPo1q2ipSusohZtvfsQ9CtL6syxD63X6Gq73lbmC2qqp55aBbUj8WdahQawomNOLQaZ6HXtwJbM%3D",
                arr,
                "zh-tw" as LanguageEnum,
                0,
                11,
                "0",
                20
              ))
            )
          }}>
            <Image cachePolicy="none" className="w-6 h-6" source={require("../../assets/images/ui_icon/WrapAccount.svg")} />
          </Button>
        </Animated.View>
      </View>
      {openedImportPage && <WrapPopUp 
        isOpened={openedImportPage} 
        setIsOpened={setOpenedImportPage}
        setWrapURL={setWrapURL}
        />
      }
    </View>
  )
}