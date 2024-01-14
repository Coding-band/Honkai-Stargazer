import {
  View,
  Text,
  TouchableNativeFeedback,
  TouchableOpacity,
  TouchableWithoutFeedback,
} from "react-native";
import React, { useEffect, useState } from "react";
import useUser from "../../../../../firebase/hooks/User/useUser";
import useUserCharacters from "../../../../../firebase/hooks/UserCharacters/useUserCharacters";
import useCharId from "../../../../../context/CharacterData/hooks/useCharId";
import officalCharId from "../../../../../../map/character_offical_id_map";
import { findKey } from "lodash";
import { extractMentionsSplit } from "../../../../../utils/extractMetions";
import { hasUserByUsername } from "../../../../../firebase/utils/hasUser";
import CommentUserAvatar from "./CommentUserAvatar/CommentUserAvatar";
import useCopyToClipboard from "../../../../../hooks/useCopyToClipboard";
import { Vibration } from "react-native";
import TagContent from "../../../../global/TagContent/TagContent";

export default function CommentItem({
  user_id,
  content,
  input,
  setInput,
}: {
  user_id: string;
  content: string;
  input: string;
  setInput: (v: string) => void;
}) {
  const { data: user } = useUser(user_id);
  const { data: userCharsInfo } = useUserCharacters(user_id);

  const charId = useCharId();
  const charOfficalId = findKey(officalCharId, (v) => v === charId);
  const username = user?.name;

  const userCharacters = userCharsInfo?.characters;
  const userHasChar = !!userCharacters?.filter(
    (char) => char.id.toString() === charOfficalId
  ).length;
  const userHasCharAndIsFullRank =
    userCharacters?.filter((char) => char.id.toString() === charOfficalId)[0]
      ?.rank === 6;

  const handleCopy = useCopyToClipboard();
  const handleCopyTag = () => {
    setInput(input + ` @${username}`.trim() + " ");
  };

  return (
    <TouchableNativeFeedback
      onLongPress={() => {
        handleCopy(content);
        Vibration.vibrate(50);
      }}
    >
      <View className="px-6 py-3" style={{ flexDirection: "row", gap: 14 }}>
        <CommentUserAvatar user={user} />
        <View style={{ gap: 2, flex: 1 }}>
          <View style={{ flexDirection: "row", gap: 10, alignItems: "center" }}>
            <TouchableWithoutFeedback onPress={handleCopyTag}>
              <Text className="text-text text-[16px] font-[HY65] leading-5">
                {username}
              </Text>
            </TouchableWithoutFeedback>
            {userHasChar && !userHasCharAndIsFullRank && (
              <View
                className="h-4 px-[5px] bg-[#F3F9FF] rounded-[34px]"
                style={{ justifyContent: "center" }}
              >
                <Text className="text-[#393A5C] text-[10px] font-[HY65]">
                  {"已擁有"}
                </Text>
              </View>
            )}
            {userHasCharAndIsFullRank && (
              <View
                className="h-4 px-[5px] bg-[#FFE690] rounded-[34px]"
                style={{ justifyContent: "center" }}
              >
                <Text className="text-[#6C5710] text-[10px] font-[HY65]">
                  {"滿命"}
                </Text>
              </View>
            )}
          </View>
          <TagContent>{content}</TagContent>
        </View>
      </View>
    </TouchableNativeFeedback>
  );
}
