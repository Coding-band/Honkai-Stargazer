export type WallPaperAction = {
  type: "set_wall_paper";
  id: number;
};

export type WallPaper = {
  id: number;
  url: string;
  name: string;
};
