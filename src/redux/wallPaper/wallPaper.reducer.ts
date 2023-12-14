import { WallPaperAction } from "./wallpaper.types";

export const wallPaper = (
  prevSate = `https://act-webstatic.hoyoverse.com/game_record/hkrpg/SpriteOutput/PhoneTheme/Theme/PhoneThemeMain/${221000}.png`,
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
