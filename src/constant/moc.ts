import MOCDataMap from "../../map/memory_of_chao_data_map";
import { TextLanguage } from "../language/language.types";

export const MocVersion = (lang: TextLanguage) => [
  {
    id: 1009,
    name: `${MOCDataMap[1009].time.versionBegin} - ${MOCDataMap[1009].time.versionEnd} ${MOCDataMap[1009].name[lang]}`,
  },
  {
    id: 1008,
    name: `${MOCDataMap[1008].time.versionBegin} - ${MOCDataMap[1008].time.versionEnd} ${MOCDataMap[1008].name[lang]}`,
  },
];
