import CookieManager from "@react-native-cookies/cookies"
import { map } from "lodash";
import cookie from "cookie";

export const getHoyolabCookie = async () => {
  try {
    const hoyolabCookie = await CookieManager.get(
      "https://act.hoyolab.com/app/community-game-records-sea/index.html"
    ).then((cookies: any) => {
      return map(cookies, (c: any) => {
        return cookie.serialize(c.name, c.value);
      }).join("; ");
    });
    return hoyolabCookie;
  } catch (e) {
    // error reading value
  }
};
