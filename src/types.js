export type ColorScheme = 'light' | 'dark' | 'no-preference'
export type AppearancePreferences = {
  colorScheme: ColorScheme
}
export type AppearanceListener = (preferences: AppearancePreferences) => void
