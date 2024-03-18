import { View, Text, Dimensions, Platform } from "react-native";
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
import officalLcId from "../../map/lightcone_offical_id_map";
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
import GachaHandler, { GachaPoolArray } from "../utils/gacha/GachaHandler";
import { LanguageEnum } from "../utils/hoyolab/language/language.interface";
import { GachaInfo, GachaSummary } from "../utils/gacha/GachaHandler";
import lottery_list from "../../data/lottery_data/lottery_list.json";
import BouncyCheckbox from "react-native-bouncy-checkbox";
/**
 * 本功能確認將依照SRGF (https://uigf.org/zh/standards/SRGF.html) 
 * 設計SRGF制式的匯入及匯出功能
 * 以及預計將來會進一步擴展自適配/轉譯其他標準
 * 
 * 本功能默認使用「躍遷紀錄連結」（從官方抽卡紀錄截取）
 * 不排除以後添加其他更加便利的方案（https://github.com/UIGF-org/mihoyo-api-collect/issues/40#issuecomment-1983776127）
 * 
 * 看不明是十分正常 感到混亂也是十分正常
 * 因爲我在開發這個功能的時候 思緒十分散
 * 先説一句Sorry ...
*/


export default function WrapAnalysisScreen() {
  const route = useRoute<RouteProp<ParamList, "WrapAnalysis">>();
  const { language: appLanguage } = useAppLanguage();
  const { language: textLanguage } = useTextLanguage();
  const navigation = useNavigation();

  //躍遷頁面List
  const pageList = [
    { page: 1, title: LOCALES[appLanguage].WrapStaticPool }, //常駐躍遷
    { page: 2, title: LOCALES[appLanguage].WrapNewbiePool }, //新手躍遷
    { page: 11, title: LOCALES[appLanguage].WrapCharPool }, //角色活動躍遷
    { page: 12, title: LOCALES[appLanguage].WrapLcPool }, //光錐活動躍遷
  ]

  //躍遷數據URL
  const [wrapURL, setWrapURL] = useState("")

  //導入了躍遷數據
  const [gachaData, setGachaData] = useState<GachaInfo[]>([])

  //躍遷紀錄總結
  const [gachaSummary, setGachaSummary] = useState<GachaSummary[]>([])

  //躍遷第一筆紀錄ID
  const [gachaEndId, setGachaEndId] = useState("0")

  //檢查是否選取展示五星和四星
  const [showRare4and5, setShowRare4and5] = useState(false)

  //選取了哪個躍遷頁面？
  const [selectedPage, setSelectedPage] = useState(2)

  //開啟了匯入頁面？
  const [openedImportPage, setOpenedImportPage] = useState(false)

  //Scale of your screen
  const dpScale = Dimensions.get('screen').scale

  const barMaxLength = (Dimensions.get('window').width) - (36 + 10) * dpScale

  const goodRateColor = ["#FFD0709A", "#FFD070"];
  const midRateColor = ["#F3F9FF9A", "#F3F9FF"];
  const poorRateColor = ["#AB6F669A", "#AB6F66"];

  useEffect(() => {
    async function refreshData() {
      setGachaData((await new GachaHandler().getGachaRecord()) as GachaInfo[]);
      setGachaSummary((await new GachaHandler().getGachaSummary()) as GachaSummary[])
      setGachaEndId((await new GachaHandler().getGachaEndId()))
    }

    refreshData();
  }, [])

  const tmpData = [
    { "title": LOCALES[appLanguage].WrapInfoPity, "data": ((1 - gachaSummary[selectedPage]?.rare5HavePityPercent) * 100)?.toFixed(1) + "%" },
    { "title": LOCALES[appLanguage].WrapAvgGetUP, "data": (isNaN(parseFloat(gachaSummary[selectedPage]?.rare5GachaAverage)) ? "?" : gachaSummary[selectedPage]?.rare5GachaAverage?.toFixed(1)) },
    { "title": LOCALES[appLanguage].WrapTotalPull, "data": gachaSummary[selectedPage]?.totalPulls },
  ]
  const avgGetUP = gachaSummary[selectedPage]?.rare5GachaAverage;

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
          {gachaData.length === 0 ? (<Text className="text-[13px] font-[HY65] text-[#FFFFFF] pl-[48px] pr-[48px] pt-[60px] pb-[60px]"
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
                    !(selectedPage === 0 && index === 0) && (<View key={"wrapinfo_" + index} style={{ flex: 1 }}>
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
                    </View>)
                  ))}
                </View>
              </View>
            </View>
          )}
        </View>

        {gachaData.length === 0 ? (
          <View
            style={{
              backgroundColor: "#AAAAAA00",
              borderRadius: 10,
              borderWidth: 0,
              flex: 1,
              flexWrap: 'wrap',
              marginBottom: 21
            }}
          ></View>
        ) : (
          <>
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
                <BouncyCheckbox
                  style={{ height: 24, width: 24, marginLeft: 8 }}
                  fillColor="#FFFFFF40"
                  unfillColor="#FFFFFF40"
                  iconStyle={{ borderRadius: 3 }}
                  innerIconStyle={{ borderRadius: 3, }}
                  onPress={setShowRare4and5}
                >
                </BouncyCheckbox >
              </View>
            </View>

            {/* 詳細記錄 - 列表*/}
            <ScrollView
              horizontal={Platform.OS === "android" ? false : true}
              alwaysBounceHorizontal={false}
              style={{
                backgroundColor: "#AAAAAA66",
                borderRadius: 10,
                borderWidth: 0,
                flex: 1,
                flexWrap: 'wrap',
                marginBottom: 21
              }}
            >
              <View style={{ padding: 8 }}>
                {/* 詳細記錄 - 列表物件*/}
                {gachaData.filter((data: GachaInfo) =>
                  ((parseInt(data.rank_type) === 4 && showRare4and5) || parseInt(data.rank_type) === 5) &&
                  (parseInt(data.gacha_type) === GachaPoolArray[selectedPage])
                ).map((data, index) => {
                  const gacha_id = parseInt(data.item_id)
                  const dataFull = (gacha_id >= 10000 ? getLcFullData(officalLcId[gacha_id], appLanguage) : getCharFullData(officalCharId[gacha_id], appLanguage))
                  const dataIMG = (gacha_id >= 10000 ? LightconeImage[officalLcId[gacha_id]]?.icon : CharacterImage[officalCharId[gacha_id]]?.icon)
                  const dataPulled = (data?.afterPulled === undefined ? 1 : data?.afterPulled)
                  const dataIsPity = (data?.isPity === undefined ? false : data?.isPity)
                  return (
                    <View key={index} style={{ height: 36, margin: 8, flexDirection: 'row', backgroundColor: "#31313100" }}>
                      {/* 角色圖片*/}
                      <Image cachePolicy="none"
                        transition={200}
                        className="rounded-full"
                        style={{ height: 36, width: 36 }}
                        source={dataIMG}
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
                          colors={(dataPulled <= avgGetUP ? goodRateColor : dataPulled <= 70 ? midRateColor : poorRateColor)}
                          start={{ x: 0, y: 0 }}
                          end={{ x: 0.58, y: 0 }}
                          style={{
                            flexDirection: 'row',
                            justifyContent: "space-between",
                            padding: 3,
                            width: (barMaxLength * dataPulled / 90 < (minDpOfText * (dataIsPity ? 2 : 1)) ? minDpOfText * (dataIsPity ? 2 : 1) : barMaxLength * dataPulled / 90) + 1,
                          }}
                        >
                          <Text className="text-[12px] font-[HY65] text-[#000000]">
                            {dataPulled}
                          </Text>
                          {dataIsPity && (
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
          </>
        )}


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
          <View style={{ flex: 1, flexWrap: 'wrap' }}>

            <ListboxUp style={{ alignSelf: "center" }}
              //top={8}
              bottom={46 + 8}
              button={
                <UpButton
                  width={"100%"}
                  style={{ flexWrap: 'wrap', flexDirection: "row" }}
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
              {pageList.map((pages, index) => (
                <ListboxUp.Item key={pages.page} value={index}>
                  {/* @ts-ignore */}
                  {pages.title}
                </ListboxUp.Item>
              ))
              }
            </ListboxUp>
          </View>
          <Button width={46} height={46} onPress={async () => {

          }}>
            <Image cachePolicy="none" className="w-6 h-6" source={require("../../assets/images/ui_icon/WrapAccount.svg")} />
          </Button>
        </Animated.View>
      </View>
      {openedImportPage && <WrapPopUp
        isOpened={openedImportPage}
        setIsOpened={setOpenedImportPage}
        setWrapURL={setWrapURL}
        confirmedTasks={(url: string) => {
          async function todo() {
            let arr: GachaInfo[] = [];
            const gachaInfoArray = await new GachaHandler().gachaCombineHandler(
              (url.startsWith("https://") ? encodeURIComponent(new URL(url).searchParams.get('authkey')) : url),
              arr,
              "zh-tw" as LanguageEnum,
              0,
              -1,
              "0",
              20,
              appLanguage
            ) as GachaInfo[]


            //const gachaInfoArray = await new GachaHandler().getGachaRecord();

            let preSortGachaInfoArray = gachaInfoArray.sort(
              (a: GachaInfo, b: GachaInfo) => (
                a.id - b.id
              )
            )

            //console.log(gachaInfoArray)

            let tmpGachaSummary = [
              { regionTimezone: -1, rare5Gacha: [], rare4Gacha: [], rare5GachaAverage: 0, rare5HavePityPercent: 0, totalPulls: 0, luckyRanking: 0, isInit: false } as GachaSummary,
              { regionTimezone: -1, rare5Gacha: [], rare4Gacha: [], rare5GachaAverage: 0, rare5HavePityPercent: 0, totalPulls: 0, luckyRanking: 0, isInit: false } as GachaSummary,
              { regionTimezone: -1, rare5Gacha: [], rare4Gacha: [], rare5GachaAverage: 0, rare5HavePityPercent: 0, totalPulls: 0, luckyRanking: 0, isInit: false } as GachaSummary,
              { regionTimezone: -1, rare5Gacha: [], rare4Gacha: [], rare5GachaAverage: 0, rare5HavePityPercent: 0, totalPulls: 0, luckyRanking: 0, isInit: false } as GachaSummary,
            ] as GachaSummary[]
            for (let x = 0; x < GachaPoolArray.length; x++) {
              const pool = GachaPoolArray[x]
              let isPityRare4 = false, isPityRare5 = false;
              let afterPullRare4 = 0, afterPullRare5 = 0;
              preSortGachaInfoArray
                .filter((gacha: GachaInfo) => (parseInt(gacha.gacha_type) === pool))
                .map((gacha: GachaInfo) => {
                  tmpGachaSummary[x].totalPulls += 1
                  //console.log(tmrGachaSummary[0].totalPulls + " | " + tmrGachaSummary[1].totalPulls + " | " + tmrGachaSummary[2].totalPulls + " | " + tmrGachaSummary[3].totalPulls)
                  switch (parseInt(gacha.rank_type)) {
                    case 3: {
                      afterPullRare4++
                      afterPullRare5++
                      break;
                    }
                    case 4: {
                      //根據時間判定是否歪/UP角色
                      gacha = (pool !== 1 && pool !== 2 ? updateGachaData(gacha, pool, isPityRare5, 4) : gacha);
                      gacha.afterPulled = afterPullRare4;
                      afterPullRare4 = 1
                      afterPullRare5++
                      tmpGachaSummary[x].rare4Gacha.push(gacha)
                      break;
                    }
                    case 5: {
                      //根據時間判定是否歪/UP角色
                      gacha = (pool !== 1 && pool !== 2 ? updateGachaData(gacha, pool, isPityRare5, 5) : gacha);
                      gacha.afterPulled = afterPullRare5;
                      afterPullRare4++
                      afterPullRare5 = 1
                      tmpGachaSummary[x].rare5Gacha.push(gacha)
                      break;
                    }
                  }
                })
            }
            let postSortGachaInfoArray = preSortGachaInfoArray.sort(
              (a: GachaInfo, b: GachaInfo) => (
                b.id - a.id
              )
            )

            //這個稍後弄
            setGachaEndId(postSortGachaInfoArray[0].id);

            for (let x = 0; x < GachaPoolArray.length; x++) {
              let rare5IsPity = 0, rare5TotalPulled = 0, rare5PityPulled = 0;

              for (let index = 0; index < tmpGachaSummary[x].rare5Gacha.length; index++) {
                const gacha = tmpGachaSummary[x].rare5Gacha[index]
                rare5TotalPulled += gacha.afterPulled;
                rare5PityPulled += (gacha.isPity === true ? gacha.afterPulled : 0)
                rare5IsPity += (gacha.isPity === true ? 1 : 0)
              }

              tmpGachaSummary[x].rare5GachaAverage = rare5TotalPulled / (tmpGachaSummary[x].rare5Gacha.length - rare5IsPity);
              tmpGachaSummary[x].rare5HavePityPercent = rare5IsPity / tmpGachaSummary[x].rare5Gacha.length

              //console.log(tmpGachaSummary[x].rare5GachaAverage)
            }
            //卡池合併 & 存放在同一個Array
            setGachaData(postSortGachaInfoArray);
            setGachaSummary(tmpGachaSummary);

            //console.log(JSON.stringify(finalGachaInfoArray))

            new GachaHandler().importGachaRecord(JSON.stringify(postSortGachaInfoArray))
            new GachaHandler().setGachaSummary(tmpGachaSummary)
          }

          todo();
        }}
      />
      }
    </View>
  )
}

function updateGachaData(gacha: GachaInfo, targetGacha: number, isPityRare: boolean, rare: number): GachaInfo {
  if (parseInt(gacha.gacha_type) === targetGacha) {
    const time = (new Date(gacha.time).getTime() / 1000)
    const lotteryList = lottery_list.filter((data) => (data.type === (targetGacha === 11 ? "CHAR" : "LIGHTCONE")) && (data.begin_time <= time && data.end_time >= time))
    for (let y = 0; y < lotteryList.length; y++) {
      let data = lotteryList[y]
      //中了當期UP
      if (
        (parseInt(gacha.item_id) >= 10000 //檢查是角色還是光錐
          && (rare === 4 ? data.special_rare4 : data.special_rare5).includes(officalLcId[parseInt(gacha.item_id)])) //光錐case
        || (rare === 4 ? data.special_rare4 : data.special_rare5).includes(officalCharId[parseInt(gacha.item_id)]) //角色case
      ) {
        isPityRare = false; //沒歪！
      } else {
        isPityRare = true; //歪了！
      }
      //更新資料 & 確認沒歪就直接break
      gacha.isPity = isPityRare;
      if (isPityRare === false) { break; }
    }
  }
  return gacha;
}