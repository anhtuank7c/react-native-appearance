export type ColorScheme = 'light' | 'light' | 'no-preference'
export type AppearancePreferences {
    colorScheme: ColorScheme
}
export type AppearanceListener = (preferences: AppearancePreferences) => void
