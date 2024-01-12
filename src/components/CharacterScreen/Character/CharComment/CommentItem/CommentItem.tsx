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
import { extractMentionsSplit } from "../CommentInput/utils/extractMetions";
import { hasUserByUsername } from "../../../../../firebase/utils/hasUser";
import CommentUserAvatar from "./CommentUserAvatar/CommentUserAvatar";
import useCopyToClipboard from "../../../../../hooks/useCopyToClipboard";
import { Vibration } from "react-native";

export default function CommentItem({
  user_id,
  content,
  mentions,
  input,
  setInput,
}: {
  user_id: string;
  content: string;
  mentions: string[];
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

  const [processedContent, setProcessedContent] = useState<any[]>([]);
  useEffect(() => {
    async function processContent() {
      const parts = extractMentionsSplit(content);
      const processedParts = await Promise.all(
        parts.map(async (part, i) => {
          if (
            mentions?.includes(part) &&
            (await hasUserByUsername(part.slice(1)))
          ) {
            return (
              <Text key={i} className="text-[#DD8200]">
                {part}
              </Text>
            );
          } else {
            return <Text key={i}>{part}</Text>;
          }
        })
      );

      setProcessedContent(processedParts);
    }

    processContent();
  }, [content]); // 依赖项列表，当这些依赖项更改时，useEffect 会重新执行

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
              <Text className="text-text text-[16px] font-[HY65]">
                {username}
              </Text>
            </TouchableWithoutFeedback>
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
          <Text className="text-text2 text-[14px] font-[HY65]">
            {processedContent}
          </Text>
        </View>
      </View>
    </TouchableNativeFeedback>
  );
}
