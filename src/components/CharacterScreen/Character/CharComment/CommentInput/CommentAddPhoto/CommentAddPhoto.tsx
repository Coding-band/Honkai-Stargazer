import { View, Text, TouchableOpacity } from "react-native";
import React, { useState } from "react";
import { Image } from "expo-image";
import * as ImagePicker from "expo-image-picker";

export default function CommentAddPhoto() {
  const [image, setImage] = useState(null);

  const pickImageAsync = async () => {
    let result = await ImagePicker.launchImageLibraryAsync({
      allowsEditing: true,
      quality: 1,
    });

    if (!result.canceled) {
      console.log(result);
    } else {
      alert("You did not select any image.");
    }
  };

  return (
    <TouchableOpacity
      onPress={pickImageAsync}
      className="w-8 h-8 absolute right-2 top-[7px]"
      activeOpacity={0.35}
    >
      <Image
        className="w-full h-full"
        source={require("./icons/AddBtn.svg")}
      ></Image>
    </TouchableOpacity>
  );
}
