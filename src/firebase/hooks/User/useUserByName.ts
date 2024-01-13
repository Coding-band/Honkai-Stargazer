import { useQuery } from "react-query";
import db from "../../db";
import Users from "../../models/Users";

const useUserByName = (name: string | undefined) => {
  const data = useQuery(
    ["firebase-user-by-name", name],
    async () => {
      const querySnapshot = await db.Users.where("name", "==", name).get();
      if (querySnapshot.empty) {
        return null;
      }
      const user = querySnapshot.docs[0].data() as Users;
      return user;
    }
  );
  return data;
};

export default useUserByName;
