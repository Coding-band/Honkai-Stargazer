import { WallPaperAction } from "./wallpaper.types";

const randomWallPaper = Math.floor(Math.random() * 3) + 221000;

export const wallPaper = (
  prevSate = `https://act-webstatic.hoyoverse.com/game_record/hkrpg/SpriteOutput/PhoneTheme/Theme/PhoneThemeMain/${randomWallPaper}.png`,
  action: WallPaperAction
) => {
  let newState = prevSate;
  if (action.type === "set_wall_paper") {
    newState = action.payload;
    return newState;
  } else {
    return prevSate;
  }
};
