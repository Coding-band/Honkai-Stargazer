import dayjs from "dayjs";
import "dayjs/locale/zh-tw"; // 導入所需的語言包

dayjs.locale("zh-tw"); // 設置本地化語言

export function formatTimeDuration(second: number) {

  console.log(second)

  const formattedTime = dayjs()
    .startOf("day")
    .add(second, "second")
    .format(`H小時mm分鐘ss秒`);

  return formattedTime;
}

export function formatTimePoint(second: number) {
  // 取得當前時間
  const now = dayjs();

  // 從現在開始加上 38667 秒
  const timeAtZero = now.add(second, "second");

  let localizedTime;
  if (timeAtZero.isSame(now, "day")) {
    // 如果是今天，格式化為 "今天 HH:mm"
    localizedTime = `今天${timeAtZero.format("HH:mm")}`;
  } else if (timeAtZero.isSame(now.add(1, "day"), "day")) {
    // 如果是明天，格式化為 "明天 HH:mm"
    localizedTime = `明天${timeAtZero.format("HH:mm")}`;
  } else {
    // 如果不是今天或明天，使用一般的日期格式
    localizedTime = timeAtZero.format("YYYY年MM月DD日 HH:mm");
  }

  return localizedTime;
}
