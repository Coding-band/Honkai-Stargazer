import { useQuery } from "react-query";
import db from "../../db";

const useUsersByName = (name: string | undefined) => {
  const data = useQuery(["firebase-users-by-name", name], async () => {
    const querySnapshot = await db.Users.where("name", "==", name).get();
    if (querySnapshot.empty) {
      return null;
    }
    const users: any[] = [];
    querySnapshot.forEach(async (doc) => {
      users.push(doc);
    });
    return users;
  });
  return data;
};

export default useUsersByName;
