export interface NavigationBarPlugin {
  setNavigationBarColor(options: { color: string }): Promise<void>;
  getNavigationBarColor(): Promise<{ color: string }>;
}
