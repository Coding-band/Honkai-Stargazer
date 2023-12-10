import { IsAcceptBindingPolicyAction } from "./isAcceptBindingPolicy.types";

export const isAcceptBindingPolicy = (
  prevSate: boolean = false,
  action: IsAcceptBindingPolicyAction
) => {
  let newState = prevSate;
  if (action.type === "set_is_accept_binding_policy") {
    newState = action.payload;
    return newState;
  } else {
    return prevSate;
  }
};
