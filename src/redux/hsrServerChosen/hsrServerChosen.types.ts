import { hsrServerId } from "../../constant/hsrServer";

export type HsrServerChosenAction = {
  type: "has_server_chosen";
  payload: hsrServerId;
};
