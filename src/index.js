import React, { useMemo } from 'react'
import { requireNativeComponent, NativeModules, NativeEventEmitter, DeviceEventEmitter, EventSubscription } from 'react-native'
import { useSubscription } from 'use-subscription'
import { AppearancePreferences, ColorScheme, AppearanceListener } from './types'

const RNCAppearance = NativeModules.RNCAppearance
const EventListener = new NativeEventEmitter(RNCAppearance)
const RNCAppearanceProvider = requireNativeComponent('RNCAppearanceProvider')

EventListener.addListener('appearanceChanged', (preference: AppearancePreferences) => {
    Appearance.set(preference)
})

export const AppearanceProvider = (props: { children: any }) => <RNCAppearanceProvider style={{ flex: 1 }} {...props} />
let appearancePreferences: AppearancePreferences = RNCAppearance.initialPreferences

export class Appearance {
    static getColorScheme(): ColorScheme {
        return appearancePreferences.colorScheme;
    }

    static set(preferences: AppearancePreferences): void {
        let { colorScheme } = preferences
        if (appearancePreferences.colorScheme !== colorScheme) {
            appearancePreferences = { colorScheme }
            DeviceEventEmitter.emit('change', preferences)
        }
    }

    static addChangeListener(listener: AppearanceListener): EventSubscription {
        return DeviceEventEmitter.addListener('change', listener)
    }
}

export function useColorScheme(): ColorScheme {
    const subscription = useMemo(
        () => ({
            getCurrentValue: () => Appearance.getColorScheme(),
            subscribe: (callback: AppearanceListener) => {
                let eventSubscription = Appearance.addChangeListener(callback)
                return () => eventSubscription.remove()
            }
        }), []
    )
  
    return useSubscription(subscription)
}
