import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../store";
import { characterSearchAction } from "./characterSearch.action";

const useCharacterSearch = () => {
  const dispatch = useDispatch();
  const searchValue = useSelector<RootState, string | undefined>(
    (state) => state.characterSearch
  );

  const setSearchValue = (v: string) => {
    dispatch(characterSearchAction(v));
  };
  return { searchValue, setSearchValue };
};

export default useCharacterSearch;
