import { View, Text, Dimensions } from "react-native";
import React, { useState } from "react";
import WebView from "react-native-webview";

export default function EventWebView({
  title,
  content,
}: {
  title: string;
  content: string;
}) {
  const [webviewHeight, setWebviewHeight] = useState(0);
  return (
    <WebView
      automaticallyAdjustContentInsets={false}
      scrollEnabled={false}
      onMessage={(event: any) => {
        setWebviewHeight(parseInt(event.nativeEvent.data) / 2.7);
      }}
      javaScriptEnabled={true}
      injectedJavaScript={`
            setTimeout(function() {window.ReactNativeWebView.postMessage(document.body.scrollHeight) }, 500);`}
      domStorageEnabled={true}
      className="bg-transparent"
      style={{
        width: Dimensions.get("window").width - 32,
        height: webviewHeight,
      }}
      source={{
        html: `
                  <style>
                    * {
                        margin: 0;
                        padding: 0;
                        color: white;
                        font-size: 48px;
                      }
                      .title {
                        font-size: 150%;
                        text-align: center;
                        margin-bottom:32px;
                      
                      }
                      h1 {
                        margin-bottom: 16px;
                      }
                      img {
                        width:100%;
                      } 
                  </style>
                  ${`<h1 class="title">${title}</h1>`}
                  ${content}
                `,
      }}
    ></WebView>
  );
}
