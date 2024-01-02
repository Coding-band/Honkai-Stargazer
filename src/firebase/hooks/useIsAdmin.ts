import { useEffect, useState } from "react";
import useHsrUUID from "../../hooks/hoyolab/useHsrUUID";
import db from "../db";

const useIsAdmin = () => {
  const hsrUUID = useHsrUUID();
  const [isAdmin, setIsAdmin] = useState(false);
  useEffect(() => {
    if (hsrUUID) {
      db.Users.doc(hsrUUID)
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
  }, [hsrUUID]);
  return isAdmin;
};

export default useIsAdmin;
