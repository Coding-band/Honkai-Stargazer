import { useContext } from "react";
import UserCharDetailContext from "../UserCharDetailContext";

const useProfileCharId = () => {
  const { charId } = useContext(UserCharDetailContext)!;
  return charId;
};

export default useProfileCharId;
