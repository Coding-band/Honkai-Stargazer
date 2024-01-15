import { DoUseBlurHomePageEffectAction } from "./doUseHomePageBlurEffect.types";

export const doUseHomePageBlurEffectAction = (
  data: boolean
): DoUseBlurHomePageEffectAction => {
  return {
    type: "set_do_use_home_page_blur_effect",
    payload: data,
  };
};
