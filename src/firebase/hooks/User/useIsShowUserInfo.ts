import { useMutation, useQuery } from "react-query";
import db from "../../db";
import useFirebaseUidByUUID from "../FirebaseUid/useFirebaseUidByUUID";

const useIsShowUserInfo = (uuid: string) => {
  const firebaseUid = useFirebaseUidByUUID(uuid);

  const { data: isShowInfo } = useQuery(
    ["firebase-is-show-user-info", firebaseUid],
    async () => {
      const show_info = (await db.Users.doc(firebaseUid).get()).data()
        ?.show_info;
      return show_info;
    }
  );

  const { mutate: setIsShowInfo } = useMutation(
    ["firebase-set-is-show-user-info", firebaseUid, isShowInfo],
    async (s: boolean) => {
      db.Users.doc(firebaseUid).update({
        show_info: s,
      });
    }
  );

  return { isShowInfo, setIsShowInfo };
};

export default useIsShowUserInfo;
