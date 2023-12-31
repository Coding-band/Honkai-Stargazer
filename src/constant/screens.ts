import {
  BaseballCap,
  MapTrifold,
  MedalMilitary,
  Person,
  SlidersHorizontal,
  Sword,
  User,
  Users,
} from "phosphor-react-native";

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
    shortName: "光锥",
    icon: Sword,
  },
  RelicListPage: {
    id: "RelicList",
    name: "遺器列表",
    shortName: "遺器",
    icon: BaseballCap,
  },
  RelicPage: {
    id: "Relic",
    name: "遺器",
    shortName: "遺器",
    icon: BaseballCap,
  },
  MapPage: {
    id: "Map",
    name: "Hoyolab 地圖",
    shortName: "地圖",
    icon: MapTrifold,
  },
  LoginPage: {
    id: "Login",
    name: "登入",
    shortName: "",
    icon: User,
  },
  ExpeditionPage: {
    id: "Expedition",
    name: "派遣委托",
    shortName: "派遣委托",
    icon: Users,
  },
  SettingPage: {
    id: "Setting",
    name: "设置",
    shortName: "设置",
    icon: SlidersHorizontal,
  },
  WallPaperPage: {
    id: "WallPaper",
    name: "更换壁纸",
    shortName: "壁纸",
    icon: SlidersHorizontal,
  },
  MemoryOfChaosPage: {
    id: "MemoryOfChaos",
    name: "混沌回憶",
    shortName: "混沌回憶",
    icon: MedalMilitary,
  },
};
