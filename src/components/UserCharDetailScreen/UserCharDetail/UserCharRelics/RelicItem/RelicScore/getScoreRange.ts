export default function getScoreRange(score: number) {
  // 10,20,30,40,50 (0,+10,+10,+10,+10)
  // 6,14,22,36,45 (0,+8,+8,+14,+9)
  // 6,10,14,20,28,36,45 (0,+4,+4,+6,+8,+8,+9) -> Using
  //if (score < 6) return "E";
  if (score < 10) return "D";
  if (score < 14) return "C";
  if (score < 20) return "B";
  if (score < 28) return "A";
  if (score < 36) return "S";
  if (score < 45) return "SS";
  //if (score >= 45) return "Voc";
  return "D";
}
