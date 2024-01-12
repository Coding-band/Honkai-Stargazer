// redux

import { createStore, applyMiddleware, combineReducers } from "redux";
import { persistStore, persistReducer } from "redux-persist";
import AsyncStorage from "@react-native-async-storage/async-storage";

// reducers

import { hsrServerChosen } from "./hsrServerChosen/hsrServerChosen.reducer";
import { hoyolabCookie } from "./hoyolabCookie/hoyolabCookie.reducer";
import { isAcceptBindingPolicy } from "./isAcceptBindingPolicy/isAcceptBindingPolicy.reducer";
import { characterSorting } from "./characterSorting/characterSorting.reducer";
import { characterSortingReverse } from "./characterSortingReverse/characterSortingReverse.reducer";
import { characterFilter } from "./characterFilter/characterFilter.reducer";
import { lightconeSorting } from "./lightconeSorting/lightconeSorting.reducer";
import { lightconeSortingReverse } from "./lightconeSortingReverse/lightconeSortingReverse.reducer";
import { lightconeFilter } from "./lightconeFilter/lightconeFilter.reducer";
import { relicSearch } from "./relicSearch/relicSearch.reducer";
import { relicIsSearching } from "./relicIsSearching/relicIsSearching.reducer";
import { wallPaper } from "./wallPaper/wallPaper.reducer";
import { characterSearch } from "./characterSearch/characterSearch.reducer";
import { characterIsSearching } from "./characterIsSearching/characterIsSearching.reducer";
import { lightconeSearch } from "./lightconeSearch/lightconeSearch.reducer";
import { lightconeIsSearching } from "./lightconeIsSearching/lightconeIsSearching.reducer";
import { doUseCustomFont } from "./doUseCustomFont/doUseCustomFont.reducer";

const reducer = combineReducers({
  hsrServerChosen,
  hoyolabCookie,
  isAcceptBindingPolicy,
  characterSorting,
  characterSortingReverse,
  characterFilter,
  lightconeSorting,
  lightconeSortingReverse,
  lightconeFilter,
  relicSearch,
  relicIsSearching,
  wallPaper,
  characterSearch,
  characterIsSearching,
  lightconeSearch,
  lightconeIsSearching,
  doUseCustomFont,
});

// persist
const persistConfig = {
  key: "root",
  storage: AsyncStorage,
  whitelist: [
    "hsrServerChosen",
    "hoyolabCookie",
    "isAcceptBindingPolicy",
    // "characterSorting",
    // "characterSortingReverse",
    // "lightconeSorting",
    // "lightconeSortingReverse",
    "wallPaper",
    "doUseCustomFont",
  ],
};

// @ts-ignore
const persistedReducer = persistReducer(persistConfig, reducer);

// middlewares
const middlewares: any[] = [];

// store
const store = createStore(persistedReducer, applyMiddleware(...middlewares));

export type RootState = ReturnType<typeof store.getState>;

const persistor = persistStore(store);

export { store, persistor };
