import { BlurView } from "expo-blur";

const withBlurView = (jsx: React.ReactNode, active: boolean) => {
  return active ? (
    <BlurView intensity={20} tint="light">
      {jsx}
    </BlurView>
  ) : (
    jsx
  );
};

export default withBlurView