import { hsrServerId } from "./servers/hsrServer.types";

export function isHoyolabPlatform(serverId: hsrServerId) {
  if (serverId === "cn1" || serverId === "cn2") return false;
  else return true;
}
