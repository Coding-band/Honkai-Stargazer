import { Keyboard, Pressable, TextInput, View } from "react-native";
import React, { useState, useEffect } from "react";
import { cn } from "../../../../../utils/css/cn";
import db from "../../../../../firebase/db";
import firestore from "@react-native-firebase/firestore";
import useCharId from "../../../../../context/CharacterData/hooks/useCharId";
import { findKey } from "lodash";
import officalCharId from "../../../../../../map/character_offical_id_map";
import useCharComments from "../../../../../firebase/hooks/CharComments/useCharComments";
import BlurView from "../../../../global/BlurView/BlurView";
import useMyFirebaseUid from "../../../../../firebase/hooks/FirebaseUid/useMyFirebaseUid";
import Toast from "../../../../../utils/toast/Toast";
import {
  extractMentionsMatch,
  extractMentionsSplit,
} from "../../../../../utils/extractMetions";
import pushExpoNoti from "../../../../../notifications/utils/pushExpoNoti";
import useHsrPlayerName from "../../../../../hooks/hoyolab/useHsrPlayerName";
import useCharData from "../../../../../context/CharacterData/hooks/useCharData";
import { pushExpoNotiType } from "../../../../../notifications/constant/pushExpoNotiType";
import CommentToolBox from "./CommentAddPhoto/CommentAddPhoto";
import useAddCharComment from "../../../../../firebase/hooks/CharComments/useAddCharComment";
import { customAlphabet } from "nanoid/non-secure";
import useAddUserComment from "../../../../../firebase/hooks/UserComments/useAddUserComment";
import useAppLanguage from "../../../../../language/AppLanguage/useAppLanguage";
import { LOCALES } from "../../../../../../locales";

export default function CommentInput({
  value,
  onChange,
}: {
  value: string;
  onChange: (value: string) => void;
}) {
  const { uid } = useMyFirebaseUid();
  const playerName = useHsrPlayerName();
  const charId = useCharId();
  const charName = useCharData().charFullData.name;
  const officalId = findKey(officalCharId, (v) => v === charId);

  const [input, setInput] = useState(value || "");

  const { language } = useAppLanguage();

  useEffect(() => {
    setInput(value);
  }, [value]);

  useEffect(() => {
    onChange(input);
  }, [input]);

  const { refetch: getComments } = useCharComments(officalId || "");
  const { mutateAsync: addCharComments } = useAddCharComment(officalId || "");
  const { mutateAsync: addUserComments } = useAddUserComment();

  const handleTextChange = (text: string) => {
    setInput(text);
  };

  const handleSubmit = async () => {
    // 輸入超過 1000 字
    if (input.length > 200) {
      Toast(LOCALES[language].CommentOverLimit);
      return;
    }

    // 輸入空訊息
    if (!input || input.trim() === "") {
      Toast(LOCALES[language].CommentPlsEnterComment);
      return;
    }

    // 沒登入
    if (!uid) {
      Toast(LOCALES[language].CommentHaventLogin);
      return;
    }

    try {
      const commentId = customAlphabet(
        "abcdefghijklmnopqrstuvwxyzABCDEFGHIGKLMNOPQUSTUVWXYZ0123456789"
      )();

      await Promise.all([
        addCharComments({
          id: commentId,
          content: input,
        }),
        addUserComments({
          id: commentId,
          type: "character-comment",
          title: officalId || "",
          content: input,
        }),
      ]);

      Toast(LOCALES[language].CommentSuccessful);
      setInput("");
      Keyboard.dismiss();

      // 更新評論區
      getComments();

      // 提及用戶 (推送通知)
      const extractMetionInput = extractMentionsSplit(input);
      if (extractMetionInput?.length) {
        extractMetionInput.map(async (mentionUser: string) => {
          const mentionUserName = mentionUser.slice(1);
          const querySnapshot = await db.Users.where(
            "name",
            "==",
            mentionUserName
          ).get();
          querySnapshot.forEach(async (doc) => {
            const uid = doc.id;
            const expoPushToken = (await db.UserTokens.doc(uid).get()).data()
              ?.expo_push_token;
            pushExpoNoti({
              to: expoPushToken,
              title: LOCALES[language].TrailblazerNoti,
              body: LOCALES[language].TrailblazerNotiTaggedU.replace(
                "${playerName}",
                playerName
              ).replace("${1}", charName),
              data: {
                type: pushExpoNotiType.sendCharacterComment,
                charId,
              },
            });
          });
        });
      }
    } catch (e: any) {
      Toast(LOCALES[language].CommentFailed + e.message);
    }
  };

  return (
    <View className="w-full px-6" style={{ justifyContent: "center" }}>
      <View className="rounded-[23px] overflow-hidden">
        <BlurView
          tint="dark"
          intensity={40}
          className="bg-[#FFFFFF20]"
          style={{ flexDirection: "row" }}
        >
          {/* 輸入框 */}
          <TextInput
            multiline
            className={cn(
              "py-[9px] px-[20px]",
              "text-[16px] font-[HY65] text-text leading-5"
            )}
            style={{ justifyContent: "center", flex: 1 }}
            value={input}
            onChangeText={handleTextChange}
            onSubmitEditing={handleSubmit}
            placeholder={LOCALES[language].CommentTextInputHint}
            placeholderTextColor="#F3F9FF40"
          />
          {/* 新增圖片 */}
          <CommentToolBox onHandleSend={handleSubmit} />
        </BlurView>
      </View>
    </View>
  );
}
