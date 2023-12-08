import React, { useEffect, useState } from "react";
import { View } from "react-native";
import useHoyolabGameRecord from "../../../../hooks/hoyolab/useHoyolabGameRecord";
import useHsrPlayerData from "../../../../hooks/hoyolab/useHsrPlayerData";

export default function PlayerLevelBar() {
  const playerData = useHsrPlayerData();
  const playerLevel = Number(playerData?.level);

  const [levelRate, setLevelRate] = useState<number>(0);
  useEffect(() => {
    setLevelRate(playerLevel / 70);
  }, [playerLevel]);

  return (
    <View style={{ flexDirection: "row" }} className="h-1">
      <View
        style={{ width: `${levelRate * 100}%` }}
        className="h-full bg-[#DBC291]"
      />
      <View className="flex-1 h-full bg-[#666]" />
    </View>
  );
}
