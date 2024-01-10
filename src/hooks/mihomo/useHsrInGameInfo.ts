import axios from "axios";
import { useQuery } from "react-query";

const useHsrInGameInfo = (uuid: string) => {
  const query = useQuery(
    ["hsr-ingame-info", uuid],
    () => axios.get(`https://api.mihomo.me/sr_info_parsed/${uuid}?lang=cht`),
    {
      select(data) {
        return data?.data;
      },
    }
  );
  return query;
};

export default useHsrInGameInfo;
