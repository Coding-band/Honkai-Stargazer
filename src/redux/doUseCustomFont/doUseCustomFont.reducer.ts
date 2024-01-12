import { DoUseCustomFontAction } from "./doUseCustomFont.types";

export const doUseCustomFont = (
  prevSate: boolean = false,
  action: DoUseCustomFontAction
) => {
  let newState = prevSate;
  if (action.type === "set_do_use_custom_font") {
    newState = action.payload;
    return newState;
  } else {
    return prevSate;
  }
};
