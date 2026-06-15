/// <reference types="@capacitor/cli" />

declare module '@capacitor/cli' {
  export interface PluginsConfig {
    NavigationBar?: {
      /**
       * The hexadecimal color to set as the background color of the navigation bar.
       *
       * Use `'transparent'` to make the navigation bar transparent.
       *
       * Only available on Android.
       *
       * @since 8.2.0
       * @example "#ffffff"
       */
      color?: string;
      /**
       * The hexadecimal color to set as the color of the navigation bar divider.
       *
       * Use `'transparent'` to hide the divider.
       *
       * Only available on Android (API level 28+).
       *
       * @since 8.2.0
       * @example "#d9d9d9"
       */
      dividerColor?: string;
      /**
       * The style of the navigation bar buttons.
       *
       * Only available on Android.
       *
       * @since 8.2.0
       * @example "LIGHT"
       */
      style?: 'DARK' | 'LIGHT' | 'DEFAULT';
    };
  }
}

/**
 * Predefined navigation bar colors.
 *
 * @since 1.0.0
 */
export enum NavigationBarColor {
  /** White color */
  WHITE = '#FFFFFF',
  /** Black color */
  BLACK = '#000000',
  /** Transparent color */
  TRANSPARENT = 'transparent',
}
/**
 * Capacitor Navigation Bar Plugin for customizing the Android navigation bar.
 *
 * @since 1.0.0
 */
export interface NavigationBarPlugin {
  /**
   * Hide the navigation bar.
   *
   * Only available on Android.
   *
   * @since 8.2.0
   * @example
   * ```typescript
   * await NavigationBar.hide();
   * ```
   */
  hide(): Promise<void>;

  /**
   * Show the navigation bar.
   *
   * Only available on Android.
   *
   * @since 8.2.0
   * @example
   * ```typescript
   * await NavigationBar.show();
   * ```
   */
  show(): Promise<void>;

  /**
   * Set the navigation bar color and button theme.
   *
   * @param options - Configuration for navigation bar appearance
   * @returns Promise that resolves when the color is set
   * @throws Error if setting the color fails
   * @since 1.0.0
   * @example
   * ```typescript
   * // Set to white with dark buttons
   * await NavigationBar.setNavigationBarColor({
   *   color: NavigationBarColor.WHITE,
   *   darkButtons: true
   * });
   *
   * // Set to custom color
   * await NavigationBar.setNavigationBarColor({
   *   color: '#FF5733',
   *   darkButtons: false
   * });
   *
   * // Set a custom divider color on Android 9+
   * await NavigationBar.setNavigationBarColor({
   *   color: NavigationBarColor.WHITE,
   *   darkButtons: true,
   *   dividerColor: '#D9D9D9'
   * });
   * ```
   */
  setNavigationBarColor(options: {
    /** Predefined colors or any valid hex color code */
    color: NavigationBarColor | string;
    /** Whether to use dark buttons. Defaults to true if not specified */
    darkButtons?: boolean;
    /** Divider color for Android 9+ (API 28+). Accepts predefined colors or any valid hex color code */
    dividerColor?: NavigationBarColor | string;
  }): Promise<void>;

  /**
   * Get the current navigation bar color and button theme.
   *
   * @returns Promise that resolves with the current navigation bar configuration
   * @throws Error if getting the color fails
   * @since 1.0.0
   * @example
   * ```typescript
   * const { color, darkButtons } = await NavigationBar.getNavigationBarColor();
   * console.log('Current color:', color);
   * console.log('Using dark buttons:', darkButtons);
   * ```
   */
  getNavigationBarColor(): Promise<{
    /** Current navigation bar color */
    color: string;
    /** Whether dark buttons are enabled */
    darkButtons: boolean;
  }>;

  /**
   * Get the native Capacitor plugin version.
   *
   * @returns Promise that resolves with the plugin version
   * @throws Error if getting the version fails
   * @since 1.0.0
   * @example
   * ```typescript
   * const { version } = await NavigationBar.getPluginVersion();
   * console.log('Plugin version:', version);
   * ```
   */
  getPluginVersion(): Promise<{ version: string }>;
}
