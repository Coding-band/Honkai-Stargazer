import { useEffect, useState } from "react";
import useHsrUUID from "../../hooks/hoyolab/useHsrUUID";
import db from "../db";
import useFirebaseUid from "./useFirebaseUid";

const useIsAdmin = () => {
  const uid = useFirebaseUid();
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
