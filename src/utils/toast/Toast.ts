import T from "react-native-root-toast";

export default function Toast(message: string, second: number = 2) {
  return T.show(message, {
    duration: second * 1000,
    position: T.positions.CENTER,
    shadow: true,
    animation: true,
    hideOnPress: true,
    delay: 0,
    opacity: 1,
    containerStyle: {
      paddingHorizontal: 10,
      paddingVertical: 8,
      backgroundColor: "#F3F9FF",
    },
    textStyle: {
      lineHeight: 20,
      fontFamily: "HY65",
      color: "#222",
    },
  });
}

/**20240118 UNSUITABLE TO EDIT */
//FunctionStillInDevelop
Toast.StillDevelopingToast = () => {
  return Toast("此功能仍在開發中 Still in Development");
};

Toast.CopyToClipboard = () => {
  return Toast("已複製 Copied");
};

