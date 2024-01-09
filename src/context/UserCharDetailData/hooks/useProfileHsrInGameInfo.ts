import { findKey } from "lodash";
import useHsrInGameInfo from "../../../hooks/mihomo/useHsrInGameInfo";
import useProfileUUID from "./useProfileUUID";
import officalCharId from "../../../../map/character_offical_id_map";
import useProfileCharId from "./useProfileCharId";

const useProfileHsrInGameInfo = () => {
  const profileUUID = useProfileUUID();
  const charId = useProfileCharId();
  const { data } = useHsrInGameInfo(profileUUID);
  return {
    inGameInfo: data,
    inGameCharData: data.characters?.filter(
      (char: any) => char.id === findKey(officalCharId, (v) => v === charId)
    )[0],
  };
};

export default useProfileHsrInGameInfo;
