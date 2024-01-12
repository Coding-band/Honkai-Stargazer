import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../store";
import { doUseCustomFontAction } from "./doUseCustomFont.action";

const useDoUseCustomFont = () => {
  const dispatch = useDispatch();
  const doUseCustomFont = useSelector<RootState, boolean>(
    (state) => state.doUseCustomFont || false
  );
  const setDoUseCustomFont = (v: boolean) => {
    dispatch(doUseCustomFontAction(v));
  };
  return { doUseCustomFont, setDoUseCustomFont };
};

export default useDoUseCustomFont;
