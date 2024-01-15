import { DoUseBlurEffectAction } from "./doUseBlurEffect.types";

export const doUseBlurEffectAction = (data: boolean): DoUseBlurEffectAction => {
  return {
    type: "set_do_use_blur_effect",
    payload: data,
  };
};
