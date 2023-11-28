import React, { useEffect, useState } from "react";
import { View } from "react-native";

export default function PlayerLevelBar() {
  const [flag, setFlag] = useState<"LEFT" | "RIGHT">("LEFT");
  const [level, setLevel] = useState(260);

  useEffect(() => {
    const interval = setInterval(() => {
      setLevel((prevLevel) => {
        const newLevel = flag === "LEFT" ? prevLevel + 3 : prevLevel - 3;
        if (newLevel > 300) {
          setFlag("RIGHT");
        } else if (newLevel < 100) {
          setFlag("LEFT");
        }
        return newLevel;
      });
    }, 8);

    return () => clearInterval(interval); // 清除定時器
  }, [flag]);

  return (
    <View style={{ flexDirection: "row" }} className="h-1">
      <View style={{ width: level }} className="h-full bg-[#DBC291]" />
      <View className="flex-1 h-full bg-[#666]" />
    </View>
  );
}
