import { View, Text } from "react-native";
import React, { useEffect, useState } from "react";
import db from "../../../../../firebase/db";
import Users from "../../../../../firebase/models/Users";
import { Image } from "expo-image";

export default function CommentItem({
  user_id,
  content,
}: {
  user_id: string;
  content: string;
}) {
  const [username, setUsername] = useState("");
  const [userAvatar, setUserAvatar] = useState("");

  useEffect(() => {
    db.Users.doc(user_id)
      .get()
      .then((data) => {
        const user = data.data() as Users;
        setUsername(user.name);
        setUserAvatar(user.avatar_url);
      });
  }, [user_id]);

  return (
    <View style={{ flexDirection: "row", gap: 14 }}>
      <Image
        className="w-9 h-9"
        source={
          userAvatar ||
          "https://act.hoyoverse.com/darkmatter/hkrpg/prod_gf_cn/item_icon_763646/c86d9128cff46891e47275f3b48b5eeb.png?x-oss-process=image%2Fformat%2Cwebp"
        }
      />
      <View style={{ gap: 2, flex: 1 }}>
        <Text className="text-text text-[16px] font-[HY65]">{username}</Text>
        <Text className="text-text2 text-[14px] font-[HY65]">{content}</Text>
      </View>
    </View>
  );
}
