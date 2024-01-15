import { useQuery } from "react-query";
import db from "../../db";
import useFirebaseUidByUUID from "../FirebaseUid/useFirebaseUidByUUID";

const useUserMocByUUID = (uuid: string) => {
  const firebaseUid = useFirebaseUidByUUID(uuid);

  const data = useQuery(
    ["firebase-user-moc-by-uuid", firebaseUid],
    async () => {
      const querySnapshot = await db
        .UserMemoryOfChaos(1009)
        .doc(firebaseUid)
        .get();
      const mocData = querySnapshot.data();
      return mocData;
    },
    { staleTime: 1000 * 60 }
  );
  return data;
};

export default useUserMocByUUID;
