import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../store";
import { doUseBlurEffectAction } from "./doUseBlurEffect.action";

const useDoUseBlurEffect = () => {
  const dispatch = useDispatch();
  const doUseBlurEffect = useSelector<RootState, boolean>(
    (state) => state.doUseBlurEffect || false
  );
  const setDoUseBlurEffect = (v: boolean) => {
    dispatch(doUseBlurEffectAction(v));
  };
  return { doUseBlurEffect, setDoUseBlurEffect };
};

export default useDoUseBlurEffect;
