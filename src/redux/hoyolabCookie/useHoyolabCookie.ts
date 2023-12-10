import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../store";
import { hoyolabCookieAction } from "./hoyolabCookie.action";
import cookie from "cookie";

const useHoyolabCookie = () => {
  const dispatch = useDispatch();
  const hoyolabCookie = useSelector<RootState, string>(
    (state) => state.hoyolabCookie || ""
  );
  const hoyolabCookieParse = cookie.parse(hoyolabCookie);
  const setHoyolabCookie = (v: string) => {
    dispatch(hoyolabCookieAction(v.trim()));
  };
  return { hoyolabCookie, setHoyolabCookie, hoyolabCookieParse };
};

export default useHoyolabCookie;
