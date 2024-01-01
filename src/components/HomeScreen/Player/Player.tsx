import { View, Text, StyleSheet } from "react-native";
import React from "react";
import PlayerAction from "./PlayerAction/PlayerAction";
import PlayerLevelBar from "./PlayerLevelBar/PlayerLevelBar";
import UUID from "./UUID/UUID";
import PlayerAvator from "./PlayerAvator/PlayerAvator";
import { cn } from "../../../utils/css/cn";
import PlayerCharacter from "./PlayerCharacter/PlayerCharacter";
import useHsrPlayerData from "../../../hooks/hoyolab/useHsrPlayerData";
import useAppLanguage from "../../../context/AppLanguage/useAppLanguage";
import { LOCALES } from "../../../../locales";

export default function Player() {
  const playerData = useHsrPlayerData();
  const playerNickName = playerData?.nickname;
  const playerLevel = playerData?.level;
  const {language} = useAppLanguage();

  return (
    <View
      className={cn("w-full pt-8 px-4 z-50")}
      style={styles.playerContainer}
    >
      {/* uuid */}
      <UUID />
      {/* player details */}
      <View className="w-full" style={styles.playerDetails}>
        <View className="z-50" style={styles.playerTopRow}>
          <View style={styles.playerAvatarRow}>
            <PlayerAvator />
            <View>
              <Text className="text-white text-xl font-medium font-[HY65] mb-1.5">
                {playerNickName || "開拓者"}
              </Text>
              <PlayerCharacter />
            </View>
          </View>
          <View style={styles.playerActionColumn}>
            <PlayerAction />
            <View>
              <Text className="font-[HY75] text-[#DBC291] text-[14px] font-medium">
                {LOCALES[language].PlayerLevel} {playerLevel || "?"}
              </Text>
            </View>
          </View>
        </View>
        <PlayerLevelBar />
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  playerContainer: {
    gap: 12,
    alignItems: "flex-start",
  },
  playerDetails: {
    flexDirection: "column",
    gap: 12,
  },
  playerTopRow: {
    justifyContent: "space-between",
    flexDirection: "row",
    alignItems: "flex-end",
  },
  playerAvatarRow: {
    flexDirection: "row",
  },
  playerActionColumn: {
    flexDirection: "column",
    gap: 20,
    alignItems: "flex-end",
  },
});
