import { useEffect, useState } from "react";
import auth from "@react-native-firebase/auth";
import useFirebaseUidByUUID from "./useFirebaseUidByUUID";
import useHsrUUID from "../../../hooks/hoyolab/useHsrUUID";

const useMyFirebaseUid = () => {
  const hsrUUID = useHsrUUID();
  const firebaseUID = useFirebaseUidByUUID(hsrUUID);
  return firebaseUID || "";
};

export default useMyFirebaseUid;
