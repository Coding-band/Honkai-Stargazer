import db from "../db";
import { useQuery } from "react-query";
import Users from "../models/Users";

const useUser = (userId: string) => {
  const data = useQuery(["user", userId], () =>
    db.Users.doc(userId)
      .get()
      .then((data) => {
        const user = data.data() as Users;
        return user;
      })
  );
  return data;
};

export default useUser;
