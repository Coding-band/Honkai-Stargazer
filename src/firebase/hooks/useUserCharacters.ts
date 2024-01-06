import db from "../db";
import { useQuery } from "react-query";
import UserCharacters from "../models/UserCharacters";

const useUserCharacters = (userId: string) => {
  const data = useQuery(["user-characters", userId], () =>
    db.UserCharacters.doc(userId)
      .get()
      .then((data) => {
        const user = data.data() as UserCharacters;
        return user;
      })
  );
  return data;
};

export default useUserCharacters;
