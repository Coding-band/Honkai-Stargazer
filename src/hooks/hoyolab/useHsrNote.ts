import { useQuery } from "react-query";
import axios from "axios";
import useHoyolabCookie from "./useHoyolabCookie";
import useHsrUUID from "./useHsrUUID";
import generateDS from "../../utils/hoyolab/generateDs";
import HoyolabRequest from "../../utils/hoyolab/request/hoyolabRequest";

const useHsrNote = () => {
  const { hoyolabCookie } = useHoyolabCookie();
  const HsrUUID = useHsrUUID();

  const { data, isError, error, isLoading, isFetching, refetch } = useQuery(
    ["hsr-note", HsrUUID],
    () => new HoyolabRequest(hoyolabCookie).setDs().send(getUrl(HsrUUID)),
    {
      select(data) {
        return data.data;
      },
    }
  );
  return { data, isError, error, isLoading, isFetching, refetch };
};

export default useHsrNote;

const getUrl = (uuid: string) =>
  `https://bbs-api-os.hoyolab.com/game_record/hkrpg/api/note?server=prod_official_asia&role_id=${uuid}`;
