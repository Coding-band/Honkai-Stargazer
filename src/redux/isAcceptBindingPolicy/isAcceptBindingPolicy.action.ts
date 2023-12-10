import { IsAcceptBindingPolicyAction } from "./isAcceptBindingPolicy.types";

export const isAcceptBindingPolicyAction = (
  data: boolean
): IsAcceptBindingPolicyAction => {
  return {
    type: "set_is_accept_binding_policy",
    payload: data,
  };
};
