import { View, Text, TouchableOpacity } from "react-native";
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

type Props = {
  uuid: string;
};

export default function UserInfoCharacters(props: Props) {
  const { language: textLanguage } = useTextLanguage();
  const navigation = useNavigation();

  const hsrUUID = useHsrUUID();
  const profileUUID = props.uuid;

  const isOwner = profileUUID === hsrUUID;
  const isShowInfo = useUser().data?.show_info;

  const { data: hsrInGameInfo } = useHsrInGameInfo(props.uuid);
  const userCharList = useUserCharactersByUUID(profileUUID).data?.characters;

  // 展櫃資料
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
        id: char.id,
        rare: charJsonData.rare,
        name: charFullData.name,
        rank: char.rank,
        level: "Lv " + char.level,
        image: CharacterImage[charId].icon,
      };
    });

  const [showMoreChars, setShowMoreChars] = useState(false);

  return (
    <View style={{ alignItems: "center", gap: 10 }}>
      <TouchableOpacity
        activeOpacity={0.65}
        onPress={() => {
          Toast(
            "為了保障安全性和用戶權益，我們會通過用戶的設備（客戶端），而不是通過伺服器，來更新 hoyolab 角色數據。這意味著如果用戶一段時間不使用 Stargazer，可能會導致他們的角色數量、活躍天數、達成成就和忘卻之庭數據存在差異。請注意，這些數據僅供參考，不具有絕對準確性。",
            5
          );
        }}
      >
        <Text className="text-text text-[16px] font-[HY65]">
          擁有角色 {hsrInGameInfo?.player?.space_info?.avatar_count}
        </Text>
      </TouchableOpacity>
      {/* 展示櫃 */}
      <View
        style={{
          flexDirection: "row",
          gap: 8,
          flexWrap: "wrap",
          justifyContent: "center",
        }}
      >
        {inGameCharacters?.map((char: any) => (
          <CharCard
            key={char.id}
            {...char}
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
      <Image
        source={require("./icons/Divider.svg")}
        className="w-[355px] h-[13px]"
      />
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
                <CharCard key={char.id} {...char} />
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
