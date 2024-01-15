import { Platform } from "react-native";
import { DoUseBlurHomePageEffectAction } from "./doUseHomePageBlurEffect.types";

export const doUseHomePageBlurEffect = (
  prevSate: boolean = Platform.OS === "ios",
  action: DoUseBlurHomePageEffectAction
) => {
  let newState = prevSate;
  if (action.type === "set_do_use_home_page_blur_effect") {
    newState = action.payload;
    return newState;
  } else {
    return prevSate;
  }
};
