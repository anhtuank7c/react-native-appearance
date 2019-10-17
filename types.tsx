export type ColorScheme = 'light' | 'light' | 'no-preference'
export interface AppearancePreferences {
    colorScheme: ColorScheme
}
export type AppearanceListener = (preferences: AppearancePreferences) => void