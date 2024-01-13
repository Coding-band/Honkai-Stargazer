import db from "../../db";
import { useQuery } from "react-query";
import UserComments from "../../models/UserComments";

const useUserComments = (uid: string) => {
  const data = useQuery(["firebase-user-comments", uid], () =>
    db.UserComments.doc(uid)
      .get()
      .then((data) => {
        return data.data() as UserComments;
      })
  );

  return data;
};

export default useUserComments;
