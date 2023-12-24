import { hsrServerId } from "../../utils/hoyolab/servers/hsrServer.types";
import { HsrServerChosenAction } from "./hsrServerChosen.types";

export const hsrServerChosenaAction = (
  data: hsrServerId
): HsrServerChosenAction => {
  return {
    type: "set_has_server_chosen",
    payload: data,
  };
};
