import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../store";
import { hsrServerId } from "../../constant/hsrServer";
import { hsrServerChosenaAction } from "./hsrServerChosen.action";

const useHsrServerChosen = () => {
  const dispatch = useDispatch();
  const hsrServerChosen = useSelector<RootState, hsrServerId>(
    (state) => state.hsrServerChosen || "asia"
  );
  const setHsrServerChosen = (v: hsrServerId) => {
    dispatch(hsrServerChosenaAction(v));
  };
  return { hsrServerChosen, setHsrServerChosen };
};

export default useHsrServerChosen;
