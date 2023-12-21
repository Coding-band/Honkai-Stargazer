import { wallPapers } from "./wallpapers";
import { WallPaperAction } from "./wallpaper.types";

export const wallPaper = (
  prevSate = wallPapers[0],
  action: WallPaperAction
) => {
  let newState = prevSate;
  if (action.type === "set_wall_paper") {
    const wpId = action.id;
    newState = wallPapers.filter((w) => w.id === wpId)[0];
    return newState;
  } else {
    return prevSate;
  }
};
