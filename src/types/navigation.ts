import { IconProps } from "phosphor-react-native";

// const route = useRoute<RouteProp<ParamList, "Character">>();

export type ParamList = {
  Character: {
    Icon: (props: IconProps) => React.JSX.Element;
  };
};
