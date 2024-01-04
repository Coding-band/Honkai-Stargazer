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

export default function CommentInput() {
  const [input, setInput] = useState("");

  const hsrUUID = useHsrUUID();
  const charId = useCharId();
  const officalId = findKey(officalCharId, (v) => v === charId);

  const { refetch } = useCharComments(officalId || "");

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
            onSubmitEditing={async () => {
              if (hsrUUID) {
                try {
                  await db.CharacterComments.doc(officalId).update({
                    comments: firestore.FieldValue.arrayUnion({
                      user_id: hsrUUID,
                      content: input,
                    }),
                  });
                  refetch();
                } catch (error: any) {
                  if (error.code === "firestore/not-found") {
                    await db.CharacterComments.doc(officalId).set({
                      comments: firestore.FieldValue.arrayUnion({
                        user_id: hsrUUID,
                        content: input,
                      }),
                    });
                    refetch();
                  }
                }
              }
              setInput("");
            }}
            placeholder="幫幫我 史瓦羅先生！"
            placeholderTextColor="gray"
          />
        </BlurView>
      </View>
    </View>
  );
}
