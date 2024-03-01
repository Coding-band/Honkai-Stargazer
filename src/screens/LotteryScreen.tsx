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

type CharListItem = {
  id: CharacterName;
  name: string;
  rare: number;
  path: Path;
  combatType: CombatType;
  image: ExpoImage;
};

type LcListItem = {
  id: LightconeName;
  name: string;
  rare: number;
  path: Path;
  image: ExpoImage;
};

export default function LotteryScreen() {
  const { language: appLanguage } = useAppLanguage();
  const { language: textLanguage } = useTextLanguage();
  const navigation = useNavigation();
  const [charCardListData, setCharCardListData] = useState<CharListItem[]>();
  const [lcCardListData, setLcCardListData] = useState<LcListItem[]>();

  const typeWithPageIndex = [PullType.PULL_LIMIT_CHAR, PullType.PULL_STATIC, PullType.PULL_LIMIT_LIGHTCONE]

  const tmpDataFromJSON = [
    {"version": "2.0", "phase" : 2, "versionCode": 2004, "type" : "CHAR", "special_rare5" : ["Jing Yuan"], "special_rare4": ["Sampo", "Qingque", "Hanya"], "title":{"en" : "Swirl of Heavenly Spear", "zh_hk" : "天戈麾斥", "vocchinese" : "狂三崩鐵版"}},
    {"version": "2.0", "phase" : 2, "versionCode": 2003, "type" : "CHAR", "special_rare5" : ["Sparkle"], "special_rare4": ["Sampo", "Qingque", "Hanya"], "title":{"en" : "Sparkling Splendor", "zh_hk" : "焰錦遊魚", "vocchinese" : "狂三崩鐵版"}},
    {"version": "2.0", "phase" : 1, "versionCode": 2002, "type" : "CHAR", "special_rare5" : ["Black Swan"], "special_rare4": ["Misha", "Tingyun", "Guinaifen"], "title":{"en" : "Swirl of Heavenly Spear", "zh_hk" : "鏡影婆娑", "vocchinese" : "狂三崩鐵版"}},
    {"version": "2.0", "phase" : 1, "versionCode": 2001, "type" : "CHAR", "special_rare5" : ["Dan Heng • Imbibitor Lunae"], "special_rare4": ["Misha", "Tingyun", "Guinaifen"], "title":{"en" : "Sparkling Splendor", "zh_hk" : "濯世垂虹", "vocchinese" : "狂三崩鐵版"}},
    {"version": "2.0", "phase" : 2, "versionCode": 2011, "type" : "LIGHTCONE", "special_rare5" : ["Void"], "special_rare4": ["Sampo", "Qingque", "Hanya"], "title":{"en" : "I am dumb", "zh_hk" : "測試光錐1", "vocchinese" : "狂三崩鐵版"}},
    {"version": "2.0", "phase" : 2, "versionCode": 2012, "type" : "LIGHTCONE", "special_rare5" : ["Dance! Dance! Dance!"], "special_rare4": ["Void"], "title":{"en" : "I am dumb", "zh_hk" : "先去洗個澡2", "vocchinese" : "狂三崩鐵版"}},
    {"version": "1.6", "phase" : 2, "versionCode": 1602, "type" : "CHAR", "special_rare5" : ["Black Swan"], "special_rare4": ["Guinaifen", "Misha", "Tingyun"], "title":{"en" : "I am dumb", "zh_hk" : "名字暫時這樣改"}},
    {"version": "1.6", "phase" : 1, "versionCode": 1601, "type" : "CHAR", "special_rare5" : ["Black Swan"], "special_rare4": ["Guinaifen", "Misha", "Tingyun"], "title":{"en" : "I am dumb", "zh_hk" : "名字暫時這樣改"}},
    {"version": "1.0", "phase" : -1, "versionCode": 1000, "type" : "STATIC", "special_rare5" : [], "special_rare4": [], "title":{"en" : "I am dumb", "zh_hk" : "群星躍遷", "vocchinese" : "常駐池"}},
  ]

  const poolItems : Array<PullInfo> = tmpDataFromJSON.map((data) => {
    let poolItem = new PullInfo();
    poolItem.version = data.version;
    poolItem.phase = data.phase;
    poolItem.versionCode = data.versionCode;
    poolItem.type = data.type;
    poolItem.special_rare5 = data.special_rare5;
    poolItem.special_rare4 = data.special_rare4;
    poolItem.title = data.title;
    return poolItem
  })

  //紀錄當前選取了哪個卡池
  const [selectedPage, setSelectedPage] = useState<number>(
    //"user-pull-simulator-select-page",
    0
  );
  
  const [selectedPool, setSelectedPool] = useState<Array<PullInfo>>(
    //"user-pull-simulator-select-pool",
    [
      poolItems.filter((pool) => pool.type === "CHAR").sort((a,b) => {return b.versionCode - a.versionCode})[0],
      poolItems.filter((pool) => pool.type === "STATIC").sort((a,b) => {return b.versionCode - a.versionCode})[0],
      poolItems.filter((pool) => pool.type === "LIGHTCONE").sort((a,b) => {return b.versionCode - a.versionCode})[0]
    ]
  );
  
  //抽卡紀錄
  const [pullRecord, setPullRecord] = useLocalState<Array<String>>(
    "user-pull-simulator-record7",
    []
  )

  const [pullCharConfig, setPullCharConfig] = useLocalState<PullConfig>(
    "user-pull-simulator-config-char",
    {"isMustGetRare4": false, "isMustGetRare5": false, "pullsAfterRare4": 0, "pullsAfterRare5": 0}
  )
  const [pullLcConfig, setPullLcConfig] = useLocalState<PullConfig>(
    "user-pull-simulator-config-lc",
    {"isMustGetRare4": false, "isMustGetRare5": false, "pullsAfterRare4": 0, "pullsAfterRare5": 0}
  )
  const [pullStaticConfig, setPullStaticConfig] = useLocalState<PullConfig>(
    "user-pull-simulator-config-static",
    {"isMustGetRare4": false, "isMustGetRare5": false, "pullsAfterRare4": 0, "pullsAfterRare5": 0}
  )

  const configs = [pullCharConfig,pullStaticConfig,pullLcConfig]

  function makeOnePull(){    
    handlePullResult(makePulls(selectedPool[selectedPage],configs[selectedPage], 1))
  }

  function makeTenPull(){        
    handlePullResult(makePulls(selectedPool[selectedPage],configs[selectedPage], 10))
  }

  function handlePullResult(result : object){
      const work = (typeWithPageIndex[selectedPage] === PullType.PULL_LIMIT_CHAR ? setPullCharConfig(result["pullConfig"])
      : typeWithPageIndex[selectedPage] === PullType.PULL_STATIC ? setPullStaticConfig(result["pullConfig"])
      : setPullLcConfig(result["pullConfig"])
      )
      setPullRecord(pullRecord.concat(result["pullArray"]))
      Toast("獲得了 : "+result["pullArray"].map((item : { itemId: any; }) => ((getCharFullData(item.itemId, textLanguage) || getLcFullData(item.itemId, textLanguage)).name))).toString();    
  }

  const handleCharPress = useCallback((charId: string, charName: string) => {
    // @ts-ignore
    navigation.push(SCREENS.CharacterPage.id, {
      id: charId,
      name: charName,
    });
  }, []);

  const handleLcPress = useCallback((lcId: string, lcName: string) => {
    // @ts-ignore
    navigation.push(SCREENS.LightconePage.id, {
      id: lcId,
      name: lcName,
    });
  }, []);

  const handleRecordPress = useCallback(() => {
    // @ts-ignore
    navigation.push(SCREENS.LotteryRecordPage.id, {
    });
  }, []);

  useEffect(() => {
    setCharCardListData(
      characterList.map((char) => {
        const charId = char.name as CharacterName;
        const charFullData = getCharFullData(charId, textLanguage);
        return {
          id: charId,
          name: charFullData?.name || char.name,
          rare: char.rare,
          combatType: char.element as CombatType,
          path: char.path as Path,
          image: CharacterImage[char.name as CharacterName]?.icon,
          version: char.version,
        };
      })
    );
    
  }, []);

  useEffect(() => {
    setLcCardListData(
      lightconeList.map((lc) => {
        const lcId = lc.name as LightconeName;
        const lcFullData = getLcFullData(lcId, textLanguage);
        return {
          id: lcId,
          name: lcFullData?.name || lc.name,
          rare: lc.rare,
          path: lc.path as Path,
          image: LightconeImage[lc.name as LightconeName]?.icon,
          version: lc.version,
        };
      })
    );
    
  }, []);

  return (
    <View style={{ flex: 1 }} className="overflow-hidden">
      <StatusBar style="dark" />
      <WallPaper isBlur />
      <LinearGradient
        className="w-full h-[600px] absolute bottom-0"
        colors={["#00000000", "#000000"]}
      />

      {/* 頂部導航欄 */}
      <HeaderAlpha
        children={
          <LotteryHeaderChild
            selectedChild={selectedPage}
            setSelectedChild={setSelectedPage}
          />
        }
        rightBtn={<LotteryRecordBtn onPress={handleRecordPress}/>}
      />

      {/* 限定角色圖片 */}
      <Image cachePolicy="none"
        style={[{ flex : 1}]}
        source={
          CharacterImage[selectedPool[selectedPage].special_rare5[0]]?.imageSplash ||
          Lightcone[selectedPool[selectedPage].special_rare5[0]]?.imageFull || 
          require("../../assets/images/ui_icon/static_lottery_bg.webp")}
      />

      {/* 空格區 */}
      <View style={{ marginTop: 6 , marginBottom: 8 }}></View>
      
      {/* 躍遷相關 */}
      <View style={{ justifyContent: "center"}}>
        
        {/* 選取卡池Spinner */}
        <LotteryListBox key={"lotteryListBox"+selectedPage} style={{ alignSelf: "center", marginBottom : 8, marginLeft : 16, marginRight : 16}}
          //top={8}
          bottom={32 + 4}
          button={
            <DropDownMenuButton
              width={"100%"}
              height={32}
              withArrow
            >
              <Text className="text-[14px] font-[HY65] text-[#F3F9FF66] leading-5 pr-5 pl-5" numberOfLines={1}>
                {
                  ( selectedPool[selectedPage].phase === -1 
                    ? selectedPool[selectedPage].title[appLanguage]
                    //: selectedPool[selectedPage].version + " " + (selectedPool[selectedPage].phase === 1 ? LOCALES[appLanguage].MOCPart1 : LOCALES[appLanguage].MOCPart2)
                    : selectedPool[selectedPage].version + " - " + (getCharFullData(selectedPool[selectedPage].special_rare5[0], textLanguage) || getLcFullData(selectedPool[selectedPage].special_rare5[0], textLanguage)).name
                  )
                  //+selectedPool[selectedPage].title[appLanguage]
                }
              </Text>
            </DropDownMenuButton>
          }
          value={selectedPool[selectedPage]}
          onChange={(poolSelect : PullInfo) => {
            selectedPool[selectedPage] = poolSelect
            //https://stackoverflow.com/questions/56266575/why-is-usestate-not-triggering-re-render
            //在這裏卡住了1小時
            setSelectedPool([...selectedPool]) 
          }}
        >  
          {poolItems.filter((pool) => pool.type === typeWithPageIndex[selectedPage]).sort((a,b) => {return (b.versionCode - a.versionCode)}).map((pool) => (
              <LotteryListBox.Item key={pool.versionCode} value={pool}>
                  {/* @ts-ignore */}
                  {
                    ( pool.phase === -1 
                      ? pool.title[appLanguage]
                      //: pool.version +" " + (pool.phase === 1 ? LOCALES[appLanguage].MOCPart1 : LOCALES[appLanguage].MOCPart2) + " "
                      : pool.version + " - " + (getCharFullData(pool.special_rare5[0], textLanguage) || getLcFullData(pool.special_rare5[0], textLanguage)).name
                    )
                  }
                </LotteryListBox.Item>
              )) || []
          }
        </LotteryListBox>
        
        {/* 卡池名稱 */}
        <Text
          className="text-[29px] font-[HY65] text-white"
          style={[
            { fontSize: 28 },
            { color: "#FFFFFF" },
            { textAlign: "center" },
            {},
          ]}
        >
          {selectedPool[selectedPage].title[appLanguage]}
        </Text>

        {/* 限定UP資訊 */}
        <View 
          style={{
              flexDirection: "row",
              flexWrap: "wrap",
              gap: 0,
              justifyContent: "center",
              marginLeft:8,
              marginTop:10,
              marginRight:8
          }}
        >
          {
            charCardListData?.filter((char) => {
              return selectedPool[selectedPage].special_rare4.includes(char.id) || selectedPool[selectedPage].special_rare5.includes(char.id)
            }).sort((a,b) => {return b.rare - a.rare}).map((char, i) => (
              (<CharCard 
                key={i} {...char}
                onPress={handleCharPress}
              />)
            ))
            }
            {
            lcCardListData?.filter((lc) => {
              return selectedPool[selectedPage].special_rare4.includes(lc.id) || selectedPool[selectedPage].special_rare5.includes(lc.id)
            }).sort((a,b) => {return b.rare - a.rare}).map((lc, i) => (
              (<LightConeCard 
                key={i} {...lc}
                onPress={() => {
                  handleLcPress(lc.id, lc.name)
                }}
                
              />)
            ))
          
          }
        </View>

        {/* 躍遷按鈕 */}
        <View 
          style={{
              flexDirection: "row",
              flexWrap: "wrap",
              gap: 11,
              maxHeight: 46,
              height:46,
              maxWidth : "100%",
              justifyContent: "center",
              marginLeft:8,
              marginBottom:10,
              marginTop:16,
              marginRight:8
          }}
        >
          <Button onPress={makeOnePull} >
            <Text className="font-[HY65] text-[16px]" style={{marginLeft: 24,marginRight: 24}}>
              {LOCALES[appLanguage].MakeOnePull}
            </Text>
          </Button>
          <Button onPress={makeTenPull}  >
            <Text className="font-[HY65] text-[16px]" style={{marginLeft: 24,marginRight: 24}}>
              {LOCALES[appLanguage].MakeTenPull}
            </Text>
          </Button>
        </View>

        <View style={{marginBottom: dynamicHeightBottomBar }}></View>
      </View>

    </View>
  );
}
