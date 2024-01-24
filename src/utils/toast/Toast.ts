import T from "react-native-root-toast";
import { AppLanguage } from "../../language/language.types";
import { LOCALES } from "../../../locales";

export default function Toast(message: string, second: number = 2) {
  return T.show(message, {
    duration: second * 1000,
    position: T.positions.CENTER,
    shadow: true,
    animation: true,
    hideOnPress: true,
    delay: 0,
    opacity: 1,
    shadowColor: "gray",
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
Toast.StillDevelopingToast = (lang: AppLanguage = "en") => {
  return Toast(LOCALES[lang].FunctionStillInDevelop);
};

Toast.CopyToClipboard = (lang: AppLanguage = "zh_hk") => {
  return Toast(LOCALES[lang].CopyToClipBoard);
};

Toast.FailToCopy = (lang: AppLanguage = "zh_hk") => {
  return Toast(LOCALES[lang].FailToCopy);
};
