
export const extractMentionsRegexp = /(@[^ ]+(?=\s|$))/gu;

export  function extractMentionsMatch(message: string) {

  let matches = message.match(extractMentionsRegexp)?.map((m) => m.trim());
  return matches || [];
}

export function extractMentionsSplit(message: string){
    return message.split(extractMentionsRegexp);
}