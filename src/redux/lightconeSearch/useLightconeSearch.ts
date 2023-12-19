import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../store";
import { lightconeSearchAction } from "./lightconeSearch.action";

const useLightconeSearch = () => {
  const dispatch = useDispatch();
  const searchValue = useSelector<RootState, string | undefined>(
    (state) => state.lightconeSearch
  );

  const setSearchValue = (v: string) => {
    dispatch(lightconeSearchAction(v));
  };
  return { searchValue, setSearchValue };
};

export default useLightconeSearch;
