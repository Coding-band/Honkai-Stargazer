import { findKey } from "lodash";
import useHsrInGameInfo from "../../../hooks/mihomo/useHsrInGameInfo";
import useProfileUUID from "./useProfileUUID";
import officalCharId from "../../../../map/character_offical_id_map";
import useProfileCharId from "./useProfileCharId";
import useUserCharactersByUUID from "../../../firebase/hooks/UserCharacters/useUserCharactersByUUID";

const useProfileHsrInGameInfo = () => {
  // 角色頁面之用戶 uid
  const profileUUID = useProfileUUID();
  // 角色 id
  const charId = useProfileCharId();
  // 及時的展櫃資料
  const inGameInfo = useHsrInGameInfo(profileUUID).data as any;
  // 及時的展櫃 - 該角色資料
  const inGameCharDataRealTime = inGameInfo?.characters?.filter(
    (char: any) => char.id === findKey(officalCharId, (v) => v === charId)
  )[0];
  // 來自 firebase 的展櫃資料 (舊角色記憶)
  const userCharDetails =
    useUserCharactersByUUID(profileUUID).data?.characters_details;
  // 來自 firebase 的展櫃資料 - 該角色資料
  const inGameCharDataFromDB = userCharDetails?.filter(
    (char: any) => char.id === findKey(officalCharId, (v) => v === charId)
  )[0];

  return {
    inGameCharData: inGameCharDataRealTime || inGameCharDataFromDB,
    inGameInfo,
  };
};

export default useProfileHsrInGameInfo;
