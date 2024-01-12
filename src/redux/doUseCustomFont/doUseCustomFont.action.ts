import { DoUseCustomFontAction } from "./doUseCustomFont.types";

export const doUseCustomFontAction = (data: boolean): DoUseCustomFontAction => {
  return {
    type: "set_do_use_custom_font",
    payload: data,
  };
};
