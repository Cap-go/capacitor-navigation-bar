export enum NavigationBarColor {
  WHITE = '#FFFFFF',
  BLACK = '#000000',
  TRANSPARENT = 'transparent',
}

export interface NavigationBarPlugin {
  setNavigationBarColor(options: {
    color: NavigationBarColor | string; // Predefined colors or any valid hex
    darkButtons?: boolean; // Set to true when not specified
  }): Promise<void>;
  getNavigationBarColor(): Promise<{
    color: string;
    darkButtons: boolean;
  }>;

  /**
   * Get the native Capacitor plugin version
   *
   * @returns {Promise<{ id: string }>} an Promise with version for this device
   * @throws An error if the something went wrong
   */
  getPluginVersion(): Promise<{ version: string }>;
}
