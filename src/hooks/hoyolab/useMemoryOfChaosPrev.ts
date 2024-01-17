import { useQuery } from "react-query";
import useHoyolabCookie from "../../redux/hoyolabCookie/useHoyolabCookie";
import useHsrUUID from "./useHsrUUID";
import useHsrServerChosen from "../../redux/hsrServerChosen/useHsrServerChosen";
import HoyolabRequest from "../../utils/hoyolab/request/HoyolabRequest";
import MihoyoRequest from "../../utils/hoyolab/request/MihoyoRequest";
import { isHoyolabPlatform } from "../../utils/hoyolab/utils";

const useMemoryOfChaosPrev = () => {
  const { hoyolabCookie } = useHoyolabCookie();
  const HsrUUID = useHsrUUID();
  const { hsrServerChosen } = useHsrServerChosen();

  const { data, isError, error, isLoading, isFetching } = useQuery(
    ["hsr-memory-of-chaos", hoyolabCookie, HsrUUID, hsrServerChosen],
    () =>
      new (isHoyolabPlatform(hsrServerChosen) ? HoyolabRequest : MihoyoRequest)(
        hoyolabCookie
      ).getHsrMemoryOfChaos(HsrUUID, hsrServerChosen, 2),
    {
      select(data) {
        return data?.data;
      },
    }
  );
  return { data, isError, error, isLoading, isFetching };
};

export default useMemoryOfChaosPrev;
