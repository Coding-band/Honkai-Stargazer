import {
  View,
  Text,
  ScrollView,
  Dimensions,
  TouchableOpacity,
} from "react-native";
import React, { useEffect, useState } from "react";
import Listbox from "../global/Listbox/Listbox";
import Button from "../global/Button/Button";
import WallPaper from "../global/WallPaper/WallPaper";
import CharScoreLb from "./CharScoreLb/CharScoreLb";
import { LinearGradient } from "expo-linear-gradient";

export default function ScoreLbList() {
  const scoreOptions = [
    { id: "char", name: "角色" },
    // { id: "relic", name: "遺器" },
  ];
  const [selectedScoreOption, setSelectedScoreOption] = useState("char");
  const [selectedCharOption, setSelectedCharOption] = useState<any>();

  return (
    <View style={{ width: "100%" }} className="z-30">
      <WallPaper wallPaperId={Number(selectedCharOption?.id)} />
      <LinearGradient
        className="absolute w-full h-full"
        colors={["#00000080", "#00000020"]}
      />
      <LinearGradient
        className="w-full h-[600px] absolute bottom-0"
        colors={["#00000000", "#000000"]}
      />
      <View
        className="p-4 pb-0 pt-[127px]"
        style={{ height: Dimensions.get("screen").height }}
      >
        {/* 頂欄 */}
        <View className="w-full mb-4 z-40" style={{ alignItems: "center" }}>
          <Listbox
            top={8}
            button={
              <Button
                width={Dimensions.get("screen").width - 32}
                height={46}
                withArrow
              >
                <Text className="text-[16px] font-[HY65] text-[#222]">
                  {
                    scoreOptions.filter((v) => v.id === selectedScoreOption)[0]
                      ?.name
                  }
                </Text>
              </Button>
            }
            value={selectedScoreOption}
            onChange={(option) => {
              setSelectedScoreOption(option);
            }}
          >
            {scoreOptions?.map((option) => (
              <Listbox.Item key={option.id} value={option.id}>
                {/* @ts-ignore */}
                {option?.name}
              </Listbox.Item>
            )) || []}
          </Listbox>
        </View>
        {/* 角色 */}
        <CharScoreLb
          onChange={(v) => {
            setSelectedCharOption(v);
          }}
        />
      </View>
    </View>
  );
}
