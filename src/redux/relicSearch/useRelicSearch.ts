import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../store";
import { relicSearchAction } from "./relicSearch.action";

const useRelicSearch = () => {
  const dispatch = useDispatch();
  const searchValue = useSelector<RootState, string | undefined>(
    (state) => state.relicSearch
  );

  const setSearchValue = (v: string) => {
    dispatch(relicSearchAction(v));
  };
  return { searchValue, setSearchValue };
};

export default useRelicSearch;
