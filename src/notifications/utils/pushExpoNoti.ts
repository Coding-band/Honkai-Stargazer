import axios from "axios";

export default function pushExpoNoti({
  to,
  title,
  body,
  data,
}: {
  to: string;
  title: string;
  body: string;
  data?: any;
}) {
  return axios.post(`https://exp.host/--/api/v2/push/send`, {
    to: to,
    title: title,
    body: body,
    data: data,
  });
}
