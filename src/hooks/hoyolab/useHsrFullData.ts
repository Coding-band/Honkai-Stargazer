import { useQuery } from "react-query";
import useHoyolabCookie from "./useHoyolabCookie";
import useHsrUUID from "./useHsrUUID";
import HoyolabRequest from "../../utils/hoyolab/request/hoyolabRequest";
import { hsrServerId, hsrServer } from "../../constant/hsrServer";
import useHsrServerChosen from "../../redux/hsrServerChosen/useHsrServerChosen";

const useHsrFullData = () => {
  const { hoyolabCookie } = useHoyolabCookie();
  const HsrUUID = useHsrUUID();
  const { hsrServerChosen } = useHsrServerChosen();

  const { data, isError, error, isLoading, isFetching } = useQuery(
    ["hsr-full-data", hoyolabCookie, HsrUUID, hsrServerChosen],
    () =>
      new HoyolabRequest(hoyolabCookie)
        .setDs()
        .send(getUrl(HsrUUID, hsrServerChosen)),
    {
      select(data) {
        return data?.data;
      },
    }
  );
  return { data, isError, error, isLoading, isFetching };
};

export default useHsrFullData;

const getUrl = (uuid: string, server: hsrServerId = "asia") =>
  `https://bbs-api-os.hoyolab.com/game_record/hkrpg/api/index?server=${hsrServer[server]}&role_id=${uuid}`;
