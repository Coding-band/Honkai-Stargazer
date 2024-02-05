import PFDataMap from "../../map/pure_fiction_data_map";
import { TextLanguage } from "../language/language.types";

export const PFVersion = (lang: TextLanguage) => [
  {
    id: 2003,
    name: `${PFDataMap[2003].name[lang]}`,
    startBegin: PFDataMap[2003].time.begin,
  },
  {
    id: 2002,
    name: `${PFDataMap[2002].name[lang]}`,
    startBegin: PFDataMap[2002].time.begin,
  },
  {
    id: 2001,
    name: `${PFDataMap[2001].name[lang]}`,
    startBegin: PFDataMap[2001].time.begin,
  },
];
