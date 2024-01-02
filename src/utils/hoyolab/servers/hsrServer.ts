import { hsrServerId, hsrServer as hsrServerType } from "./hsrServer.types";

export const hsrServer: { [serverId in hsrServerId]: hsrServerType } = {
  asia: "prod_official_asia",
  europe: "prod_official_eur",
  america: "prod_official_usa",
  twhkmo: "prod_official_cht",
  cn1: "prod_gf_cn",
  cn2: "prod_qd_cn",
};
