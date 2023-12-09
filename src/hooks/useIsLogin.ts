import { useEffect, useState } from "react";
import {
  getIsLogin,
  setIsLogin as _setIsLogin,
} from "../utils/general/userIsLogin";

const useIsLogin = () => {
  const [loaded, setLoaded] = useState(false);
  const [isLogin, setIsLogin] = useState(false);

  // init
  useEffect(() => {
    getIsLogin().then((isLogin: boolean) => {
      setIsLogin(isLogin);
    });
    setLoaded(true);
  }, []);

  // handle state change
  useEffect(() => {
    loaded && _setIsLogin(isLogin);
  }, [isLogin, loaded]);

  return { isLogin, setIsLogin };
};

export default useIsLogin;
