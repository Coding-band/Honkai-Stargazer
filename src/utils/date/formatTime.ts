import dayjs from "dayjs";
import "dayjs/locale/zh-tw"; // 導入所需的語言包

dayjs.locale("zh-tw"); // 設置本地化語言

export default function formatTime(second: number) {
  const formattedTime = dayjs()
    .startOf("day")
    .add(second, "second")
    .format(`H小時mm分鐘ss秒`);

  return formattedTime;
}
