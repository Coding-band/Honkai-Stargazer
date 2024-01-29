import { useQuery } from "react-query";
import useHoyolabCookie from "../../redux/hoyolabCookie/useHoyolabCookie";
import useHsrUUID from "./useHsrUUID";
import useHsrServerChosen from "../../redux/hsrServerChosen/useHsrServerChosen";
import HoyolabRequest from "../../utils/hoyolab/request/HoyolabRequest";
import MihoyoRequest from "../../utils/hoyolab/request/MihoyoRequest";
import { isHoyolabPlatform } from "../../utils/hoyolab/utils";
import appLangHoyoLangMap from "../../utils/hoyolab/language/appLangHoyoLangMap";
import useAppLanguage from "../../language/AppLanguage/useAppLanguage";

const useMemoryOfChaos = (scheduleType: 1 | 2 = 1) => {

  const { language } = useAppLanguage();


  const { hoyolabCookie } = useHoyolabCookie();
  const HsrUUID = useHsrUUID();
  const { hsrServerChosen } = useHsrServerChosen();

  const { data, isError, error, isLoading, isFetching } = useQuery(
    [
      "hsr-memory-of-chaos",
      hoyolabCookie,
      HsrUUID,
      hsrServerChosen,
      scheduleType,
    ],
    () =>
      new (isHoyolabPlatform(hsrServerChosen) ? HoyolabRequest : MihoyoRequest)(
        hoyolabCookie,
        appLangHoyoLangMap[language]
      ).getHsrMemoryOfChaos(HsrUUID, hsrServerChosen, scheduleType),
    {
      select(data) {
        return data?.data;
      },
    }
  );
  return { data, isError, error, isLoading, isFetching };
};

export default useMemoryOfChaos;
