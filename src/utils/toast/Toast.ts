import T from "react-native-root-toast";

export default function Toast(message: string) {
  return T.show(message, {
    duration: 2000,
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
      fontFamily: "HY65",
      color: "#222",
    },
  });
}

Toast.StillDevelopingToast = () => {
  return Toast("此功能仍在開發中");
};

Toast.CopyToClipboard = () => {
  return Toast("已複製到剪貼簿");
};
