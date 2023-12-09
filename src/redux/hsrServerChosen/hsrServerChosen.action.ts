import { hsrServerId } from "../../constant/hsrServer";
import { HsrServerChosenAction } from "./hsrServerChosen.types";

export const hsrServerChosenaAction = (
  data: hsrServerId
): HsrServerChosenAction => {
  return {
    type: "has_server_chosen",
    payload: data,
  };
};
