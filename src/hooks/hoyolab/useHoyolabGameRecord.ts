import { useQuery } from "react-query";
import useHoyolabCookie from "../../redux/hoyolabCookie/useHoyolabCookie";
import HoyolabRequest from "../../utils/hoyolab/request/HoyolabRequest";
import MihoyoRequest from "../../utils/hoyolab/request/MihoyoRequest";
import useHsrServerChosen from "../../redux/hsrServerChosen/useHsrServerChosen";
import { isHoyolabPlatform } from "../../utils/hoyolab/utils";
import useAppLanguage from "../../language/AppLanguage/useAppLanguage";
import { LanguageEnum } from "../../utils/hoyolab/language/language.interface";

const useHoyolabGameRecord = () => {

  const { hoyolabCookie, hoyolabCookieParse } = useHoyolabCookie();
  const { hsrServerChosen } = useHsrServerChosen();

  const hoyolabId = isHoyolabPlatform(hsrServerChosen)
    ? hoyolabCookieParse.account_id_v2
    : hoyolabCookieParse.ltuid_v2;

  const { data, isError, error, isLoading, isFetching } = useQuery(
    ["hoyolab-game-record", hoyolabCookie, hoyolabId],
    () =>
      new (isHoyolabPlatform(hsrServerChosen) ? HoyolabRequest : MihoyoRequest)(
        hoyolabCookie
      ).getGameRecord(hoyolabId),
    {
      select(data) {
        return data?.data;
      },
    }
  );

  return { data, isError, error, isLoading, isFetching };
};

export default useHoyolabGameRecord;
