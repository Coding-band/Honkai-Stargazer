export default function getScoreRange(score: number) {
  if (score < 8) return "D";
  else if (score < 16 && score > 8) return "C";
  else if (score < 24 && score > 16) return "B";
  else if (score < 32 && score > 24) return "A";
  else if (score < 40 && score > 32) return "S";
  else if (score > 40) return "SS";
  else return "D";
}
