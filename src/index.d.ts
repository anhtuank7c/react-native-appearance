
declare module "react-native-appearance" {
  import * as React from "react";

  export type ColorScheme = "light" | "dark" | "no-preference";

  export interface AppearancePreferences {
    colorScheme: ColorScheme
  }

  export class AppearanceProvider extends React.PureComponent<any, any> {}

  export type useColorScheme = Function
}
