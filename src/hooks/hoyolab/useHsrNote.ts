import { useQuery } from "react-query";
import useHoyolabCookie from "./useHoyolabCookie";
import useHsrUUID from "./useHsrUUID";
import HoyolabRequest from "../../utils/hoyolab/request/hoyolabRequest";
import { hsrServer, hsrServerId } from "../../constant/hsrServer";
import useHsrServerChosen from "../../redux/hsrServerChosen/useHsrServerChosen";

const useHsrNote = () => {
  const { hoyolabCookie } = useHoyolabCookie();
  const HsrUUID = useHsrUUID();
  const { hsrServerChosen } = useHsrServerChosen();

  const { data, isError, error, isLoading, isFetching, refetch } = useQuery(
    ["hsr-note", hoyolabCookie, , HsrUUID, hsrServerChosen],
    () =>
      new HoyolabRequest(hoyolabCookie)
        .setDs()
        .send(getUrl(HsrUUID, hsrServerChosen)),
    {
      select(data) {
        return data.data;
      },
    }
  );
  return { data, isError, error, isLoading, isFetching, refetch };
};

export default useHsrNote;

const getUrl = (uuid: string, server: hsrServerId = "asia") =>
  `https://bbs-api-os.hoyolab.com/game_record/hkrpg/api/note?server=${hsrServer[server]}&role_id=${uuid}`;
