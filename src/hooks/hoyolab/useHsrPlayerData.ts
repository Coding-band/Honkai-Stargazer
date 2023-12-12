import useHoyolabGameRecord from "./useHoyolabGameRecord";

const useHsrPlayerData = () => {
  const gameRecord = useHoyolabGameRecord()?.data;
  const playerData = gameRecord?.list?.filter(
    (game: any) => game.game_id === 6
  )[0];
  return playerData;
};

export default useHsrPlayerData;
