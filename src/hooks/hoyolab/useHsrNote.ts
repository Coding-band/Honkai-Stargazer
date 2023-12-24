import { useQuery } from "react-query";
import useHoyolabCookie from "../../redux/hoyolabCookie/useHoyolabCookie";
import useHsrUUID from "./useHsrUUID";
import useHsrServerChosen from "../../redux/hsrServerChosen/useHsrServerChosen";
import HoyolabRequest from "../../utils/hoyolab/HoyolabRequest";

const useHsrNote = () => {
  const { hoyolabCookie } = useHoyolabCookie();
  const HsrUUID = useHsrUUID();
  const { hsrServerChosen } = useHsrServerChosen();

  const { data, isError, error, isLoading, isFetching, refetch } = useQuery(
    ["hsr-note", hoyolabCookie, , HsrUUID, hsrServerChosen],
    () =>
      new HoyolabRequest(hoyolabCookie).getHsrNote(HsrUUID, hsrServerChosen),
    {
      select(data) {
        return data.data;
      },
    }
  );
  return { data, isError, error, isLoading, isFetching, refetch };
};

export default useHsrNote;
