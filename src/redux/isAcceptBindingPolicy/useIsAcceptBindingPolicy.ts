import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../store";
import { isAcceptBindingPolicyAction } from "./isAcceptBindingPolicy.action";

const useIsAcceptBindingPolicy = () => {
  const dispatch = useDispatch();
  const isAcceptBindingPolicy = useSelector<RootState, boolean>(
    (state) => state.isAcceptBindingPolicy || false
  );
  const setIsAcceptBindingPolicy = (v: boolean) => {
    dispatch(isAcceptBindingPolicyAction(v));
  };
  return { isAcceptBindingPolicy, setIsAcceptBindingPolicy };
};

export default useIsAcceptBindingPolicy;
