// const route = useRoute<RouteProp<ParamList, "Character">>();

import {
  hsrPlatform,
  hsrServerId,
} from "../utils/hoyolab/servers/hsrServer.types";

export type ParamList = {
  Character: {
    id: string;
  };
  Lightcone: { id: string; name: string };
  Relic: { id: string; name: string };
  Login: { platform: hsrPlatform; serverId: hsrServerId };
  Event: { id: string };
  UserInfo: { uuid: string };
  UserCharDetail: { uuid: string; charId: string };
};
