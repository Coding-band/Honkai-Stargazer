import { useMutation } from "react-query";
import db from "../../db";
import firestore from "@react-native-firebase/firestore";
import useMyFirebaseUid from "../FirebaseUid/useMyFirebaseUid";

const useAddCharComment = (charId: string) => {
  const uid = useMyFirebaseUid();

  const data = useMutation(
    async ({ id, content }: { id: string; content: string }) => {
      const doc = db.CharacterComments.doc(charId);
      const docIsExists = (await doc.get()).exists;
      if (docIsExists) {
        const prevCommentsNum = (await doc.get()).data()?.comments_num;
        await doc.update({
          comments_num: prevCommentsNum + 1,
          comments: firestore.FieldValue.arrayUnion({
            id,
            user_id: uid,
            content: content,
            createdAt: firestore.Timestamp.now(),
          }),
        });
      } else {
        await doc.set({
          comments_num: 1,
          comments: firestore.FieldValue.arrayUnion({
            id,
            user_id: uid,
            content: content,
            createdAt: firestore.Timestamp.now(),
          }),
        });
      }
    }
  );

  return data;
};

export default useAddCharComment;
