import { useEffect, useState, } from "react";
import AsyncStorage from "@react-native-async-storage/async-storage";
import axios, { AxiosError, AxiosRequestConfig } from "axios";

const CHAR_WEIGHT_LIST_UPDATE_TIME_KEY = "char-weight-list-update-unix4"
const CHAR_WEIGHT_LIST_JSON_KEY = "char-weight-list-json"

async function checkCharWeightListNew(){
  console.log("Voc2048.com")
  const request = await axios(
    "https://voc2048.com/stargazer/charWeightList.json",
    { timeoutErrorMessage: "{downloadTokens: \"-3001\", retcode:-3001, message:\"voc2048.com CharWeightList Timeout La\"}" }
  )

  const result = request?.data;
  if (result.retcode === -3001) {
    return false
  } else if ([200, 201].includes(request.status) === false) {
    return false
  }else{
    const chunksParser = (body : string) => {
            return body
              .replace(/^(\w{1,3})\r\n/, "") // replace header chunks info 
              .replace(/\r\n(\w{1,3})\r\n/, "") // replace in-body chunks info
              .replace(/(\r\n0\r\n\r\n)$/, ""); // replace end chunks info
          };
          
        //console.log(data?.data)
        //儲存最新版本的檔案
        const reducedData = chunksParser(JSON.stringify(result))
        if (result !== null && isJsonString(reducedData)) {
          AsyncStorage.setItem(CHAR_WEIGHT_LIST_JSON_KEY, reducedData);
          return true
        }
        return false
  }

}

async function checkCharWeightList() {
  if(await checkCharWeightListNew()){
    return 
  }
  const request = await axios(
    "https://firebasestorage.googleapis.com/v0/b/honkai-stargazer-bf382.appspot.com/o/static_data%2FcharWeightList.json",
    { timeoutErrorMessage: "{downloadTokens: \"-3001\", retcode:-3001, message:\"Firebase CharWeightList Timeout La\"}" }
  )

  const result = request?.data;
  if (result.retcode === -3001) {
    throw new AxiosError(
      "Firebase CharWeightList Timeout La",
      request.status.toString()
    );
  } else if ([200, 201].includes(request.status) === false) {
    throw new AxiosError(
      request.statusText ?? result.data,
      request.status.toString()
    );
  }

  const fileUpdateTimeFromFirebase = request?.data?.updated
  const fileToken = request?.data?.downloadTokens

  AsyncStorage.getItem(CHAR_WEIGHT_LIST_UPDATE_TIME_KEY).then(async (dataGet) => {
    //獲取上次更新的時間
    if ((dataGet === null || dataGet !== fileUpdateTimeFromFirebase) && fileToken) {
      const updateTime = dataGet;
      //申請獲取Firebase版本的檔案
      await axios(
        "https://firebasestorage.googleapis.com/v0/b/honkai-stargazer-bf382.appspot.com/o/static_data%2FcharWeightList.json?alt=media&token=" + fileToken,
        { timeoutErrorMessage: "{downloadTokens: \"-3002\", retcode:-3002, message:\"Firebase CharWeightList Timeout La2\"}" }
      ).then((data) => {
        //獲取了Firebase版本的檔案
        const result = data?.data;
        if (result.retcode === -3002) {
          throw new AxiosError(
            "Firebase CharWeightList Timeout La2",
            data.status.toString()
          );
        } else if ([200, 201].includes(data.status) === false) {
          throw new AxiosError(
            data.statusText ?? result.data,
            data.status.toString()
          );
        }

        const chunksParser = (body : string) => {
            return body
              .replace(/^(\w{1,3})\r\n/, "") // replace header chunks info 
              .replace(/\r\n(\w{1,3})\r\n/, "") // replace in-body chunks info
              .replace(/(\r\n0\r\n\r\n)$/, ""); // replace end chunks info
          };
          
        //console.log(data?.data)
        //儲存最新版本的檔案
        const reducedData = chunksParser(JSON.stringify(data?.data))
        if (data?.data !== null && isJsonString(reducedData)) {
          AsyncStorage.setItem(CHAR_WEIGHT_LIST_UPDATE_TIME_KEY, updateTime!);
          AsyncStorage.setItem(CHAR_WEIGHT_LIST_JSON_KEY, reducedData);
        }
      })
    }
  });
}
function isJsonString(str: string) {
  try {
    JSON.parse(str);
  } catch (e) {
    console.log("e : "+e)
    return false;
  }
  return true;
}


export default checkCharWeightList