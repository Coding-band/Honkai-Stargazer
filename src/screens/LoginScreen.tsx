import { View } from "react-native";
import React from "react";
import { StatusBar } from "expo-status-bar";
import { ImageBackground } from "expo-image";
import { WebView } from "react-native-webview";


export default function LoginScreen() {
  // const [textInput, setTextInput] = useState("");
  return (
    <View style={{ flex: 1, backgroundColor: "white" }}>
      <StatusBar style="dark" />
      <ImageBackground
        className="absolute w-full h-full"
        // 把背景關掉
        source={require("../../assets/images/test-bg.png")}
        // placeholder={blurhash}
        contentFit="cover"
        blurRadius={10}
      />
      <WebView
        style={{ flex: 1 }}
        source={{
          uri: "https://act.hoyolab.com/app/community-game-records-sea/index.html",
        }}
      />
      {/* <View className="p-2" style={{ gap: 8 }}>
        <Text className="text-black text-[20px] font-[HY65]">
          輸入 Hoyolab Cookie
        </Text>
        <TextInput
          value={textInput}
          onChangeText={setTextInput}
          className="p-1 bg-white rounded-lg"
          placeholder="請輸入 Hoyolab Cookie by 椰子冰"
        />
      </View> */}
    </View>
  );
}
