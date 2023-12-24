import md5 from "crypto-js/md5";
import hex from "crypto-js/enc-hex";

export type DS = "hoyolab" | "mihoyo";

export default function generateDS(dsType: DS): string {
  let ds = "";
  if (dsType === "hoyolab") {
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

    ds = `${time},${random},${hash}`;
  } else if (dsType === "mihoyo") {
    const salt = "xV8v4Qu54lUKrEYFZkJhB8cuOh9Asafs";
    // body和query一般来说不会同时存在
    // 可以使用内置的JSON.stringify函数将对象或数组转换为JSON字符串
    // const body = JSON.stringify({role: "123456789"})
    const body = '{"role": "123456789"}';
    // 需要对URL参数进行排序
    const query = "server=cn_gf01&role_id=123456789"
      .split("&")
      .sort()
      .join("&");

    const t = Math.floor(Date.now() / 1000);
    let r = Math.floor(Math.random() * 100001 + 100000);
    if (r == 100000) {
      r = 642367;
    }
    // const r = Math.floor(Math.random() * 100001 + 100001)

    const main = `salt=${salt}&t=${t}&r=${r}&b=${body}&q=${query}`;
    const hash = hex.stringify(md5(main));

    ds = `${t},${r},${hash}`; // 最终结果
  }
  return ds;
}
