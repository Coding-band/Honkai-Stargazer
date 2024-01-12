import { useQuery } from "react-query";
import db from "../../db";
import Users from "../../models/Users";

const useUserByUUID = (uuid: string) => {
  const data = useQuery(["firebase-user-by-uuid", uuid], async () => {
    const querySnapshot = await db.Users.where("uuid", "==", uuid).get();
    if (querySnapshot.empty) {
      return null;
    }
    const user = querySnapshot.docs[0].data() as Users;
    return user;
  });
  return data;
};

export default useUserByUUID;
