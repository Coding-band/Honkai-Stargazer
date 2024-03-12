import axios, { AxiosError, AxiosRequestConfig } from "axios";

/**
 * Represents the interface for a response from the server.
 */
export interface IResponse {
  /**
   * The status code of the response.
   */
  retcode: number;

  /**
   * A message associated with the response.
   */
  message: string;

  /**
   * The data returned by the server.
   */
  data: any;
}

export default class GachaRequest {
  /**
   * Send the HTTP request.
   */
  public async send(
    url: string,
    method: "GET" | "POST" = "GET"
  ): Promise<IResponse> {
    const config: AxiosRequestConfig = {
      method,
      responseType: "json",
      withCredentials:false
    };

    try {
      const request = await axios(url, config);

      const result = request.data as IResponse;

      if ([200, 201].includes(request.status) === false) {
        throw new AxiosError(
          request.statusText ?? result.data,
          request.status.toString()
        );
      }

      return result;
    } catch (error) {
      return {
        retcode: -9999,
        message: String(error),
        data: null,
      };
    }
  }
}
