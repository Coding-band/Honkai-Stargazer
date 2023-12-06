import { View } from "react-native";
import React, { memo, useContext, useEffect } from "react";
import FixedContext from "./FixedContext";
import { useNavigation, useRoute } from "@react-navigation/native";
import CharacterContext from "../../../context/CharacterContext";

export default memo(function Fixed() {
  const navigation = useNavigation();
  const currentRoute =
    navigation.getState().routes[navigation.getState().routes.length - 1];
  const route = useRoute();

  const { content, setFixed } = useContext(FixedContext)!;

  useEffect(() => {
    return () => {
      setFixed(null);
    };
  }, []);

  return (
    route.key === currentRoute.key && (
      <View
        className="absolute left-0 bottom-0 right-0 z-[9999]"
        style={{ justifyContent: "center", alignItems: "center" }}
      >
        {content}
      </View>
    )
  );
});
