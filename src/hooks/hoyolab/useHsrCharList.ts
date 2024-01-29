import { useQuery } from "react-query";
import useHoyolabCookie from "../../redux/hoyolabCookie/useHoyolabCookie";
import useHsrServerChosen from "../../redux/hsrServerChosen/useHsrServerChosen";
import useHsrFullData from "./useHsrFullData";
import useHsrUUID from "./useHsrUUID";
import { isHoyolabPlatform } from "../../utils/hoyolab/utils";
import HoyolabRequest from "../../utils/hoyolab/request/HoyolabRequest";
import MihoyoRequest from "../../utils/hoyolab/request/MihoyoRequest";
import useAppLanguage from "../../language/AppLanguage/useAppLanguage";
import appLangHoyoLangMap from "../../utils/hoyolab/language/appLangHoyoLangMap";

const useHsrCharList = () => {
  const { language } = useAppLanguage();

  const { hoyolabCookie } = useHoyolabCookie();
  const HsrUUID = useHsrUUID();
  const { hsrServerChosen } = useHsrServerChosen();

  const data = useQuery(
    ["hsr-char-list", hoyolabCookie, HsrUUID, hsrServerChosen],
    () =>
      new (isHoyolabPlatform(hsrServerChosen) ? HoyolabRequest : MihoyoRequest)(
        hoyolabCookie,
        appLangHoyoLangMap[language]
      ).getHsrCharactersData(HsrUUID, hsrServerChosen),
    {
      select(data) {
        return data?.data?.avatar_list;
      },
    }
  );
  return data;
};

export default useHsrCharList;
