import { IconProps } from "phosphor-react-native";

// const route = useRoute<RouteProp<ParamList, "Character">>();

export type ParamList = {
  CharacterList: {
    Icon: (props: IconProps) => React.JSX.Element;
  };
  Character: {
    id: string;
  };
};
