export default interface UserCharacters {
  char_num: number;
  characters: {
    id: number;
    level: number;
    rank: number;
  }[];
}
