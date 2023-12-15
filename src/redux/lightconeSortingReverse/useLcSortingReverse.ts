import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../store";
import { lightconeSortingReverseAction } from "./lightconeSortingReverse.action";

const useLcSortingReverse = () => {
  const dispatch = useDispatch();
  const lcSortingReverse = useSelector<RootState, boolean | undefined>(
    (state) => state.lightconeSortingReverse
  );

  const setLcSortingReverse = (v: boolean) => {
    dispatch(lightconeSortingReverseAction(v));
  };
  return { lcSortingReverse, setLcSortingReverse };
};

export default useLcSortingReverse;
