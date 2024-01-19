export default function formatLocale(localeString: string, params: any[] = []) {
  for(let index = 0 ; index < params.length ; index++){
    localeString = localeString.replace("${"+index+"}", params[index]);
  }
  return localeString;
}
