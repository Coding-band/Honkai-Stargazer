import CharacterComments from "../../models/CharacterComments";
import db from "../../db";
import { useQuery } from "react-query";

const useCharComments = (charId: string) => {
  const data = useQuery(["firebase-char-comments", charId], async () =>
    (await db.CharacterComments(charId).orderBy("createdAt").get()).docs.map(
      (doc) => {
     
        return { id: doc.id, ...doc.data() } as unknown as CharacterComments[];
      }
    )
  );
  return data;
};

export default useCharComments;
