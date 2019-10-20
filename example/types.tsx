export type ColorScheme = 'light' | 'dark' | 'no-preference'
export interface AppearancePreferences {
  colorScheme: ColorScheme
}
export type AppearanceListener = (preferences: AppearancePreferences) => void