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
import { AppLanguage } from "../types/language";
import { LOCALES } from "../../locales";

export const SCREENS = {
  HomePage: {
    id: "Home",
    getName: (lang: AppLanguage) => "",
    getShortName: (lang: AppLanguage) => "",
    icon: null,
  },
  CharacterListPage: {
    id: "CharacterList",
    getName: (lang: AppLanguage) => LOCALES[lang].CharacterList,
    getShortName: (lang: AppLanguage) => LOCALES[lang].Character,
    icon: Person,
  },
  CharacterPage: {
    id: "Character",
    getName: (lang: AppLanguage) => LOCALES[lang].Character,
    getShortName: (lang: AppLanguage) => LOCALES[lang].Character,
    icon: Person,
  },
  LightconeListPage: {
    id: "LightconeList",
    getName: (lang: AppLanguage) => LOCALES[lang].LightconeList,
    getShortName: (lang: AppLanguage) => LOCALES[lang].Lightcone,
    icon: Sword,
  },
  LightconePage: {
    id: "Lightcone",
    getName: (lang: AppLanguage) => LOCALES[lang].Lightcone,
    getShortName: (lang: AppLanguage) => LOCALES[lang].Lightcone,
    icon: Sword,
  },
  RelicListPage: {
    id: "RelicList",
    getName: (lang: AppLanguage) => LOCALES[lang].RelicList,
    getShortName: (lang: AppLanguage) => LOCALES[lang].Relic,
    icon: BaseballCap,
  },
  RelicPage: {
    id: "Relic",
    getName: (lang: AppLanguage) => LOCALES[lang].Relic,
    getShortName: (lang: AppLanguage) => LOCALES[lang].Relic,
    icon: BaseballCap,
  },
  MapPage: {
    id: "Map",
    getName: (lang: AppLanguage) => LOCALES[lang].Map,
    getShortName: (lang: AppLanguage) => LOCALES[lang].Map,
    icon: MapTrifold,
  },
  LoginPage: {
    id: "Login",
    getName: (lang: AppLanguage) => LOCALES[lang].Login,
    getShortName: (lang: AppLanguage) => LOCALES[lang].Login,
    icon: User,
  },
  ExpeditionPage: {
    id: "Expedition",
    getName: (lang: AppLanguage) => LOCALES[lang].NotiExpedition,
    getShortName: (lang: AppLanguage) => LOCALES[lang].NotiExpedition,
    icon: Users,
  },
  SettingPage: {
    id: "Setting",
    getName: (lang: AppLanguage) => LOCALES[lang].Setting,
    getShortName: (lang: AppLanguage) => LOCALES[lang].Setting,
    icon: SlidersHorizontal,
  },
  WallPaperPage: {
    id: "WallPaper",
    getName: (lang: AppLanguage) => LOCALES[lang].WallPaper,
    getShortName: (lang: AppLanguage) => LOCALES[lang].ChangeWallPaper,
    icon: SlidersHorizontal,
  },
  MemoryOfChaosPage: {
    id: "MemoryOfChaos",
    getName: (lang: AppLanguage) => LOCALES[lang].MemoryOfChaos,
    getShortName: (lang: AppLanguage) => LOCALES[lang].MemoryOfChaos,
    icon: MedalMilitary,
  },
};
