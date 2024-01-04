import CookieManager from "@react-native-cookies/cookies";
import { map } from "lodash";
import cookieUtil from "cookie";
import { hsrPlatform } from "../servers/hsrServer.types";

export const cookieURLs = {
  mihoyo:
    "https://user.miyoushe.com/login-platform/mobile.html?app_id=bll8iq97cem8&theme=&token_type=4&game_biz=bbs_cn&redirect_url=https%253A%252F%252Fuser.miyoushe.com%252Fsingle-page%252Fcommunity-init.html%253Fapp_id%253Dbll8iq97cem8%2526ux_mode%253Dredirect%2526st%253Dhttps%25253A%25252F%25252Fm.miyoushe.com%25252Fsr%25252F%252523%25252Fhome%25252F0%2526dest%253Dhttps%25253A%25252F%25252Fpassport-api.miyoushe.com%25252Faccount%25252Fma-cn-session%25252Fweb%25252FcrossLoginStart%25253Fdest%25253Dhttps%2525253A%2525252F%2525252Fm.miyoushe.com%2525252Fsr%2525252F%25252523%2525252Fhome%2525252F0&st=https%253A%252F%252Fm.miyoushe.com%252Fsr%252F%2523%252Fhome%252F0&succ_back_type=redirect&fail_back_type=&ux_mode=redirect#/login/captcha",
  hoyolab: "https://act.hoyolab.com/app/community-game-records-sea/index.html",
};

export const getHoyolabCookieFromCookieManager = async (
  platform: hsrPlatform
) => {
  let hoyolabCookie = "";

  try {
    const hoyolabCookieParse = await CookieManager.get(
      platform === "hoyolab" ? cookieURLs.hoyolab : cookieURLs.mihoyo
    );
    hoyolabCookie = map(hoyolabCookieParse, (c: any) => {
      return cookieUtil.serialize(c.name, c.value);
    }).join("; ");
  } catch (e) {
    console.log(e);
  }

  console.log(hoyolabCookie);

  return hoyolabCookie;
};
