import { View, Text } from "react-native";
import React from "react";
import useIsLogin from "../../../../../hooks/useAcceptBindingPolicy";
import LoginPolicy from "./LoginPolicy/LoginPolicy";
import ServerChosen from "./ServerChosen/ServerChosen";

export default function AccountBinding() {
  return (
    <>
      <LoginPolicy />
      <ServerChosen />
    </>
  );
}
