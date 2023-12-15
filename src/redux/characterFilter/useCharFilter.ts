import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../store";
import { CharacterFilter, CharacterFilterType } from "./characterFilter.types";
import { characterFilterAction } from "./characterFilter.action";

const useCharFilter = () => {
  const dispatch = useDispatch();

  const charFilter = useSelector<RootState, CharacterFilter | undefined>(
    (state) => state.characterFilter
  );
  const setCharFilter = (filter: CharacterFilter) => {
    dispatch(characterFilterAction(filter));
  };

  const charFilterSelected = charFilter
    ?.filter((c) => c.selected)
    .map((s) => s.id);

  const setCharFilterSelected = (v: CharacterFilterType[]) => {
    setCharFilter(
      charFilter?.map((item) => ({
        id: item.id,
        name: item.name,
        selected: v.includes(item.id),
      }))!
    );
  };

  return {
    setCharFilter,
    charFilter,
    charFilterSelected,
    setCharFilterSelected,
  };
};

export default useCharFilter;
