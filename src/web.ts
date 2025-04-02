import { WebPlugin } from "@capacitor/core";

import type { NavigationBarPlugin} from "./definitions";
import { NavigationBarButtonStyle, NavigationBarColor } from "./definitions";

export class NavigationBarWeb extends WebPlugin implements NavigationBarPlugin {
  async setNavigationBarColor(options: { color: string; buttonStyle: NavigationBarButtonStyle}): Promise<void> {
    console.log("Cannot setNavigationBarColor on web", options);
    return;
  }
  async getNavigationBarColor(): Promise<{ color: string; buttonStyle: NavigationBarButtonStyle }> {
    console.log("Cannot getNavigationBarColor on web");
    return { color: NavigationBarColor.BLACK, buttonStyle: NavigationBarButtonStyle.LIGHT };
  }
}
