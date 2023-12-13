import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../store";
import { characterSortingReverseAction } from "./characterSortingReverse.action";

const useCharSortingReverse = () => {
  const dispatch = useDispatch();
  const charSortingReverse = useSelector<RootState, boolean | undefined>(
    (state) => state.characterSortingReverse
  );

  const setCharSortingReverse = (v: boolean) => {
    dispatch(characterSortingReverseAction(v));
  };
  return { charSortingReverse, setCharSortingReverse };
};

export default useCharSortingReverse;
