import { View, Text, TouchableOpacity, Pressable } from "react-native";
import React, { useState } from "react";
import { Image } from "expo-image";
import * as ImagePicker from "expo-image-picker";

export default function CommentToolBox({
  onHandleSend,
}: {
  onHandleSend: () => void;
}) {
  // const [image, setImage] = useState(null);

  // const pickImageAsync = async () => {
  //   let result = await ImagePicker.launchImageLibraryAsync({
  //     allowsEditing: true,
  //     quality: 1,
  //   });

  //   if (!result.canceled) {
  //     console.log(result);
  //   } else {
  //     alert("You did not select any image.");
  //   }
  // };

  return (
    <View className="px-2 h-full" style={{ justifyContent: "center" }}>
      {/* Send Button */}
      <TouchableOpacity
        onPress={onHandleSend}
        className="w-8 h-8"
        activeOpacity={0.35}
      >
        <Image
          className="w-full h-full"
          source={require("./icons/SendBtn.svg")}
        ></Image>
      </TouchableOpacity>
    </View>
  );
}
