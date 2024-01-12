import { StyleSheet } from "react-native";

export const globalStyles = StyleSheet.create({
  textShadow: {
    textShadowOffset: { width: 0, height: 2 },
    textShadowColor: "#00000025",
    textShadowRadius: 4,
  },
  rJCenterFWrap: {
    flexDirection: "row",
    justifyContent: "center",
    flexWrap: "wrap",
  },
  rJCenter: {
    flexDirection: "row",
    justifyContent: "center",
  },
  rACenterFWrap: {
    flexDirection: "row",
    alignItems: "center",
    flexWrap: "wrap",
  },
  rACenter: {
    flexDirection: "row",
    alignItems: "center",
  },
  cJCenterFWrap: {
    justifyContent: "center",
    flexWrap: "wrap",
  },
  cJCenter: {
    justifyContent: "center",
  },
  cACenterFWrap: {
    alignItems: "center",
    flexWrap: "wrap",
  },
  cACenter: {
    alignItems: "center",
  },
});
