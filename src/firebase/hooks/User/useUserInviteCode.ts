import { useQuery } from "react-query";
import db from "../../db";

const useUserInviteCode = (uid: string) => {
  const inviteCode = useQuery(["firebase-user-invite-code", uid], () =>
    db.Users.doc(uid)
      .get()
      .then((data) => {
        const user = data.data();
        return user;
      })
  ).data?.invite_code;
  return inviteCode;
};

export default useUserInviteCode;
