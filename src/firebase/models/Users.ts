import { hsrServer } from "../../utils/hoyolab/servers/hsrServer.types";
import firestore, {
  FirebaseFirestoreTypes,
} from "@react-native-firebase/firestore";

export default interface Users {
  uuid: string;
  name: string;
  avatar_url: string;
  role: "user" | "admin";
  plan: "normal" | "premium";
  level: number;
  region: hsrServer;
  active_days: number;
  char_num: number;
  achievement_num: number;
  chest_num: number;
  last_login: FirebaseFirestoreTypes.Timestamp;
}
