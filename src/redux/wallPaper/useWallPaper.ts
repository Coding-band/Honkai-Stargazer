import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../store";
import { wallPaperAction } from "./wallPaper.action";

const useWallPaper = () => {
  const dispatch = useDispatch();
  const wallPaper = useSelector<RootState, string | undefined>(
    (state) => state.wallPaper
  );
  const setWallPaper = (v: string) => {
    dispatch(wallPaperAction(v));
  };
  return { wallPaper, setWallPaper };
};

export default useWallPaper;
