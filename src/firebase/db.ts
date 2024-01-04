import firestore from "@react-native-firebase/firestore";

// const converter = <T>() => ({
//   toFirestore: (data: T) => data,
//   fromFirestore: (snap: FirebaseFirestoreTypes.QueryDocumentSnapshot) =>
//     snap.data() as T,
// });

// const dataPoint = <T>(collectionPath: string) =>
//   firestore().collection(collectionPath);

const db = {
  Users: firestore().collection("Users"),
  UserCharacters: firestore().collection("UserCharacters"),
  UserMemoryOfChaos: firestore().collection("UserMemoryOfChaos"),
  CharacterComments: firestore().collection("CharacterComments"),
};

export default db;
