import { useEffect, useState } from "react";
import {
  getUserIsAcceptBindingPolicy,
  setUserIsAcceptBindingPolicy,
} from "../utils/general/userIsAcceptBindingPolicy";

const useAcceptBindingPolicy = () => {
  const [loaded, setLoaded] = useState(false);
  const [isLogin, setIsLogin] = useState(false);

  // init
  useEffect(() => {
    getUserIsAcceptBindingPolicy().then((isLogin: boolean) => {
      setIsLogin(isLogin);
    });
    setLoaded(true);
  }, []);

  // handle state change
  useEffect(() => {
    loaded && setUserIsAcceptBindingPolicy(isLogin);
  }, [isLogin, loaded]);

  return {
    isAcceptBindingPolicy: isLogin,
    setIsAcceptBindingPolicy: setIsLogin,
  };
};

export default useAcceptBindingPolicy;
