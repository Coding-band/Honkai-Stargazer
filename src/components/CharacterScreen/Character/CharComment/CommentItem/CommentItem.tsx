import {
  View,
  Text,
  TouchableNativeFeedback,
  TouchableOpacity,
  TouchableWithoutFeedback,
  Pressable,
} from "react-native";
import React, { useState, useEffect } from "react";
import useUser from "../../../../../firebase/hooks/User/useUser";
import useUserCharacters from "../../../../../firebase/hooks/UserCharacters/useUserCharacters";
import useCharId from "../../../../../context/CharacterData/hooks/useCharId";
import officalCharId from "../../../../../../map/character_offical_id_map";
import { findKey } from "lodash";
import CommentUserAvatar from "./CommentUserAvatar/CommentUserAvatar";
import useCopyToClipboard from "../../../../../hooks/useCopyToClipboard";
import { Vibration } from "react-native";
import TagContent from "../../../../global/TagContent/TagContent";
import { ThumbsUp } from "phosphor-react-native";
import useMyFirebaseUid from "../../../../../firebase/hooks/FirebaseUid/useMyFirebaseUid";
import db from "../../../../../firebase/db";
import firestore from "@react-native-firebase/firestore";
import { LOCALES } from "../../../../../../locales";
import useAppLanguage from "../../../../../language/AppLanguage/useAppLanguage";

export default function CommentItem({
  id,
  user_id,
  likes,
  content,
  input,
  setInput,
}: {
  id: string;
  user_id: string;
  content: string;
  likes: string[];
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
    // onLongPress={() => {
    //   handleCopy(content);
    //   Vibration.vibrate(10);
    // }}
    >
      <View className="px-6 py-3" style={{ flexDirection: "row", gap: 14 }}>
        {/* 頭像 */}
        <CommentUserAvatar user={user} />
        <View style={{ gap: 2, flex: 1 }}>
          <View style={{ flexDirection: "row", gap: 10, alignItems: "center" }}>
            {/* 用戶名 */}
            <TouchableWithoutFeedback onPress={handleCopyTag}>
              <Text className="text-text text-[16px] font-[HY65] leading-5">
                {username}
              </Text>
            </TouchableWithoutFeedback>
            {userHasChar && !userHasCharAndIsFullRank && <HasOwnedTag />}
            {userHasCharAndIsFullRank && <MaxRankTag />}
            <Like commentId={id} likeUsers={likes} />
          </View>
          <TagContent>{content}</TagContent>
        </View>
      </View>
    </TouchableNativeFeedback>
  );
}

const Like = ({
  commentId,
  likeUsers,
}: {
  commentId: string;
  likeUsers: string[];
}) => {
  const firebaseUID = useMyFirebaseUid();
  const charId = useCharId();
  const charOfficalId = findKey(officalCharId, (v) => v === charId)!;

  const [isLiked, setIsLiked] = useState(likeUsers?.includes(firebaseUID));
  useEffect(() => {
    setIsLiked(likeUsers?.includes(firebaseUID));
  }, [firebaseUID, likeUsers]);

  const handleOnLike = () => {
    setIsLiked(!isLiked);
  };

  useEffect(() => {
    if (isLiked) {
      db.CharacterComments(charOfficalId)
        .doc(commentId)
        .update({
          likes: firestore.FieldValue.arrayUnion(firebaseUID),
        });
    } else {
      db.CharacterComments(charOfficalId)
        .doc(commentId)
        .update({
          likes: firestore.FieldValue.arrayRemove(firebaseUID),
        });
    }
  }, [isLiked]);

  return (
    <Pressable
      className="absolute right-0"
      style={{
        flexDirection: "row",
        alignItems: "center",
        gap: 6,
      }}
    >
      <Text className="text-text font-[HY65] text-[14px]">
        {(likeUsers?.includes(firebaseUID)
          ? isLiked
            ? likeUsers?.length
            : likeUsers?.length - 1
          : isLiked
          ? likeUsers?.length + 1
          : likeUsers?.length) || ""}
      </Text>
      <TouchableOpacity onPress={handleOnLike} activeOpacity={0.65}>
        <ThumbsUp
          color="white"
          size={20}
          weight={isLiked ? "fill" : "regular"}
        />
      </TouchableOpacity>
    </Pressable>
  );
};

const HasOwnedTag = () => {
  const { language } = useAppLanguage();
  return (
    <View
      className="h-4 px-[5px] bg-[#F3F9FF] rounded-[34px]"
      style={{ justifyContent: "center" }}
    >
      <Text className="text-[#393A5C] text-[10px] font-[HY65]">
        {LOCALES[language].UserOwned}
      </Text>
    </View>
  );
};

const MaxRankTag = () => {
  const { language } = useAppLanguage();
  return (
    <View
      className="h-4 px-[5px] bg-[#FFE690] rounded-[34px]"
      style={{ justifyContent: "center" }}
    >
      <Text className="text-[#6C5710] text-[10px] font-[HY65]">
        {LOCALES[language].FullEidolon}
      </Text>
    </View>
  );
};
