import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../store";
import { LightconeFilter, LightconeFilterType } from "./lightconeFilter.types";
import { lightconeFilterAction } from "./lightconeFilter.action";

const useLcFilter = () => {
  const dispatch = useDispatch();

  const lcFilter = useSelector<RootState, LightconeFilter | undefined>(
    (state) => state.lightconeFilter
  );
  const setLcFilter = (filter: LightconeFilter) => {
    dispatch(lightconeFilterAction(filter));
  };

  const lcFilterSelected = lcFilter?.filter((c) => c.selected).map((s) => s.id);

  const setLcFilterSelected = (v: LightconeFilterType[]) => {
    setLcFilter(
      lcFilter?.map((item) => ({
        id: item.id,
        selected: v.includes(item.id),
      }))!
    );
  };

  return {
    setLcFilter,
    lcFilter,
    lcFilterSelected,
    setLcFilterSelected,
  };
};

export default useLcFilter;
