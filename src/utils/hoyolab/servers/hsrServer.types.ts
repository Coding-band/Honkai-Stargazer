export type Server = {
  id: hsrServerId;
  name: string;
  platform: hsrPlatform;
};

export type hsrServerId =
  | "asia"
  | "europe"
  | "america"
  | "twhkmo"
  | "cn1"
  | "cn2";

export type hsrPlatform = "hoyolab" | "mihoyo";
