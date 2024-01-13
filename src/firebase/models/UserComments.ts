import { FirebaseFirestoreTypes } from "@react-native-firebase/firestore";

export default interface UserComments {
  comments_num: number;
  comments: {
    type: "character-comment";
    id: string;
    title: string;
    content: string;
    createdAt: FirebaseFirestoreTypes.Timestamp;
  }[];
}
