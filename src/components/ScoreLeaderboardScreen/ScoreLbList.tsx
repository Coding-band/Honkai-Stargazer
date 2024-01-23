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
import useDelayLoad from "../../hooks/useDelayLoad";
import Loading from "../global/Loading/Loading";
import useWallPaper from "../../redux/wallPaper/useWallPaper";
import useAppLanguage from "../../language/AppLanguage/useAppLanguage";
import { LOCALES } from "../../../locales";
import RelicScoreLb from "./RelicScoreLb/RelicScoreLb";
import useLocalState from "../../hooks/useLocalState";
import { map } from "lodash";
import { getCharFullData } from "../../utils/data/getDataFromMap";
import useTextLanguage from "../../language/TextLanguage/useTextLanguage";
import officalCharId from "../../../map/character_offical_id_map";

export default function ScoreLbList() {
  const loaded = useDelayLoad(1000);
  const { language: appLanguage } = useAppLanguage();
  const { language: textLanguage } = useTextLanguage();
  const { wallPaper } = useWallPaper();

  const scoreOptions = [
    { id: "char", name: LOCALES[appLanguage].Character },
    { id: "relic", name: LOCALES[appLanguage].Relic },
  ];
  const [selectedScoreOption, setSelectedScoreOption] = useState("char");

  // 所有角色選項
  const charOptions: { id: string; name: string }[] = map(
    officalCharId,
    (v, k) => ({
      id: k,
      name: getCharFullData(v, textLanguage).name,
    })
  );
  const [selectedCharOption, setSelectedCharOption] = useLocalState<any>(
    "char-score-leaderboard-selected-char",
    charOptions[0]
  );

  return (
    <View style={{ width: "100%" }} className="z-30">
      <WallPaper
        isBlur
        wallPaperId={loaded ? Number(selectedCharOption?.id) : wallPaper?.id}
      />
      <LinearGradient
        className="absolute w-full h-full"
        colors={["#00000080", "#00000020"]}
      />
      <LinearGradient
        className="w-full h-[600px] absolute bottom-0"
        colors={["#00000000", "#000000"]}
      />
      {/* 排行榜主體 */}
      <View
        className="p-4 pb-0 pt-[127px]"
        style={{
          height: Dimensions.get("screen").height,
          opacity: loaded ? 1 : 0,
        }}
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
        {/* 角色排行榜 */}
        <View
          style={{ display: selectedScoreOption === "char" ? "flex" : "none" }}
        >
          <CharScoreLb
            selectedCharOption={selectedCharOption}
            onChange={(v) => {
              setSelectedCharOption(v);
            }}
          />
        </View>
        {/* 遺器排行榜 */}
        <View
          style={{ display: selectedScoreOption === "relic" ? "flex" : "none" }}
        >
          <RelicScoreLb
            selectedCharOption={selectedCharOption}
            onChange={(v) => {
              setSelectedCharOption(v);
            }}
          />
        </View>
      </View>
      {/* 載入中 */}
      <View
        className="absolute"
        style={{ opacity: loaded ? 0 : 1 }}
        pointerEvents="none"
      >
        <Loading />
      </View>
    </View>
  );
}
