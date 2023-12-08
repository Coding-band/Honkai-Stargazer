import useHoyolabGameRecord from "./useHoyolabGameRecord";

const useHsrUUID = () => {
  const { data: hoyolabGameRecord } = useHoyolabGameRecord();
  return hoyolabGameRecord?.list?.[1]?.game_role_id;
};

export default useHsrUUID;
