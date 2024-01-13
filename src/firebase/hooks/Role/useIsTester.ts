import { useEffect, useState } from "react";
import useMyFirebaseUid from "../FirebaseUid/useMyFirebaseUid";
import db from "../../db";

const useIsTester = () => {
  const uid = useMyFirebaseUid();
  const [isAdmin, setIsAdmin] = useState(false);
  useEffect(() => {
    if (uid) {
      db.Users.doc(uid)
        .get()
        .then((data) => {
          if (data.data()?.role === "beta_user") {
            setIsAdmin(true);
          } else {
            setIsAdmin(false);
          }
        });
    } else {
      setIsAdmin(false);
    }
  }, [uid]);
  return isAdmin;
};

export default useIsTester;
