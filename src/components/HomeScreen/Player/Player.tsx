import { View, Text } from "react-native";
import React from "react";
import PlayerAction from "./PlayerAction/PlayerAction";
import PlayerLevelBar from "./PlayerLevelBar/PlayerLevelBar";
import UUID from "../UUID/UUID";
import PlayerAvator from "./PlayerAvator/PlayerAvator";
import { cn } from "../../../utils/cn";
import PlayerCharacter from "./PlayerCharacter/PlayerCharacter";

export default function Player() {
  return (
    <View
      className={cn("w-full pt-8 px-4 z-50")}
      style={{ gap: 12, alignItems: "flex-start" }}
    >
      {/* uuid */}
      <UUID />
      {/* player details */}
      <View className="w-full" style={{ flexDirection: "column", gap: 12 }}>
        <View
        className="z-50"
          style={{
            justifyContent: "space-between",
            flexDirection: "row",
            alignItems: "flex-end",
          }}
        >
          <View style={{ flexDirection: "row" }}>
            <PlayerAvator />
            <View>
              <Text className="text-white text-xl font-medium mb-2">2O48</Text>
              <PlayerCharacter />
            </View>
          </View>
          <View
            style={{
              flexDirection: "column",
              gap: 20,
              alignItems: "flex-end",
            }}
          >
            <PlayerAction />
            <View>
              <Text
                style={{ fontFamily: "HY75" }}
                className="text-[#DBC291] text-[14px] font-medium"
              >
                开拓等级 58
              </Text>
            </View>
          </View>
        </View>
        <PlayerLevelBar />
      </View>
    </View>
  );
}
