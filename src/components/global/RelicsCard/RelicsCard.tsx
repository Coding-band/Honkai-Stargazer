import {
  View,
  Text,
  TouchableOpacity,
  GestureResponderEvent,
  Dimensions,
} from "react-native";
import React from "react";
import { Shadow } from "react-native-shadow-2";
import { LinearGradient } from "expo-linear-gradient";
import { Image } from "expo-image";
import { ExpoImage } from "../../../types/image";
import CardBg from "../CardBg/CardBg";

type Props = {
  image?: ExpoImage;
  rare: number;
  name: string;
  set?: number;
  onPress?: (e: GestureResponderEvent) => void;
};

export default React.memo(function RelicsCard(props: Props) {
  //   const animation = useSpring({ from: { opacity: 0.25 }, to: { opacity: 1 } });
  const itemMaxWidth = 55 * 1.4;
  const itemPadding = 5;
  const dimension = Dimensions.get('window');
  const totalAvailableWidth = (dimension.width - 8 * 2);
  const itemInRow = Math.trunc(totalAvailableWidth / itemMaxWidth)
  const oneItemWidth = itemMaxWidth + (((totalAvailableWidth % itemMaxWidth) / itemInRow)) - itemPadding*2
  const oneItemWidthMax = oneItemWidth * 2 / 1.4
  //have issue in width = 84.40413356574761 ~ 84.78599512702031

  return (
    <TouchableOpacity activeOpacity={0.65} onPress={props.onPress} style={{ paddingLeft: itemPadding,paddingRight: itemPadding }}>
      {/* <Shadow distance={6} offset={[4, 4]} startColor="#00000025"> */}
      <View style={{ width: oneItemWidth }}>
        <CardBg rare={props.rare} width={oneItemWidthMax} />
        <View
          style={{
            borderRadius: 4,
            borderTopRightRadius: 10,
            overflow: "hidden",
            width: oneItemWidth,
            height: oneItemWidth,
            alignItems: "center",
            justifyContent: "center",
            paddingRight:5
          }}
        >
          {/* 圖片 */}
          <Image cachePolicy="none"
            transition={200}
            style={{ width: oneItemWidth / 1.4, height: oneItemWidth / 1.4 }}
            source={props.image}
          />
          {/* 套數 */}
          {props.set && (
            <View
              className="w-[18px] h-[18px] rounded-full absolute left-0.5 top-1 bg-[#00000040]"
              style={{ justifyContent: "center", alignItems: "center"}}
            >
              <Text className="text-text text-[12px] font-[HY65] leading-4">
                {props?.set}
              </Text>
            </View>
          )}
        </View>
        {/* </Shadow> */}
        <View
          className="pt-1"
          style={{ alignItems: "center" }}
        >
          <Text
            numberOfLines={2}
            className="text-text2 text-[12px] font-[HY65]"
            style={{
              width: oneItemWidth,
              flexWrap: "wrap", // 允许文本换行
              textAlign: "center", // 文本居中
              paddingRight:itemPadding
            }}
          >
            {props.name}
          </Text>
        </View>
      </View>
    </TouchableOpacity>
  );
});
