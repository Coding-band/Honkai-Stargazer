import { useQuery } from "react-query";
import useHoyolabCookie from "../../redux/hoyolabCookie/useHoyolabCookie";
import useHsrUUID from "./useHsrUUID";
import useHsrServerChosen from "../../redux/hsrServerChosen/useHsrServerChosen";
import HoyolabRequest from "../../utils/hoyolab/request/HoyolabRequest";
import MihoyoRequest from "../../utils/hoyolab/request/MihoyoRequest";
import { isHoyolabPlatform } from "../../utils/hoyolab/utils";
import appLangHoyoLangMap from "../../utils/hoyolab/language/appLangHoyoLangMap";
import useAppLanguage from "../../language/AppLanguage/useAppLanguage";

const useHsrNote = () => {

  const { language } = useAppLanguage();


  const { hoyolabCookie } = useHoyolabCookie();
  const HsrUUID = useHsrUUID();
  const { hsrServerChosen } = useHsrServerChosen();

  const data = useQuery(
    ["hsr-note", hoyolabCookie, , HsrUUID, hsrServerChosen],
    () =>
      new (isHoyolabPlatform(hsrServerChosen) ? HoyolabRequest : MihoyoRequest)(
        hoyolabCookie,
        appLangHoyoLangMap[language]
      ).getHsrNote(HsrUUID, hsrServerChosen),
    {
      select(data) {
        return data.data;
      },
      refetchInterval: 1000 * 60 * 1,
    }
  );
  return data;
};

export default useHsrNote;
