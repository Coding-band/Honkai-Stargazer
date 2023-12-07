import { Person, Sword } from "phosphor-react-native";

export const SCREENS = {
  HomePage: {
    id: "Home",
    name: "",
    shortName: "",
    icon: null,
  },
  CharacterListPage: {
    id: "CharacterList",
    name: "角色列表",
    shortName: "角色",
    icon: Person,
  },
  CharacterPage: {
    id: "Character",
    name: "角色",
    shortName: "",
    icon: Person,
  },
  LightconeListPage: {
    id: "LightconeList",
    name: "光锥列表",
    shortName: "光锥",
    icon: Sword,
  },
  LightconePage: {
    id: "Lightcone",
    name: "光锥",
    shortName: "",
    icon: Sword,
  },
  LoginPage: {
    id: "Login",
    name: "",
    shortName: "",
    icon: null,
  },
};
