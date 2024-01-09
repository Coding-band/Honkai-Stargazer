import BlurView from "../components/global/BlurView/BlurView";
import { useEffect, useState } from "react";

const withBlurView = (jsx: React.ReactNode, active: boolean = true) => {
  const [intensity, setIntensity] = useState(0);
  useEffect(() => {
    setTimeout(() => {
      setIntensity(20);
    }, 500);
  }, []);

  return active ? (
    <BlurView intensity={intensity} tint="light">
      {jsx}
    </BlurView>
  ) : (
    jsx
  );
};

export default withBlurView;
