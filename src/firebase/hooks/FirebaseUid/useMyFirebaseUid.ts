import { useEffect, useState } from "react";
import auth from "@react-native-firebase/auth";

const useMyFirebaseUid = () => {
  const [uid, setUid] = useState("");
  useEffect(() => {
    // 獲取 firebase auth uid
    auth().onAuthStateChanged((user) => {
      if (user?.uid) {
        setUid(user?.uid);
      }
    });
  }, []);
  return uid;
};

export default useMyFirebaseUid;
