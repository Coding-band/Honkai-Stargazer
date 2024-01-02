export default interface UserCharacters {
  count: number;
  characters: {
    id: number;
    level: number;
    rank: number;
  }[];
}
