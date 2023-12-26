import useHsrPlayerData from "./useHsrPlayerData";

const useHsrUUID = () => {
  const playerData = useHsrPlayerData();
  return playerData?.game_role_id;
};

export default useHsrUUID;
