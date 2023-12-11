import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../store";
import {
  CharacterSorting,
  CharacterSortingType,
} from "./characterSorting.types";
import { characterSortingAction } from "./characterSorting.action";

const useCharSorting = () => {
  const dispatch = useDispatch();
  const charSortingList = useSelector<RootState, CharacterSorting | undefined>(
    (state) => state.characterSorting
  );
  const charSorting = charSortingList?.filter((sorting) => sorting.selected)[0];
  const setCharSorting = (id: CharacterSortingType) => {
    dispatch(characterSortingAction(id));
  };
  return { charSortingList, setCharSorting, charSorting };
};

export default useCharSorting;
