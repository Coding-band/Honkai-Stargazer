import { WallPaperAction } from "./wallpaper.types";

export const wallPaperAction = (data: string): WallPaperAction => {
  return {
    type: "set_wall_paper",
    payload: data,
  };
};
