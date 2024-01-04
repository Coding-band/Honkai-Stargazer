export default interface CharacterComments {
  comment_num: number;
  comments: {
    user_id: string;
    content: string;
  }[];
}
