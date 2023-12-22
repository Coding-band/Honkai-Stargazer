import CookieManager from "@react-native-cookies/cookies";
import { map } from "lodash";
import cookieUtil from "cookie";

export const getHoyolabCookieFromCookieManager = async () => {
  const hoyolabCookie = await CookieManager.get(
    "https://act.hoyolab.com/app/community-game-records-sea/index.html"
  ).then((cookies: any) => {
    return map(cookies, (c: any) => {
      return cookieUtil.serialize(c.name, c.value);
    }).join("; ");
  });

  return hoyolabCookie;
};
