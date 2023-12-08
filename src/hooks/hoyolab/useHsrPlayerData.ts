import useHoyolabGameRecord from "./useHoyolabGameRecord";

const useHsrPlayerData = () => {
  const gameRecord = useHoyolabGameRecord()?.data;
  const playerData = gameRecord?.list?.[1];
  return playerData;
};

export default useHsrPlayerData;
