import { hsrServerId } from "../../types/hsrServer";

export type HsrServerChosenAction = {
  type: "set_has_server_chosen";
  payload: hsrServerId;
};
