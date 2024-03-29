import {
  View,
  Text,
  TouchableOpacity,
  GestureResponderEvent,
  Dimensions,
} from "react-native";
import React from "react";
import { LinearGradient } from "expo-linear-gradient";
import { Image, ImageBackground } from "expo-image";
import { ExpoImage } from "../../../types/image";
import { CardColors } from "../../../constant/card";
import PathCardIcon from "../PathCardIcon/PathCardIcon";
import { Path } from "../../../types/path";
import CardBg from "../CardBg/CardBg";

type Props = {
  image?: ExpoImage;
  rare: number;
  name: string;
  path: Path;
  onPress?: (e: GestureResponderEvent) => void;
};

export default React.memo(function LightConeCard(props: Props) {
  const itemMaxWidth = 72;
  const itemPadding = 5;
  const dimension = Dimensions.get('window');
  const totalAvailableWidth = (dimension.width - 8 * 2);
  const itemInRow = Math.trunc(totalAvailableWidth / itemMaxWidth)
  const oneItemWidth = itemMaxWidth + (((totalAvailableWidth % itemMaxWidth) / itemInRow)) - itemPadding*2
  const oneItemWidthMax = oneItemWidth * 2 / 1.4
  return (
    <TouchableOpacity activeOpacity={0.65} onPress={props.onPress} style={{ paddingLeft: itemPadding,paddingRight: itemPadding }}>
      {/* <Shadow
        distance={20}
        offset={[2, 15]}
        startColor={props.rare === 5 ? "#C7A37150" : "#9663CC50"}
      > */}
      <View>
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
            {/* 光錐圖標 */}
            <Image cachePolicy="none"
              transition={200}
              style={{ width: oneItemWidth / 1.2, height: oneItemWidth / 1.2 }}
              source={props.image}
              contentFit="contain"
            />
            {/* 命途 & 元素 */}
            <View className="absolute top-1 left-0">
              <PathCardIcon value={props.path} />
            </View>
        </View>
        {/* </Shadow> */}
        <View
          className="pt-1"
          style={{ alignItems: "center"}}
        >
          <Text
            numberOfLines={2}
            className="text-text2 text-[12px] font-[HY65]"
            style={{
              width: oneItemWidth ,
              flexWrap: "wrap",
              textAlign: "center",
              paddingRight:6
            }}
          >
            {props.name}
          </Text>
        </View>
      </View>
    </TouchableOpacity>
  );
});