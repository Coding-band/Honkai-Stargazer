import { View, Text, Dimensions, Platform } from "react-native";
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
      bounces={false}
      automaticallyAdjustContentInsets={false}
      scrollEnabled={false}
      onMessage={(event: any) => {
        setWebviewHeight(parseInt(event.nativeEvent.data));
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
<meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0" />
<style>
  * {
    margin: 0;
    padding: 0;
  }
  body {
    font-family: "Gill Sans", sans-serif;
    font-weight: 600;
    color: #ddd;
  }
  .title {
    color: white;
    font-size: 150%;
    font-weight: bold;
    text-align: center;
    margin-bottom: 32px;
  }
  strong{
    color: #DD8200;
  }
  h1 {
    font-size: 125%;
    color: #e9ba79;
    margin-bottom: 8px;
  }
  img {
    width: 100%;
    margin-top: 20px;
    margin-bottom: 20px;
  }
  
table{
   font-weight: 600;
}

td{
  background-color:#f3f3f3;
  color:  rgb(157, 133, 99);
  width:25vw;
  height:50px;
  text-align:center;
}

tr{
  border-bottom: 1px solid #dddddd;
}

tr:last-of-type{
  border-bottom: 2px solid #009879;
}

tr:nth-of-type(1) td{
  background-color:#d0d0d0;
} 

</style>
<body>
  ${`<div class="title">${title}</div>`}
  ${content
    ?.replaceAll('&lt;t class="t_lc"&gt;', "")
    ?.replaceAll('&lt;t class="t_gl"&gt;', "")
    ?.replaceAll("&lt;/t&gt;", "")}
</body>
`,
      }}
    />
  );
}
