export default function getRankColor(rank: number) {
  const colors = {
    1: "#FFD070",
    2: "#F3F9FF80",
    3: "#AB6F66",
  };
  // @ts-ignore
  return colors[rank] || "#FFFFFF";
}
