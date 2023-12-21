import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../store";
import { wallPaperAction } from "./wallPaper.action";
import { WallPaper } from "./wallpaper.types";

const useWallPaper = () => {
  const dispatch = useDispatch();
  const wallPaper = useSelector<RootState, WallPaper | undefined>(
    (state) => state.wallPaper
  );
  const setWallPaper = (wallPaperId: number) => {
    dispatch(wallPaperAction(wallPaperId));
  };
  return { wallPaper, setWallPaper };
};

export default useWallPaper;
