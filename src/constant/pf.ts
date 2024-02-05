import PFDataMap from "../../map/pure_fiction_data_map";
import { TextLanguage } from "../language/language.types";

export const PFVersion = (lang: TextLanguage) => [
  {
    id: 2002,
    name: `${PFDataMap[2002].name[lang]}`,
  },
  {
    id: 2001,
    name: `${PFDataMap[2001].name[lang]}`,
  },
];
