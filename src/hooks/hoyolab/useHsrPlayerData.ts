import useHsrServerChosen from "../../redux/hsrServerChosen/useHsrServerChosen";
import { hsrServer } from "../../utils/hoyolab/servers/hsrServer";
import useHoyolabGameRecord from "./useHoyolabGameRecord";

const useHsrPlayerData = () => {
  const { hsrServerChosen } = useHsrServerChosen();

  const gameRecord = useHoyolabGameRecord()?.data;
  const playerData = gameRecord?.list?.filter(
    (game: any) =>
      game.game_id === 6 && game.region === hsrServer[hsrServerChosen]
  )[0];
  return playerData;
};

export default useHsrPlayerData;
