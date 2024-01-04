import { useEffect, useState } from "react";
import useHsrUUID from "../../hooks/hoyolab/useHsrUUID";
import db from "../db";

const useIsTester = () => {
  const hsrUUID = useHsrUUID();
  const [isAdmin, setIsAdmin] = useState(false);
  useEffect(() => {
    if (hsrUUID) {
      db.Users.doc(hsrUUID)
        .get()
        .then((data) => {
          if (data.data()?.role === "tester") {
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

export default useIsTester;
