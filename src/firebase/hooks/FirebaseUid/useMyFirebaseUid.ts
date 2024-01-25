import { useEffect, useState } from "react";
import auth from "@react-native-firebase/auth";

const useMyFirebaseUid = (type: "normal" | "ios-modify" = "normal") => {
  const [uid, setUid] = useState<string>("");
  useEffect(() => {
    // 獲取 firebase auth uid
    auth().onAuthStateChanged((user) => {
      if (user?.uid) {
        setUid(user?.uid);
      } else {
        setUid("");
      }
    });
  }, []);
  if (type === "ios-modify") return [uid, setUid];
  return uid;
};

export default useMyFirebaseUid;
