import * as Notifications from "expo-notifications";

export default function Noti({
  title,
  body,
  data,
  seconds,
}: {
  title: string;
  body: string;
  data?: string;
  seconds: number;
}) {
  Notifications.scheduleNotificationAsync({
    content: {
      title: title,
      body: body,
      data: { data: data },
    },
    trigger: { seconds: seconds },
  });
}
