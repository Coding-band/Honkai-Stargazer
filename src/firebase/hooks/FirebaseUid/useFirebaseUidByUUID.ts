import { useQuery } from "react-query";
import db from "../../db";

const useFirebaseUidByUUID = (uuid: string) => {
  const data = useQuery(["firebase-uid-by-uuid", uuid], async () => {
    let docId = "";
    const querySnapshot = await db.Users.where("uuid", "==", uuid).get();
    querySnapshot.forEach((doc) => {
      docId = doc.id;
    });

    return docId;
  });
  return data.data;
};

export default useFirebaseUidByUUID;
