export default function getTotalScoreRange(score: number) {
  if (score < 40) return "D";
  else if (score < 80) return "C";
  else if (score < 100) return "B";
  else if (score < 150) return "A";
  else if (score < 200) return "S";
  else if (score >= 200) return "SS";
  else return "D";
}
