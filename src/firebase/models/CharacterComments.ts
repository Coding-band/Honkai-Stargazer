import { FirebaseFirestoreTypes } from "@react-native-firebase/firestore";

export default interface CharacterComments {
  comments_num: number;
  comments: {
    id: string;
    user_id: string;
    content: string;
    mentions: string[];
    createdAt: FirebaseFirestoreTypes.Timestamp;
  }[];
}
