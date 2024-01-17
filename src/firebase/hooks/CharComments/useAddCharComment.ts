import { useMutation } from "react-query";
import db from "../../db";
import firestore from "@react-native-firebase/firestore";
import useMyFirebaseUid from "../FirebaseUid/useMyFirebaseUid";

const useAddCharComment = (charId: string) => {
  const uid = useMyFirebaseUid();

  const data = useMutation(
    async ({ id, content }: { id: string; content: string }) => {
      const doc = db.CharacterComments(charId).doc(id);
      const docIsExists = (await doc.get()).exists;
   
      if (docIsExists) {
        await doc.update({
          user_id: uid,
          content: content,
          createdAt: firestore.Timestamp.now(),
        });
      } else {
        await doc.set({
          user_id: uid,
          content: content,
          createdAt: firestore.Timestamp.now(),
        });
      }
    }
  );

  return data;
};

export default useAddCharComment;
