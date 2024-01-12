import { useEffect, useState } from "react";
import db from "../../db";
import useMyFirebaseUid from "../FirebaseUid/useMyFirebaseUid";

const useIsAdmin = () => {
  const uid = useMyFirebaseUid();
  const [isAdmin, setIsAdmin] = useState(false);
  useEffect(() => {
    if (uid) {
      db.Users.doc(uid)
        .get()
        .then((data) => {
          if (data.data()?.role === "admin") {
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

export default useIsAdmin;
