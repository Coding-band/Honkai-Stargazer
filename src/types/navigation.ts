// const route = useRoute<RouteProp<ParamList, "Character">>();

export type ParamList = {
  Character: {
    id: string;
    name: string;
  };
  Lightcone: { id: string; name: string };
  Relic: { id: string; name: string };
};
