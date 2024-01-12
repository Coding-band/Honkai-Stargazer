import { useEffect, useState } from "react";
import auth from "@react-native-firebase/auth";
import { useQuery } from "react-query";
import db from "../../db";

const useFirebaseUidByUUID = (uuid: string) => {
  const [uid, setUid] = useState("");
  useQuery(["firebase-uid-by-uuid", uuid], async () => {
    const querySnapshot = await db.Users.where("uuid", "==", uuid).get();
    querySnapshot.forEach((doc) => {
      setUid(doc.id);
    });
  });
  return uid;
};

export default useFirebaseUidByUUID;
