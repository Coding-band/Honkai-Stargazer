import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../store";
import { doUseHomePageBlurEffectAction } from "./doUseHomePageBlurEffect.action";

const useDoUseHomePageBlurEffect = () => {
  const dispatch = useDispatch();
  const doUseHomePageBlurEffect = useSelector<RootState, boolean>(
    (state) => state.doUseHomePageBlurEffect || false
  );
  const setDoHomePageUseBlurEffect = (v: boolean) => {
    dispatch(doUseHomePageBlurEffectAction(v));
  };
  return { doUseHomePageBlurEffect, setDoHomePageUseBlurEffect };
};

export default useDoUseHomePageBlurEffect;
