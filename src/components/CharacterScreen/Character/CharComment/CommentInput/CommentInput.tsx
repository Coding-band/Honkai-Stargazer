import { TextInput, View } from "react-native";
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
import { extractMentionsMatch } from "./utils/extractMetions";
import pushExpoNoti from "../../../../../notifications/utils/pushExpoNoti";
import useHsrPlayerName from "../../../../../hooks/hoyolab/useHsrPlayerName";
import useCharData from "../../../../../context/CharacterData/hooks/useCharData";
import { pushExpoNotiType } from "../../../../../notifications/constant/pushExpoNotiType";
import CommentToolBox from "./CommentAddPhoto/CommentAddPhoto";

export default function CommentInput({
  value,
  onChange,
}: {
  value: string;
  onChange: (value: string) => void;
}) {
  const uid = useMyFirebaseUid();
  const playerName = useHsrPlayerName();
  const charId = useCharId();
  const charName = useCharData().charFullData.name;
  const officalId = findKey(officalCharId, (v) => v === charId);

  const [input, setInput] = useState(value || "");

  useEffect(() => {
    setInput(value);
  }, [value]);

  useEffect(() => {
    onChange(input);
  }, [input]);

  const [extractMetionInput, setExtractMetionInput] = useState<string[]>([]);
  const { refetch } = useCharComments(officalId || "");

  const handleTextChange = (text: string) => {
    setInput(text);
    setExtractMetionInput(extractMentionsMatch(text));
  };

  const handleSubmit = async () => {
    if (!input) {
      Toast("請輸入訊息！");
      return;
    }

    if (uid) {
      const isDocExists = (await db.CharacterComments.doc(officalId).get())
        .exists;

      try {
        if (isDocExists) {
          await db.CharacterComments.doc(officalId).update({
            comments: firestore.FieldValue.arrayUnion({
              user_id: uid,
              content: input,
              mentions: extractMetionInput,
              createdAt: firestore.Timestamp.now(),
            }),
          });
        } else {
          await db.CharacterComments.doc(officalId).set({
            comments: firestore.FieldValue.arrayUnion({
              user_id: uid,
              content: input,
              mentions: extractMetionInput,
              createdAt: firestore.Timestamp.now(),
            }),
          });
        }
        setTimeout(() => {
          Toast("留言成功！");
          refetch();
        }, 500);

        // tag users (push notification)
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
                title: `開拓者快報！`,
                body: `玩家 ${playerName} 在【${charName}討論串】提及了您！`,
                data: {
                  type: pushExpoNotiType.sendCharacterComment,
                  charId,
                },
              });
            });
          });
        }
      } catch (e: any) {
        Toast("留言失敗，錯誤訊息：" + e.message);
      }
    } else {
      Toast("您尚未登入！");
    }
    setInput("");
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
            // multiline
            className={cn(
              "py-[9px] px-[20px]",
              "text-[16px] font-[HY65] text-text"
            )}
            style={{ justifyContent: "center", flex: 1 }}
            value={input}
            onChangeText={handleTextChange}
            onSubmitEditing={handleSubmit}
            placeholder="幫幫我 史瓦羅先生！"
            placeholderTextColor="#F3F9FF40"
          />
          {/* 新增圖片 */}
          {/* <CommentToolBox onHandleSend={handleSubmit} /> */}
        </BlurView>
      </View>
    </View>
  );
}
