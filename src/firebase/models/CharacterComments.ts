import { FirebaseFirestoreTypes } from "@react-native-firebase/firestore";

export default interface CharacterComments {
  user_id: string;
  content: string;
  createdAt: FirebaseFirestoreTypes.Timestamp;
}
