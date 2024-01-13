import { View, Text } from "react-native";
import React, { useState, useEffect } from "react";
import { extractMentionsSplit } from "../../../utils/extractMetions";
import { hasUserByUsername } from "../../../firebase/utils/hasUser";

export default function TagContent(props: { children: string }) {
  const [processedContent, setProcessedContent] = useState<any[]>([]);
  
  useEffect(() => {
    async function processContent() {
      const parts = extractMentionsSplit(props.children);
      const processedParts = await Promise.all(
        parts.map(async (part, i) => {
          if (await hasUserByUsername(part.slice(1))) {
            return (
              <Text key={i} className="text-[#DD8200]">
                {part}
              </Text>
            );
          } else {
            return <Text key={i}>{part}</Text>;
          }
        })
      );

      setProcessedContent(processedParts);
    }

    processContent();
  }, [props.children]); // 依赖项列表，当这些依赖项更改时，useEffect 会重新执行

  return (
    <Text className="text-text2 text-[14px] font-[HY65] leading-5">
      {processedContent}
    </Text>
  );
}
