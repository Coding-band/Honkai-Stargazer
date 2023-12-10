import { useQuery } from "react-query";
import useHoyolabCookie from "../../redux/hoyolabCookie/useHoyolabCookie";
import HoyolabRequest from "../../utils/hoyolab/request/hoyolabRequest";

const useHoyolabGameRecord = () => {
  const { hoyolabCookie, hoyolabCookieParse } = useHoyolabCookie();
  const hoyolabId = hoyolabCookieParse.account_id_v2;

  const { data, isError, error, isLoading, isFetching } = useQuery(
    ["hoyolab-game-record", hoyolabCookie, hoyolabId],
    () => new HoyolabRequest(hoyolabCookie).send(getUrl(hoyolabId)),
    {
      select(data) {
        return data?.data;
      },
    }
  );

  return { data, isError, error, isLoading, isFetching };
};

export default useHoyolabGameRecord;

const getUrl = (hoyolabId: string) =>
  `https://bbs-api-os.hoyolab.com/game_record/card/wapi/getGameRecordCard?uid=${hoyolabId}`;
