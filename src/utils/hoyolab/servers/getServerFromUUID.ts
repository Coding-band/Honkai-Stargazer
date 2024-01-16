export default function getServerFromUUID(uuid: string) {
  if (!uuid) return;
  if (uuid.startsWith("1")) return "cn1";
  else if (uuid.startsWith("5")) return "cn2";
  else if (uuid.startsWith("8")) return "asia";
  else if (uuid.startsWith("7")) return "europe";
  else if (uuid.startsWith("6")) return "america";
  else if (uuid.startsWith("9")) return "twhkmo";
  else return;
}
