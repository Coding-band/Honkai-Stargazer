import md5 from "crypto-js/md5";
import hex from "crypto-js/enc-hex";

export default function generateDS(): string {
  const salt = "6s25p5ox5y14umn1p61aqyyvbvvl3lrt";
  const date = new Date();
  const time = Math.floor(date.getTime() / 1000);

  let random = "";
  const characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
  for (let i = 0; i < 6; i++) {
    const randomIndex = Math.floor(Math.random() * characters.length);
    const randomChar = characters.charAt(randomIndex);
    random += randomChar;
  }

  const hash = hex.stringify(md5(`salt=${salt}&t=${time}&r=${random}`));

  return `${time},${random},${hash}`;
}
