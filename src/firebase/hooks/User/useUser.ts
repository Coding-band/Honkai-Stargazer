import db from "../../db";
import { useQuery } from "react-query";
import Users from "../../models/Users";

const useUser = (userId: string) => {
  const data = useQuery(
    ["firebase-user", userId],
    () =>
      db.Users.doc(userId)
        .get()
        .then((data) => {
          const user = data.data() as Users;
          return user;
        }),
    { staleTime: 1000 * 60 }
  );
  return data;
};

export default useUser;
