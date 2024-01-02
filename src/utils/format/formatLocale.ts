export default function formatLocale(localeString: string, params: any[] = []) {
  return localeString.replace("${1}", params[0]);
}
