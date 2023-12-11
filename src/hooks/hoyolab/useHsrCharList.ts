import useHsrFullData from "./useHsrFullData";

const useHsrCharList = () => {
  const { data: hsrFullData } = useHsrFullData();
  return hsrFullData?.avatar_list;
};

export default useHsrCharList;
