import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../store";
import { lightconeIsSearchingAction } from "./lightconeIsSearching.action";

const useLightconeIsSearching = () => {
  const dispatch = useDispatch();
  const isSearching = useSelector<RootState, boolean | undefined>(
    (state) => state.lightconeIsSearching
  );

  const setIsSearching = (v: boolean) => {
    dispatch(lightconeIsSearchingAction(v));
  };
  return { isSearching, setIsSearching };
};

export default useLightconeIsSearching;
