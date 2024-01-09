import { useContext } from "react";
import UserCharDetailContext from "../UserCharDetailContext";

const useProfileUUID = () => {
  const { uuid } = useContext(UserCharDetailContext)!;
  return uuid;
};

export default useProfileUUID;
