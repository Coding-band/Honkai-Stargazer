import { View, Text, TouchableOpacity, Dimensions } from "react-native";
import React, { useState } from "react";
import CharCard from "../../../global/CharCard/CharCard";
import useHsrInGameInfo from "../../../../hooks/mihomo/useHsrInGameInfo";
import useHsrUUID from "../../../../hooks/hoyolab/useHsrUUID";
import officalCharId from "../../../../../map/character_offical_id_map";
import {
  getCharFullData,
  getCharJsonData,
} from "../../../../utils/dataMap/getDataFromMap";
import useTextLanguage from "../../../../language/TextLanguage/useTextLanguage";
import CharacterImage from "../../../../../assets/images/images_map/chacracterImage";
import { CharacterName } from "../../../../types/character";
import { Image } from "expo-image";
import useHsrCharList from "../../../../hooks/hoyolab/useHsrCharList";
import { useNavigation } from "@react-navigation/native";
import useUserCharactersByUUID from "../../../../firebase/hooks/UserCharacters/useUserCharactersByUUID";
import Toast from "../../../../utils/toast/Toast";
import useUser from "../../../../firebase/hooks/User/useUser";
import NoPublicData from "../NoPublicData/NoPublicData";
import useUserByUUID from "../../../../firebase/hooks/User/useUserByUUID";
import { Question } from "phosphor-react-native";
import PopUpCard from "../../../global/PopUpCard/PopUpCard";
import ReactNativeModal from "react-native-modal";
import officalLightconeId from "../../../../../map/lightcone_offical_id_map";
import Lightcone from "../../../../../assets/images/images_map/lightcone";
import { LightconeName } from "../../../../types/lightcone";
import useLocalState from "../../../../hooks/useLocalState";

type Props = {
  uuid: string;
};

