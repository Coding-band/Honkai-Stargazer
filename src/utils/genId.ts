import { customAlphabet } from "nanoid/non-secure";

export default (size?: number) => {
  return customAlphabet(
    "abcdefghijklmnopqrstuvwxyzABCDEFGHIGKLMNOPQUSTUVWXYZ0123456789",
    size
  )();
};
