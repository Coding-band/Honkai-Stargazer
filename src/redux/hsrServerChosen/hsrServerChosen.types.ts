import { hsrServerId } from "../../utils/hoyolab/servers/hsrServer.types";

export type HsrServerChosenAction = {
  type: "set_has_server_chosen";
  payload: hsrServerId;
};
