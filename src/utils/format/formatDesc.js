export default function formatDesc(descHash, params = []) {
  // 使用正则表达式匹配 #数字[i] 或 #数字[f1]% 的格式
  const regex = /(#\d+\[i\]%?)|(#\d+\[f[12]\]%?)/g;

  // 替换 descHash 中的每个匹配项
  return descHash.replace(regex, (match) => {

    // 获取占位符中的数字（索引）
    const index = parseInt(match.match(/\d+/)[0]) - 1;
    const param = params[index];

    // 根据占位符的类型进行替换
    if (match.includes("[i]%") || match.includes("[f1]%") || match.includes("[f2]%")) {
      // 直接替换为数值（四舍五入到三位小数）
      return `${Math.round((param * 100) * 100) / 100}%`;
    } else if (match.includes("[i]") || match.includes("[f1]") || match.includes("[f2]")) {
      // 替换为百分比形式（四舍五入到一位小数）
      return param
    }

    // 如果匹配项不符合已知格式，保持原样
    return match;
  });
}
