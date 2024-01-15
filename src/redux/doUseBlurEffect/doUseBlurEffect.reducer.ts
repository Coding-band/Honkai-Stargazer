import { DoUseBlurEffectAction } from "./doUseBlurEffect.types";

export const doUseBlurEffect = (
  prevSate: boolean = true,
  action: DoUseBlurEffectAction
) => {
  let newState = prevSate;
  if (action.type === "set_do_use_blur_effect") {
    newState = action.payload;
    return newState;
  } else {
    return prevSate;
  }
};
