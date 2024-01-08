import db from "../db";

export async function hasUserByUsername(username: string) {
  return (
    (await db.Users.where("name", "==", username).count().get()).data().count >=
    1
  );
}
