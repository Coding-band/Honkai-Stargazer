import { hsrServer } from "../../utils/hoyolab/servers/hsrServer.types";
import { FirebaseFirestoreTypes } from "@react-native-firebase/firestore";

export default interface Users {
  uuid: string;
  name: string;
  avatar_url: string;
  role: "user" | "admin";
  plan: "normal" | "premium";
  level: number;
  region: hsrServer;
  invite_code: string;
  active_days: number;
  char_num: number;
  achievement_num: number;
  chest_num: number;
  show_info: boolean;
  last_login: FirebaseFirestoreTypes.Timestamp;
  wallpaperId: number;
}
