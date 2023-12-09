import { getCharAttrData } from "../getAttrData";
import { getLcAttrData } from "../getAttrData";
import { getCharMaterialData } from "../getMaterialData";
import { getLcMaterialData } from "../getMaterialData";

playground();

function playground() {
    let a = getCharAttrData()
    let b = getLcAttrData();
    let c = getCharMaterialData();
    let d = getLcMaterialData();

    console.log({ a, b, c, d })
}