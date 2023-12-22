import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../store";
import { relicIsSearchingAction } from "./relicIsSearching.action";

const useRelicIsSearching = () => {
  const dispatch = useDispatch();
  const isSearching = useSelector<RootState, boolean | undefined>(
    (state) => state.relicIsSearching
  );

  const setIsSearching = (v: boolean) => {
    dispatch(relicIsSearchingAction(v));
  };
  return { isSearching, setIsSearching };
};

export default useRelicIsSearching;
