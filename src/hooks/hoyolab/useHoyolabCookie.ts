import { useEffect, useState } from "react";
import cookie from "cookie";
import { getHoyolabCookie } from "../../utils/hoyolab/hoyolabCookie";

const useHoyolabCookie = () => {
  const [hoyolabCookie, setHoyolabCookie] = useState<any>(null);
  const hoyolabCookieParse = cookie.parse(hoyolabCookie || "");

  useEffect(() => {
    getHoyolabCookie().then((cookie: any) => {
      if (!hoyolabCookie) {
        setHoyolabCookie(cookie);
      }
    });
  }, [hoyolabCookie]);

  return { hoyolabCookie, hoyolabCookieParse };
};

export default useHoyolabCookie;
