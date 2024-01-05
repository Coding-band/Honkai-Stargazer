import React from "react";
import Listbox from "../../../global/Listbox/Listbox";
import useCharSortingReverse from "../../../../redux/characterSortingReverse/useCharSortingReverse";
import OrderBtn from "./OrderBtn/OrderBtn";
import useCharSorting from "../../../../redux/characterSorting/useCharSorting";

export default function OrderAction() {
  const { charSorting, setCharSorting, charSortingList } = useCharSorting();
  const { setCharSortingReverse, charSortingReverse } = useCharSortingReverse();

  const handleReverse = () => {
    setCharSortingReverse(!charSortingReverse);
  };

  const handleOrderChange = (v: any) => {
    setCharSorting(v);
  };

  return (
    <Listbox
      button={
        <OrderBtn onPressReverse={handleReverse} reverse={!!charSortingReverse}>
          {charSorting?.name || ""}
        </OrderBtn>
      }
      value={charSorting?.id}
      onChange={handleOrderChange}
      bottom={56}
    >
      {charSortingList?.map((sorting) => (
        <Listbox.Item key={sorting.id} value={sorting.id}>
          {sorting.name}
        </Listbox.Item>
      )) || []}
    </Listbox>
  );
}
