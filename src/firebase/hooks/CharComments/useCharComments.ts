import CharacterComments from "../../models/CharacterComments";
import db from "../../db";
import { useQuery } from "react-query";

const useCharComments = (charId: string) => {
  const data = useQuery(["firebase-char-comments", charId], () =>
    db.CharacterComments.doc(charId)
      .get()
      .then((data) => {
        return data.data() as CharacterComments;
      })
  );

  return data;
};

export default useCharComments;
