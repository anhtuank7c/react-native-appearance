export type ColorScheme = 'light' | 'light' | 'no-preference'
export interface AppearancePreferences {
  colorScheme: ColorScheme
}
export type AppearanceListener = (preferences: AppearancePreferences) => void
export type Props = {
  children: Any
}
export type AppearanceProvider = (props: Props) => void
