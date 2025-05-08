import { WebPlugin } from "@capacitor/core";
import type { NavigationBarPlugin } from "./definitions";

export class NavigationBarWeb extends WebPlugin implements NavigationBarPlugin {
  async setNavigationBarColor(options: { color: string; darkButtons?: boolean }): Promise<void> {
    console.log("Cannot setNavigationBarColor on web", options);
    return;
  }
  async getNavigationBarColor(): Promise<{
    color: string;
    darkButtons: boolean;
  }> {
    console.log("Cannot getNavigationBarColor on web");
    return { color: "#000000", darkButtons: true };
  }
}
