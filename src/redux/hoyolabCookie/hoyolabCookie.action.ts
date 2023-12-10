import { HoyolabCookieAction } from "./hoyolabCookie.types";

export const hoyolabCookieAction = (data: string): HoyolabCookieAction => {
  return {
    type: "set_hoyolab_cookie",
    payload: data,
  };
};
