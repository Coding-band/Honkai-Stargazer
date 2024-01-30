import { useEffect, useState } from "react";
import auth from "@react-native-firebase/auth";

const useMyFirebaseUid = (type: "normal" | "ios-modify" = "normal") => {
  const [initializing, setInitializing] = useState(true);
  const [listenUser, setListenUser] = useState(false);
  const [uid, setUid] = useState<string>("");

  useEffect(() => {
    // 獲取 firebase auth uid
    const authListener = auth().onAuthStateChanged((user) => {
      if (user?.uid) {
        setUid(user?.uid);
      } else {
        setUid("");
      }
    });
    return () => {
      if (authListener) {
        authListener();
      }
    };
  }, []);

  useEffect(() => {
    let userListener: () => void;

    if (listenUser) {
      // TODO @react-native-firebase/auth provides `onUserChanged` which is this and more.
      // what else can we add and still be web-compatible?
      userListener = auth().onIdTokenChanged((user) => {
        if (user?.uid) {
          setUid(user?.uid);
        } else {
          setUid("");
        }
      });
    }

    return () => {
      if (userListener) {
        userListener();
      }
    };
  }, [listenUser]);

  if (initializing) {
    let waiting = true;
    setTimeout(() => {
      waiting = false;
    }, 1000);
  }

  if (type === "ios-modify") return [uid, setUid];
  return uid;
};

export default useMyFirebaseUid;
