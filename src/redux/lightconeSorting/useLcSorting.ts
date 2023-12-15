import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../store";
import { LightconeSorting, LightconeSortingType } from "./lightconeSorting.types";
import { lightconeSortingAction } from "./lightconeSorting.action";

const useLcSorting = () => {
  const dispatch = useDispatch();
  const lcSortingList = useSelector<RootState, LightconeSorting | undefined>(
    (state) => state.lightconeSorting
  );
  const lcSorting = lcSortingList?.filter((sorting) => sorting.selected)[0];
  const setLcSorting = (id: LightconeSortingType) => {
    dispatch(lightconeSortingAction(id));
  };
  return { lcSortingList, setLcSorting, lcSorting };
};

export default useLcSorting;
