import { HoyolabCookieAction } from "./hoyolabCookie.types";

export const hoyolabCookie = (
  prevSate: string = "",
  action: HoyolabCookieAction
) => {
  let newState = prevSate;
  if (action.type === "set_hoyolab_cookie") {
    newState = action.payload;
    return newState;
  } else {
    return prevSate;
  }
};
