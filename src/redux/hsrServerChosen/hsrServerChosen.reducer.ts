import { hsrServerId } from "../../types/hsrServer";
import { HsrServerChosenAction } from "./hsrServerChosen.types";

export const hsrServerChosen = (
  prevSate: hsrServerId = "asia",
  action: HsrServerChosenAction
) => {
  let newState = prevSate;
  if (action.type === "set_has_server_chosen") {
    newState = action.payload;
    return newState;
  } else {
    return prevSate;
  }
};
