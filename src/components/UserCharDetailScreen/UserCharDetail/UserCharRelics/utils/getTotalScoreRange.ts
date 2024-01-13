export default function getTotalScoreRange(score: number) {
  if (score < 20) return "D";
  else if (score < 40 && score > 20) return "C";
  else if (score < 60 && score > 40) return "B";
  else if (score < 80 && score > 60) return "A";
  else if (score < 100 && score > 80) return "S";
  else if (score > 80) return "SS";
  else return "D";
}
