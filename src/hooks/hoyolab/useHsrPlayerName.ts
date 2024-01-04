import useHsrPlayerData from "./useHsrPlayerData";

const useHsrPlayerName = () => {
  const playerData = useHsrPlayerData();
  return playerData?.nickname;
};

export default useHsrPlayerName;
