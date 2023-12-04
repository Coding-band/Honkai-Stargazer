import { View } from "react-native";
import React, { useContext, useEffect } from "react";
import FixedContext from "./FixedContext";

export default function Fixed() {
  const { content, setFixed } = useContext(FixedContext)!;
  useEffect(() => {
    return () => {
      setFixed(null);
    };
  },[]);
  return (
    <View
      className="absolute left-0 bottom-0 right-0 z-[9999]"
      style={{ justifyContent: "center", alignItems: "center" }}
    >
      {content}
    </View>
  );
}
