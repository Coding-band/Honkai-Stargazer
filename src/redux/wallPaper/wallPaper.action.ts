import { WallPaperAction } from "./wallpaper.types";

export const wallPaperAction = (id: number): WallPaperAction => {
  return {
    type: "set_wall_paper",
    id,
  };
};
