import axios, { AxiosError, AxiosRequestConfig } from "axios";
import { LanguageEnum } from "../language/language.interface";
import {
  IResponse,
  RequestBodyType,
  RequestHeaderType,
  RequestParamType,
} from "./Request.interface";
import { hsrPlatform } from "../servers/hsrServer.types";
import generateDS from "../ds/generateDs";
import generateDsV2 from "../ds/generateDsV2";

type DsType = "v1" | "v2";

export default class Request {
  /*
   * Dynamic Security types
   */
  private dsType: DsType;
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

  constructor(
    cookies: string | null = null,
    lang: LanguageEnum = LanguageEnum.TRADIIONAL_CHINESE,
    dsType: DsType = "v1"
  ) {
    this.headers = {
      "Content-Type": "application/json",
      "User-Agent":
        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36",
      "x-rpc-client_type": "5",
      "x-rpc-device_fp": "38d7f38577590",
    };
    this.body = {};
    this.params = {};
    this.dsType = dsType;
    this.setLang(lang);
    if (cookies) this.headers.Cookie = cookies;
  }

  /**
   * Set Referer Headers
   */
  public setReferer(url: string): Request {
    this.headers.Referer = url;
    this.headers.Origin = url;

    return this;
  }

  public setHeaders(headers: RequestHeaderType): Request {
    this.headers = { ...this.headers, ...headers };

    return this;
  }

  /**
   * Set Body Parameter
   */
  public setBody(body: RequestBodyType): Request {
    this.body = { ...this.body, ...body };

    return this;
  }

  /**
   * Sets search parameters or query parameter.
   */
  public setParams(params: RequestParamType): Request {
    this.params = { ...this.params, ...params };

    return this;
  }

  /**
   * Set to used Dynamic Security Types
   */
  public setDs(dstype: DsType): Request {
    this.dsType = dstype;
    return this;
  }

  /**
   * Set Language
   */
  public setLang(
    lang: LanguageEnum = LanguageEnum.TRADIIONAL_CHINESE
  ): Request {
    this.headers["x-rpc-language"] = lang;

    return this;
  }

  /**
   * Send the HTTP request.
   */
  public async send(
    url: string,
    method: "GET" | "POST" = "GET"
  ): Promise<IResponse> {
    const config: AxiosRequestConfig = {
      method,
      params: this.params,
      headers: this.headers as object,
      responseType: "json",
    };

    if (method === "POST") {
      config.data = this.body;
    }

    this.headers.DS =
      this.dsType === "v1"
        ? generateDS()
        : generateDsV2(JSON.stringify(this.body), url.split("?")[1]);

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
        message: String(error),
        data: null,
      };
    }
  }
}
