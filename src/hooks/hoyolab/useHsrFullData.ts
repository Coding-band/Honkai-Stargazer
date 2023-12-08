import { useQuery } from "react-query";
import axios from "axios";
import useHoyolabCookie from "./useHoyolabCookie";
import useHsrUUID from "./useHsrUUID";
import generateDS from "../../utils/hoyolab/generateDs";
import HoyolabRequest from "../../utils/hoyolab/request/hoyolabRequest";

const useHsrFullData = () => {
  const { hoyolabCookie } = useHoyolabCookie();
  const HsrUUID = useHsrUUID();

  const { data, isError, error, isLoading, isFetching } = useQuery(
    ["hsr-full-data", HsrUUID],
    () => new HoyolabRequest(hoyolabCookie).setDs().send(getUrl(HsrUUID)),
    {
      select(data) {
        return data?.data;
      },
    }
  );
  return { data, isError, error, isLoading, isFetching };
};

export default useHsrFullData;

const getUrl = (uuid: string) =>
  `https://bbs-api-os.hoyolab.com/game_record/hkrpg/api/index?server=prod_official_asia&role_id=${uuid}`;
