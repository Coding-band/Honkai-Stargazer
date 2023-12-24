import { useQuery } from "react-query";
import useHoyolabCookie from "../../redux/hoyolabCookie/useHoyolabCookie";
import HoyolabRequest from "../../utils/hoyolab/HoyolabRequest";

const useHoyolabGameRecord = () => {
  const { hoyolabCookie, hoyolabCookieParse } = useHoyolabCookie();
  const hoyolabId = hoyolabCookieParse.account_id_v2;

  const { data, isError, error, isLoading, isFetching } = useQuery(
    ["hoyolab-game-record", hoyolabCookie, hoyolabId],
    () => new HoyolabRequest(hoyolabCookie).getGameRecord(hoyolabId),
    {
      select(data) {
        return data?.data;
      },
    }
  );

  return { data, isError, error, isLoading, isFetching };
};

export default useHoyolabGameRecord;
