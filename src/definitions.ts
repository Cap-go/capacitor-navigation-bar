export enum NavigationBarButtonStyle {
  LIGHT = '#FFFFFF',
  DARK = '#000000',
}

export enum NavigationBarColor {
  WHITE = '#FFFFFF',
  BLACK = '#000000',
  TRANSPARENT = 'transparent',
}

export interface NavigationBarPlugin {
  setNavigationBarColor(options: {
    color: NavigationBarColor | string; // Predefined colors or any valid hex
    buttonStyle?: NavigationBarButtonStyle; // Button color theme
  }): Promise<void>;
  getNavigationBarColor(): Promise<{
    color: string;
    buttonStyle?: NavigationBarButtonStyle;
  }>;
}
