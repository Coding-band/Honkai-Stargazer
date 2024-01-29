import { useQuery } from "react-query";
import useHoyolabCookie from "../../redux/hoyolabCookie/useHoyolabCookie";
import useHsrUUID from "./useHsrUUID";
import useHsrServerChosen from "../../redux/hsrServerChosen/useHsrServerChosen";
import HoyolabRequest from "../../utils/hoyolab/request/HoyolabRequest";
import MihoyoRequest from "../../utils/hoyolab/request/MihoyoRequest";
import { isHoyolabPlatform } from "../../utils/hoyolab/utils";
import appLangHoyoLangMap from "../../utils/hoyolab/language/appLangHoyoLangMap";
import useAppLanguage from "../../language/AppLanguage/useAppLanguage";

const useHsrFullData = () => {

  const { language } = useAppLanguage();


  const { hoyolabCookie } = useHoyolabCookie();
  const HsrUUID = useHsrUUID();
  const { hsrServerChosen } = useHsrServerChosen();

  const data = useQuery(
    ["hsr-full-data", hoyolabCookie, HsrUUID, hsrServerChosen],
    () =>
      new (isHoyolabPlatform(hsrServerChosen) ? HoyolabRequest : MihoyoRequest)(
        hoyolabCookie,
        appLangHoyoLangMap[language]
      ).getHsrFullData(HsrUUID, hsrServerChosen),
    {
      select(data) {
        return data?.data;
      },
    }
  );
  return data;
};

export default useHsrFullData;
