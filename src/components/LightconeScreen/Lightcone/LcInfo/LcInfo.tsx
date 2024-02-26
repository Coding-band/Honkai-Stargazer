import { View, Text, Dimensions } from "react-native";
import React, { useState } from "react";
import LcStars from "../../../global/PageStars/PageStars";
import { Image } from "expo-image";
import Path from "../../../../../assets/images/images_map/path";
import useLcData from "../../../../context/LightconeData/hooks/useLcData";
import { globalStyles } from "../../../../../styles/global";

export default function LcInfo() {
  const { lcData } = useLcData();
  const [containerLayout, setContainerLayout] = useState<any>({ height: 72 });

  return (
    <View
      className="px-6"
      style={{
        paddingTop:
          Dimensions.get("screen").height - 156 - containerLayout.height,
        gap: 8,
      }}
    >
      <View
        onLayout={(e) => {
          setContainerLayout(e.nativeEvent.layout);
        }}
        style={{ gap: 12 }}
      >
        <Text
          className="text-[32px] font-[HY65] text-white leading-9"
          style={globalStyles.textShadow}
        >
          {lcData?.name}
        </Text>
        <LcStars count={lcData?.rare || 5} />
      </View>
      <View
        style={{
          flexDirection: "row",
          justifyContent: "space-between",
          alignItems: "center",
        }}
      >
        <View style={{ flexDirection: "row", gap: 26 }}>
          <View style={{ flexDirection: "row", gap: 8 }}>
            <Image cachePolicy="none"
              // @ts-ignore
              source={Path[lcData?.pathId].icon}
              style={{ width: 24, height: 24 }}
            />
            <Text
              className="text-[16px] text-white font-[HY65] leading-5"
              style={globalStyles.textShadow}
            >
              {lcData?.path}
            </Text>
          </View>
        </View>
        <View>
          {/* <Text className="text-[16px] text-white font-[HY65]">
            {charData?.location}
          </Text> */}
        </View>
      </View>
    </View>
  );
}