export default function UserInfoCharacters(props: Props) {
  const { language: textLanguage } = useTextLanguage();
  const navigation = useNavigation();

  const hsrUUID = useHsrUUID();
  const profileUUID = props.uuid;

  const isOwner = profileUUID === hsrUUID;
  const isShowInfo = useUserByUUID(profileUUID).data?.show_info;

  const { data: hsrInGameInfo } = useHsrInGameInfo(props.uuid);
  const userCharList = useUserCharactersByUUID(profileUUID).data?.characters;

  // 展櫃資料
  // @ts-ignore
  const inGameCharacters = hsrInGameInfo?.characters?.map((char: any) => {
    // @ts-ignore
    const charId = officalCharId[char.id] as CharacterName;
    const charJsonData = getCharJsonData(charId);
    const charFullData = getCharFullData(charId, textLanguage);
    return {
      id: charId,
      rare: charJsonData.rare,
      name: charFullData.name,
      rank: char.rank,
      level: "Lv " + char.level,
      image: CharacterImage[charId].icon,
      light_cone: {
        id: officalLightconeId[char.light_cone.id] as LightconeName,
        level: char.light_cone.level,
        rank: char.light_cone.rank,
      },
    };
  });

  const inGameCharactersIds: any[] = inGameCharacters?.map(
    (char: any) => char.id
  );
  // hoyolab 角色資料
  const userCharacters = userCharList
    ?.filter(
      (char: any) => !inGameCharactersIds?.includes(officalCharId[char.id])
    )
    ?.map((char: any) => {
      // @ts-ignore
      const charId = officalCharId[char.id] as CharacterName;
      const charJsonData = getCharJsonData(charId);
      const charFullData = getCharFullData(charId, textLanguage);
      return {
        id: charId,
        rare: charJsonData.rare,
        name: charFullData.name,
        rank: char.rank,
        level: "Lv " + char.level,
        image: CharacterImage[charId].icon,
        light_cone: {
          id: officalLightconeId[char?.equip?.id] as LightconeName,
          level: char?.equip?.level,
          rank: char?.equip?.rank,
        },
      };
    });

  const [showMoreChars, setShowMoreChars] = useState(false);
  const [charsDisplayMode, setCharsDisplayMode] = useLocalState(
    "user-info-chars-display-mode",
    false
  );
  const [charsDisplayWidth, setCharsDisplayWidth] = useState(0);
  const [openQuestionPopUp, setOpenQuestionPopUp] = useState(false);

  return (
    <>
      <View style={{ gap: 10 }}>
        <View style={{ alignItems: "center", gap: 8 }}>
          {/* Top */}
          <View
            style={{
              flexDirection: "row",
              justifyContent: "space-between",
              width: charsDisplayWidth,
            }}
          >
            <View
              style={{ flexDirection: "row", gap: 4, alignItems: "center" }}
            >
              <Text className="text-text text-[14px] font-[HY65]">
                公開角色
              </Text>
              <TouchableOpacity
                activeOpacity={0.35}
                onPress={() => {
                  setOpenQuestionPopUp(true);
                }}
              >
                <Question weight="fill" color="white" size={20} />
              </TouchableOpacity>
            </View>
            <TouchableOpacity
              activeOpacity={0.35}
              onPress={() => {
                setCharsDisplayMode(!charsDisplayMode);
              }}
            >
              <Text className="text-text text-[14px] font-[HY65] leading-5">
                切換
              </Text>
            </TouchableOpacity>
          </View>
          <View
            onLayout={(e) => {
              setCharsDisplayWidth(e.nativeEvent.layout.width);
            }}
            style={{ flexDirection: "row", gap: 8 }}
          >
            {/* 展櫃數據 */}
            {inGameCharacters?.map((char: any) => (
              <CharCard
                key={char.id}
                {...char}
                name={
                  charsDisplayMode ? (
                    <View
                      style={{
                        flexDirection: "row",
                        alignItems: "center",
                        gap: 4,
                      }}
                    >
                      <View>
                        <Image
                          className="w-9 h-9"
                          // @ts-ignore
                          source={Lightcone?.[char.light_cone?.id]?.icon}
                        />
                      </View>
                      <View style={{ alignItems: "flex-end", gap: -4 }}>
                        <Text className="text-text text-[12px] font-[HY65] leading-5">
                          {char.light_cone?.level
                            ? `Lv.${char.light_cone?.level}`
                            : ""}
                        </Text>
                        <Text className="text-text text-[12px] font-[HY65] leading-5">
                          {char.light_cone?.rank
                            ? `${char.light_cone?.rank}階`
                            : "無裝備"}
                        </Text>
                      </View>
                    </View>
                  ) : (
                    char.name
                  )
                }
                onPress={() => {
                  // @ts-ignore
                  navigation.navigate("UserCharDetail", {
                    uuid: props.uuid,
                    charId: char.id,
                  });
                }}
              />
            ))}
          </View>
          <Divider />
          {/* hoyolab 數據 */}
          {isOwner || isShowInfo ? (
            <View style={{ alignItems: "center", gap: 16 }}>
              <View
                style={{
                  flexDirection: "row",
                  gap: 8,
                  flexWrap: "wrap",
                  justifyContent: "center",
                }}
              >
                {userCharacters
                  ?.slice(0, showMoreChars ? 50 : 8)
                  ?.map((char: any) => (
                    <CharCard
                      key={char.id}
                      {...char}
                      name={
                        charsDisplayMode ? (
                          <View
                            style={{
                              flexDirection: "row",
                              alignItems: "center",
                              gap: 4,
                            }}
                          >
                            <View>
                              <Image
                                className="w-9 h-9"
                                // @ts-ignore
                                source={Lightcone?.[char.light_cone?.id]?.icon}
                              />
                            </View>
                            <View style={{ alignItems: "flex-end", gap: -4 }}>
                              <Text className="text-text text-[12px] font-[HY65] leading-5">
                                {char.light_cone?.level
                                  ? `Lv.${char.light_cone?.level}`
                                  : " "}
                              </Text>
                              <Text className="text-text text-[12px] font-[HY65] leading-5">
                                {char.light_cone?.rank
                                  ? `${char.light_cone?.rank}階`
                                  : "無裝備 "}
                              </Text>
                            </View>
                          </View>
                        ) : (
                          char.name
                        )
                      }
                      onPress={() => {
                        // @ts-ignore
                        // navigation.navigate("UserCharDetail", {
                        //   uuid: props.uuid,
                        //   charId: char.id,
                        // });
                      }}
                    />
                  ))}
              </View>
              <View
                style={{
                  transform: [{ rotate: showMoreChars ? "180deg" : "0deg" }],
                }}
              >
                <MoreBtn
                  onPress={() => {
                    setShowMoreChars(!showMoreChars);
                  }}
                />
              </View>
            </View>
          ) : (
            <></>
          )}
        </View>
      </View>
      <ReactNativeModal
        useNativeDriverForBackdrop
        hasBackdrop={false}
        isVisible={openQuestionPopUp}
        statusBarTranslucent
        deviceHeight={Dimensions.get("screen").height}
      >
        <PopUpCard
          title="公開角色"
          content="為了保障安全性和用戶權益，我們通過用戶的設備（客戶端），而非透過伺服器來更新 hoyolab 角色數據。這意味著如果用戶一段時間不使用 Stargazer，可能會導致他們的角色數量、活躍天數、達成成就和忘卻之庭數據存在差異。請注意，這些數據僅供參考，不具有絕對準確性。"
          onClose={() => {
            setOpenQuestionPopUp(false);
          }}
        />
      </ReactNativeModal>
    </>
  );
}

const MoreBtn = ({ onPress }: { onPress: () => void }) => {
  return (
    <TouchableOpacity
      className="w-7 h-7"
      style={{ justifyContent: "center", alignItems: "center" }}
      activeOpacity={0.35}
      onPress={onPress}
    >
      <Image className="w-3 h-3" source={require("./icons/More.svg")} />
    </TouchableOpacity>
  );
};

const Divider = () => (
  <Image
    source={require("./icons/Divider.svg")}
    className="w-[340px] h-[13px] "
  />
);
