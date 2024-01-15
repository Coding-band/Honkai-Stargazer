import { useQuery } from "react-query";
import db from "../../db";
import Users from "../../models/Users";
import useFirebaseUidByUUID from "../FirebaseUid/useFirebaseUidByUUID";

const useUserCharactersByUUID = (uuid: string) => {
  const firebaseUid = useFirebaseUidByUUID(uuid);

  const data = useQuery(
    ["firebase-user-characters-by-uuid", firebaseUid],
    async () => {
      const querySnapshot = await db.UserCharacters.doc(firebaseUid).get();
      const charsData = querySnapshot.data();
      return charsData;
    },
    { staleTime: 1000 * 60 }
  );
  return data;
};

export default useUserCharactersByUUID;
