import {
  TextInput,
  KeyboardAvoidingView,
  View,
  Keyboard,
  Platform,
} from "react-native";
import React, { useState } from "react";
import { cn } from "../../../../../utils/css/cn";
import db from "../../../../../firebase/db";
import firestore from "@react-native-firebase/firestore";
import useHsrUUID from "../../../../../hooks/hoyolab/useHsrUUID";
import useCharId from "../../../../../context/CharacterData/hooks/useCharId";
import { findKey } from "lodash";
import officalCharId from "../../../../../../map/character_offical_id_map";
import useCharComments from "../../../../../firebase/hooks/useCharComments";
import { BlurView } from "expo-blur";
import useFirebaseUid from "../../../../../firebase/hooks/useFirebaseUid";
import Toast from "../../../../../utils/toast/Toast";

export default function CommentInput() {
  const uid = useFirebaseUid();
  const charId = useCharId();
  const officalId = findKey(officalCharId, (v) => v === charId);

  const [input, setInput] = useState("");
  const { refetch } = useCharComments(officalId || "");

  const handleSubmit = async () => {
    if (uid) {
      const isDocExists = (await db.CharacterComments.doc(officalId).get())
        .exists;

      try {
        if (isDocExists) {
          await db.CharacterComments.doc(officalId).update({
            comments: firestore.FieldValue.arrayUnion({
              user_id: uid,
              content: input,
            }),
          });
        } else {
          await db.CharacterComments.doc(officalId).set({
            comments: firestore.FieldValue.arrayUnion({
              user_id: uid,
              content: input,
            }),
          });
        }

        setTimeout(() => {
          Toast("留言成功！");
          refetch();
        }, 500);
      } catch (e) {
        Toast("留言失敗，錯誤訊息：" + e.message);
      }

      setInput("");
    }
  };

  return (
    <View className="w-full h-[80px] " style={{ justifyContent: "center" }}>
      <View className="rounded-[23px] overflow-hidden">
        <BlurView tint="dark" intensity={40}>
          <TextInput
            className={cn(
              "w-full h-[46px] px-[20px]",
              "bg-[#FFFFFF20]",
              "text-[16px] font-[HY65] text-text"
            )}
            style={{ justifyContent: "center" }}
            value={input}
            onChangeText={setInput}
            onSubmitEditing={handleSubmit}
            placeholder="幫幫我 史瓦羅先生！"
            placeholderTextColor="gray"
          />
        </BlurView>
      </View>
    </View>
  );
}
