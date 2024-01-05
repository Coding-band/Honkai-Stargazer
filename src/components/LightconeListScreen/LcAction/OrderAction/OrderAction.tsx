import React from "react";
import Listbox from "../../../global/Listbox/Listbox";
import OrderBtn from "./OrderBtn/OrderBtn";
import useLcSorting from "../../../../redux/lightconeSorting/useLcSorting";
import useLcSortingReverse from "../../../../redux/lightconeSortingReverse/useLcSortingReverse";

export default function OrderAction() {
  const { lcSorting, setLcSorting, lcSortingList } = useLcSorting();
  const { setLcSortingReverse, lcSortingReverse } = useLcSortingReverse();

  const handleReverse = () => {
    setLcSortingReverse(!lcSortingReverse);
  };

  const handleOrderChange = (v: any) => {
    setLcSorting(v);
  };

  return (
    <Listbox
      button={
        <OrderBtn onPressReverse={handleReverse} reverse={!!lcSortingReverse}>
          {lcSorting?.name || ""}
        </OrderBtn>
      }
      value={lcSorting?.id}
      onChange={handleOrderChange}
      bottom={56}
    >
      {lcSortingList?.map((sorting) => (
        <Listbox.Item key={sorting.id} value={sorting.id}>
          {sorting.name}
        </Listbox.Item>
      )) || []}
    </Listbox>
  );
}
