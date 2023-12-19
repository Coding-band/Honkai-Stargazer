import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../store";
import { characterIsSearchingAction } from "./characterIsSearching.action";

const useCharacterIsSearching = () => {
  const dispatch = useDispatch();
  const isSearching = useSelector<RootState, boolean | undefined>(
    (state) => state.characterIsSearching
  );

  const setIsSearching = (v: boolean) => {
    dispatch((characterIsSearchingAction(v)));
  };
  return { isSearching, setIsSearching };
};

export default useCharacterIsSearching;
