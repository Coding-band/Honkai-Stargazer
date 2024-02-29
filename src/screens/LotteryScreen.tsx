import { View, Text } from "react-native";
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
import CharacterImage from "../../assets/images/images_map/chacracterImage";
import { getCharFullData } from "../utils/data/getDataFromMap";
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

type CharListItem = {
  id: CharacterName;
  name: string;
  rare: number;
  path: Path;
  combatType: CombatType;
  image: ExpoImage;
};

export default function LotteryScreen() {
  const { language: appLanguage } = useAppLanguage();
  const { language: textLanguage } = useTextLanguage();
  const navigation = useNavigation();
  const [selectedChild, setSelectedChild] = useState("charLottery1");
  const [charCardListData, setCharCardListData] = useState<CharListItem[]>();

  const tmpPullList = ["Black Swan", "Guinaifen", "Misha", "Tingyun"]

  //抽卡紀錄
  const [pullRecord, setPullRecord] = useLocalState<Array<String>>(
    "user-pull-simulator-record7",
    []
  )

  const [pullConfig, setPullConfig] = useLocalState<PullConfig>(
    "user-pull-simulator-config7",
    {"isMustGetRare4": false, "isMustGetRare5": false, "pullsAfterRare4": 0, "pullsAfterRare5": 0}
  )
    
  
  function makeOnePull(){
    let pullInfo : PullInfo = new PullInfo();
    pullInfo.special_rare4 = ["桂乃芬","米沙","停雲"]
    pullInfo.special_rare5 = ["黑天鵝"]
    
    const result = makePulls(pullInfo,pullConfig, PullType.PULL_LIMIT_CHAR, 1)
    setPullConfig(result["pullConfig"])
    setPullRecord(pullRecord.concat(result["pullArray"]))

    Toast("獲得了 : "+result["pullArray"].map((item) => item.itemId)).toString();
  }

  function makeTenPull(){
    Toast("Make 10 times pull");
    let pullInfo : PullInfo = new PullInfo();
    pullInfo.special_rare4 = ["桂乃芬","米沙","停雲"]
    pullInfo.special_rare5 = ["黑天鵝"]
        
    const result = makePulls(pullInfo,pullConfig, PullType.PULL_LIMIT_CHAR, 10)
    setPullConfig(result["pullConfig"])
    setPullRecord(pullRecord.concat(result["pullArray"]))

    Toast("獲得了 : "+result["pullArray"].map((item: { itemId: any; }) => item.itemId)).toString();
  }

  const handleCharPress = useCallback((charId: string, charName: string) => {
    // @ts-ignore
    navigation.push(SCREENS.CharacterPage.id, {
      id: charId,
      name: charName,
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

  return (
    <View style={{ flex: 1 }} className="overflow-hidden">
      <StatusBar style="dark" />
      <WallPaper isBlur />
      <LinearGradient
        className="w-full h-[600px] absolute bottom-0"
        colors={["#00000000", "#000000"]}
      />

      <HeaderAlpha
        children={
          <LotteryHeaderChild
            selectedChild={selectedChild}
            setSelectedChild={setSelectedChild}
          />
        }
        rightBtn={<LotteryRecordBtn />}
      />
      <Image cachePolicy="none"
        style={[{ height: 520, flex:1 }]}
        source={CharacterImage[tmpPullList[0]]?.imageSplash}
      />
      <View style={{ marginTop: 6 , marginBottom: 8 }}></View>
      
      <View style={{
              justifyContent: "center", backgroundColor:"#000000"}}>
        <Text
          className="text-[29px] font-[HY65] text-white"
          style={[
            { fontSize: 28 },
            { color: "#FFFFFF" },
            { textAlign: "center" },
            {},
          ]}
        >
          疏影三迭
        </Text>

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
          {charCardListData?.filter((char) => {return tmpPullList.includes(char.id)}).sort((char) => {return char.rare}).map((char, i) => (
            <CharCard 
              key={i} {...char}
              onPress={handleCharPress}
            />
          ))}
        </View>

        <View 
          style={{
              flexDirection: "row",
              flexWrap: "wrap",
              gap: 11,
              justifyContent: "center",
              marginLeft:40,
              marginBottom:10,
              marginTop:16,
              marginRight:40
          }}
        >
          <Button onPress={makeOnePull} width={140} height={46}>
            <Text className="font-[HY65] text-[16px]">
              {LOCALES[appLanguage].MakeOnePull}
            </Text>
          </Button>
          <Button onPress={makeTenPull} width={140} height={46}>
            <Text className="font-[HY65] text-[16px]">
              {LOCALES[appLanguage].MakeTenPull}
            </Text>
          </Button>
        </View>

        <View style={{marginBottom: dynamicHeightBottomBar }}></View>
      </View>

    </View>
  );
}
