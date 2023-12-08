import axios, { AxiosError, AxiosRequestConfig } from "axios";
import generateDS from "../generateDs";
import { LanguageEnum } from "../language/language.interface";
import {
  IResponse,
  RequestBodyType,
  RequestHeaderType,
  RequestParamType,
} from "./hoyolabRequest.interface";

export default class HoyolabRequest {
  /*
   * Headers for the request.
   */
  private headers: RequestHeaderType;

  /*
   * Body of the request.
   */
  private body: RequestBodyType;

  /*
   * Query parameters for the request.
   */
  private params: RequestParamType;

  /*
   * Flag indicating whether Dynamic Security is used.
   */
  private ds: boolean;

  /*
   * The number of request attempts made.
   */

  constructor(cookies: string | null = null) {
    this.headers = {
      "Content-Type": "application/json",
      Host: "bbs-api-os.hoyolab.com",
      "User-Agent":
        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36",
      "x-rpc-app_version": "1.5.0",
      "x-rpc-client_type": "5",
    };
    this.body = {};
    this.params = {};
    this.ds = false;
    if (cookies) this.headers.Cookie = cookies;
  }

  /**
   * Set Referer Headers
   *
   * @param url - The URL string of referer
   * @returns The updated Request instance.
   */
  public setReferer(url: string): HoyolabRequest {
    this.headers.Referer = url;
    this.headers.Origin = url;

    return this;
  }

  /**
   * Set Body Parameter
   *
   * @param body - RequestBodyType as object containing the body parameters.
   * @returns This instance of Request object.
   */
  public setBody(body: RequestBodyType): HoyolabRequest {
    this.body = { ...this.body, ...body };

    return this;
  }

  /**
   * Sets search parameters or query parameter.
   *
   * @param params - An object of query parameter to be set.
   * @returns {Request} - Returns this Request object.
   */
  public setParams(params: RequestParamType): HoyolabRequest {
    this.params = { ...this.params, ...params };

    return this;
  }

  /**
   * Set to used Dynamic Security or not
   *
   * @param flag boolean Flag indicating whether to use dynamic security or not (default: true).
   * @returns {this} The current Request instance.
   */
  public setDs(flag = true): HoyolabRequest {
    this.ds = flag;
    return this;
  }

  /**
   * Set Language
   *
   * @param lang Language Language that used for return of API (default: Language.ENGLISH).
   * @returns {this}
   */
  public setLang(lang: LanguageEnum = LanguageEnum.ENGLISH): HoyolabRequest {
    this.headers["x-rpc-language"] = lang;

    return this;
  }

  /**
   * Send the HTTP request.
   *
   * @param url - The URL to send the request to.
   * @param method - The HTTP method to use. Defaults to 'GET'.
   * @param ttl - The TTL value for the cached data in seconds.
   * @returns A Promise that resolves with the response data, or rejects with a HoyolabError if an error occurs.
   * @throws {HoyolabError} if an error occurs rejects with a HoyolabError
   */
  public async send(
    url: string,
    method: "GET" | "POST" = "GET"
  ): Promise<IResponse> {
    if (this.ds) {
      this.headers.DS = generateDS();
    }

    const config: AxiosRequestConfig = {
      method,
      params: this.params,
      headers: this.headers as object,
      responseType: "json",
    };

    if (method === "POST") {
      config.data = this.body;
    }

    try {
      const request = await axios(url, config);

      const result = request.data as IResponse;

      if ([200, 201].includes(request.status) === false) {
        throw new AxiosError(
          request.statusText ?? result.data,
          request.status.toString()
        );
      }

      this.body = {};
      return result;
    } catch (error) {
      return {
        retcode: -9999,
        message: "",
        data: null,
      };
    }
  }
}
