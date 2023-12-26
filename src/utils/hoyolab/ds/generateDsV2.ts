import md5 from "crypto-js/md5";
import hex from "crypto-js/enc-hex";

export default function generateDsV2(body: string = "", query: string = "") {
  const salt = "xV8v4Qu54lUKrEYFZkJhB8cuOh9Asafs";

  const _body = body === "{}" || !body ? "" : body;
  const _query = query.split("&").sort().join("&");

  const t = Math.floor(Date.now() / 1000);
  let r = Math.floor(Math.random() * 100001 + 100000);
  if (r == 100000) {
    r = 642367;
  }

  const main = `salt=${salt}&t=${t}&r=${r}&b=${_body}&q=${_query}`;
  const hash = hex.stringify(md5(main));

  return `${t},${r},${hash}`; // 最终结果
}
