import MOCDataMap from "../../map/memory_of_chao_data_map";
import { TextLanguage } from "../language/language.types";

export const MocVersion = (lang: TextLanguage) => [
  {
    id: 1012,
    name: `${MOCDataMap[1012].time.versionBegin} - ${MOCDataMap[1012].time.versionEnd} ${MOCDataMap[1012].name[lang]}`,
    startBegin: MOCDataMap[1012].time.begin,
  },
  {
    id: 1011,
    name: `${MOCDataMap[1011].time.versionBegin} - ${MOCDataMap[1011].time.versionEnd} ${MOCDataMap[1011].name[lang]}`,
    startBegin: MOCDataMap[1011].time.begin,
  },
  {
    id: 1010,
    name: `${MOCDataMap[1010].time.versionBegin} - ${MOCDataMap[1010].time.versionEnd} ${MOCDataMap[1010].name[lang]}`,
    startBegin: MOCDataMap[1010].time.begin,
  },
  {
    id: 1009,
    name: `${MOCDataMap[1009].time.versionBegin} - ${MOCDataMap[1009].time.versionEnd} ${MOCDataMap[1009].name[lang]}`,
    startBegin: MOCDataMap[1009].time.begin,
  },
  {
    id: 1008,
    name: `${MOCDataMap[1008].time.versionBegin} - ${MOCDataMap[1008].time.versionEnd} ${MOCDataMap[1008].name[lang]}`,
    startBegin: MOCDataMap[1008].time.begin,
  },
];
