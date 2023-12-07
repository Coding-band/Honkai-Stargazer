import classNames from "classnames";
import { twMerge } from "tailwind-merge";

const cn = (...args: string[]): string => {
  return classNames(twMerge(...args));
};

export { cn };
