import { View, Text, TouchableOpacity } from "react-native";
import React, { useEffect, useState } from "react";
import { animated, useSpring } from "@react-spring/native";
import BlurView from "../../../global/BlurView/BlurView";

export default function TopTabs(props: {
  tabs: { name: string; value: string }[];
  active: string;
  onChange?: (active: string) => void;
}) {
  const [active, setActive] = useState(props.active);
  useEffect(() => {
    props.onChange && props.onChange(active);
  }, [active]);

  const [offsets, setOffsets] = useState<number[]>([]);
  const [widths, setWidths] = useState<number[]>([]);

  const [width, setWidth] = useState(0);
  const [height, setHeight] = useState(0);
  const [offset, setOffset] = useState(0);

  const animationConatiner = useSpring({ left: offsets[offset] });
  const animation = useSpring({ width: widths[width] });

  return (
    <View style={{ flexDirection: "row", gap: 8 }}>
      <AnimatedView
        className="rounded-[20px] overflow-hidden absolute"
        style={animationConatiner}
      >
        {widths.length ? (
          <AnimatedBlurView
            intensity={35}
            style={[{ height }, animation]}
            className="py-[7px] px-[14px] bg-[#FFFFFF20]"
          />
        ) : (
          <></>
        )}
      </AnimatedView>
      {props.tabs.map((tab, i) => (
        <TouchableOpacity
          activeOpacity={1}
          onPress={() => {
            setActive(tab.value);
            setOffset(i);
            setWidth(i);
          }}
          onLayout={(event) => {
            const { width, height, x } = event.nativeEvent.layout;
            if (active === tab.value) {
              setHeight(height);
            }

            setWidths((widths) => [...widths, width].sort((b, a) => a - b));
            setOffsets((offsets) => [...offsets, x].sort());
          }}
        >
          <View className="py-[7px] px-[14px] rounded-[20px]">
            <Text className="text-text font-[HY65] text-[16px] leading-5">
              {tab.name}
            </Text>
          </View>
        </TouchableOpacity>
      ))}
    </View>
  );
}

const AnimatedView = animated(View);
const AnimatedBlurView = animated(BlurView);
