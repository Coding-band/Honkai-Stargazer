import { useQuery } from "react-query";
import db from "../../db";
import Users from "../../models/Users";

const useUserByUUID = (uuid: string) => {
  const data = useQuery(
    ["firebase-user-by-uuid", uuid],
    async () => {
      let user: any = {};
      if (uuid) {
        const querySnapshot = await db.Users.where("uuid", "==", uuid).get();
        if (querySnapshot.empty) {
          return null;
        }
        user = querySnapshot.docs[0].data() as Users;
      }
      return user;
    },
    { staleTime: 1000 * 60 }
  );
  return data;
};

export default useUserByUUID;
