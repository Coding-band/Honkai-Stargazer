export default function formatDesc(descHash: string, params: number[]): string {
  // 替换 descHash 中的占位符
  params.forEach((param, index) => {
    descHash = descHash.replace(`#${index + 1}[i]`, param.toString());
  });

  return descHash;
}
