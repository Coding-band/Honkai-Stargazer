import { hsrServer } from "../../utils/hoyolab/servers/hsrServer.types";

export default interface Users {
  id: string;
  name: string;
  level: number;
  region: hsrServer;
  active_days: number;
  char_num: number;
  achievement_num: number;
  chest_num: number;
}
