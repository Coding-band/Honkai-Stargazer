import {
  View,
  Text,
  TouchableOpacity,
  TouchableNativeFeedback,
} from "react-native";
import React, { useEffect, useState } from "react";
import db from "../../../../../firebase/db";
import Users from "../../../../../firebase/models/Users";
import { Image } from "expo-image";
import useUser from "../../../../../firebase/hooks/useUser";
import useUserCharacters from "../../../../../firebase/hooks/useUserCharacters";
import useCharId from "../../../../../context/CharacterData/hooks/useCharId";
import officalCharId from "../../../../../../map/character_offical_id_map";
import { findKey } from "lodash";

export default function CommentItem({
  user_id,
  content,
}: {
  user_id: string;
  content: string;
}) {
  const charId = useCharId();
  const charOfficalId = findKey(officalCharId, (v) => v === charId);

  const { data: user } = useUser(user_id);
  const username = user?.name;
  const userAvatar = user?.avatar_url;
  const { data: userCharsInfo } = useUserCharacters(user_id);
  const userCharacters = userCharsInfo?.characters;
  const userHasChar = !!userCharacters?.filter(
    (char) => char.id.toString() === charOfficalId
  ).length;

  return (
    <TouchableNativeFeedback>
      <View className="py-3" style={{ flexDirection: "row", gap: 14 }}>
        <Image
          className="w-9 h-9"
          source={
            userAvatar ||
            "https://act.hoyoverse.com/darkmatter/hkrpg/prod_gf_cn/item_icon_763646/c86d9128cff46891e47275f3b48b5eeb.png?x-oss-process=image%2Fformat%2Cwebp"
          }
        />
        <View style={{ gap: 2, flex: 1 }}>
          <View style={{ flexDirection: "row", gap: 10, alignItems: "center" }}>
            <Text className="text-text text-[16px] font-[HY65]">
              {username}
            </Text>
            {userHasChar && (
              <View
                className="h-4 px-[5px] bg-[#F3F9FF] rounded-[34px]"
                style={{ justifyContent: "center" }}
              >
                <Text className="text-[#393A5C] text-[10px] font-[HY65]">
                  {"已擁有"}
                </Text>
              </View>
            )}
          </View>
          <Text className="text-text2 text-[14px] font-[HY65]">{content}</Text>
        </View>
      </View>
    </TouchableNativeFeedback>
  );
}
