import { useEffect, useState } from "react";

const useDelayLoad = (delay: number) => {
  const [loaded, setLoaded] = useState(false);
  useEffect(() => {
    setTimeout(() => {
      setLoaded(true);
    }, delay || 100);
  }, []);

  return loaded;
};

export default useDelayLoad;
