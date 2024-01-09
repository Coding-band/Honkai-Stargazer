import React from "react";
import Listbox from "../../../global/Listbox/Listbox";
import OrderBtn from "./OrderBtn/OrderBtn";
import useLcSorting from "../../../../redux/lightconeSorting/useLcSorting";
import useLcSortingReverse from "../../../../redux/lightconeSortingReverse/useLcSortingReverse";
import useAppLanguage from "../../../../language/AppLanguage/useAppLanguage";
import { LOCALES } from "../../../../../locales";
import { capitalize } from "lodash";

export default function OrderAction() {
  const { language } = useAppLanguage();

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
          {/* @ts-ignore */}
          {LOCALES[language]["SortBy" + capitalize(lcSorting.id)]}
        </OrderBtn>
      }
      value={lcSorting?.id}
      onChange={handleOrderChange}
      bottom={56}
    >
      {lcSortingList?.map((sorting) => (
        <Listbox.Item key={sorting.id} value={sorting.id}>
          {/* @ts-ignore */}
          {LOCALES[language]["SortBy" + capitalize(sorting.id)]}
        </Listbox.Item>
      )) || []}
    </Listbox>
  );
}
