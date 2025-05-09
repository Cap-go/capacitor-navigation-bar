export enum NavigationBarColor {
  WHITE = "#FFFFFF",
  BLACK = "#000000",
  TRANSPARENT = "transparent",
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
}
