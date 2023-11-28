import React, { useEffect, useState } from "react";
import {
  Dimensions,
  FlatList,
  LayoutChangeEvent,
  SafeAreaView,
  ScrollView,
  Text,
  View,
} from "react-native";
import { cn } from "../../../utils/cn";
import { LinearGradient } from "expo-linear-gradient";
import { Image } from "expo-image";
import { Shadow } from "react-native-shadow-2";
import CharCard from "./CharCard/CharCard";
import { map } from "lodash";

const testImage1 = require("../../../../assets/images/test-charlist-img-1.png");
const testImage2 = require("../../../../assets/images/test-charlist-img-2.png");
const testImage3 = require("../../../../assets/images/test-charlist-img-3.png");
const testImage4 = require("../../../../assets/images/test-charlist-img-4.png");

const DATA_SET = [
  { image: testImage1, star: 5 },
  { image: testImage2, star: 4 },
  { image: testImage3, star: 5 },
  { image: testImage4, star: 5 },
  // 可以添加更多的元素
];

export default function CharList() {
  const [DATA, SetDATA] = useState<any>([]);

  useEffect(() => {
    // if (DATA.length < 60) {
    //   const i = setInterval(() => {
    //     SetDATA([
    //       ...DATA,
    //       ...[
    //         [DATA_SET[Math.floor(Math.random() * DATA_SET.length)]],
    //         [
    //           DATA_SET[Math.floor(Math.random() * DATA_SET.length)],
    //           DATA_SET[Math.floor(Math.random() * DATA_SET.length)],
    //         ],
    //         [
    //           DATA_SET[Math.floor(Math.random() * DATA_SET.length)],
    //           DATA_SET[Math.floor(Math.random() * DATA_SET.length)],
    //           DATA_SET[Math.floor(Math.random() * DATA_SET.length)],
    //           DATA_SET[Math.floor(Math.random() * DATA_SET.length)],
    //         ],
    //       ][Math.floor(Math.random() * 3)],
    //     ]);
    //   }, 80);
    //   return () => {
    //     clearInterval(i);
    //   };
    // }

    setTimeout(() => {
      SetDATA([
        DATA_SET[Math.floor(Math.random() * DATA_SET.length)],
        DATA_SET[Math.floor(Math.random() * DATA_SET.length)],
        DATA_SET[Math.floor(Math.random() * DATA_SET.length)],
        DATA_SET[Math.floor(Math.random() * DATA_SET.length)],
        DATA_SET[Math.floor(Math.random() * DATA_SET.length)],
        DATA_SET[Math.floor(Math.random() * DATA_SET.length)],
        DATA_SET[Math.floor(Math.random() * DATA_SET.length)],
        DATA_SET[Math.floor(Math.random() * DATA_SET.length)],
        DATA_SET[Math.floor(Math.random() * DATA_SET.length)],
        DATA_SET[Math.floor(Math.random() * DATA_SET.length)],
        DATA_SET[Math.floor(Math.random() * DATA_SET.length)],
        DATA_SET[Math.floor(Math.random() * DATA_SET.length)],
        DATA_SET[Math.floor(Math.random() * DATA_SET.length)],
        DATA_SET[Math.floor(Math.random() * DATA_SET.length)],
        DATA_SET[Math.floor(Math.random() * DATA_SET.length)],
        DATA_SET[Math.floor(Math.random() * DATA_SET.length)],
        DATA_SET[Math.floor(Math.random() * DATA_SET.length)],
        DATA_SET[Math.floor(Math.random() * DATA_SET.length)],
        DATA_SET[Math.floor(Math.random() * DATA_SET.length)],
        DATA_SET[Math.floor(Math.random() * DATA_SET.length)],
        DATA_SET[Math.floor(Math.random() * DATA_SET.length)],
        DATA_SET[Math.floor(Math.random() * DATA_SET.length)],
        DATA_SET[Math.floor(Math.random() * DATA_SET.length)],
        DATA_SET[Math.floor(Math.random() * DATA_SET.length)],
        DATA_SET[Math.floor(Math.random() * DATA_SET.length)],
        DATA_SET[Math.floor(Math.random() * DATA_SET.length)],
        DATA_SET[Math.floor(Math.random() * DATA_SET.length)],
        DATA_SET[Math.floor(Math.random() * DATA_SET.length)],
        DATA_SET[Math.floor(Math.random() * DATA_SET.length)],
        DATA_SET[Math.floor(Math.random() * DATA_SET.length)],
        DATA_SET[Math.floor(Math.random() * DATA_SET.length)],
        DATA_SET[Math.floor(Math.random() * DATA_SET.length)],
      ]);
    });
  }, []);

  return (
    <View style={{ width: "100%" }} className="p-[17px]">
      <ScrollView>
        <View
          style={{
            paddingVertical: 110,
            flexDirection: "row",
            flexWrap: "wrap",
            gap: 12,
            justifyContent: "center",
          }}
        >
          {map(DATA, (item, i) => (
            <CharCard key={i} image={item.image} stars={item.star} />
          ))}
        </View>
      </ScrollView>
    </View>
  );
}
