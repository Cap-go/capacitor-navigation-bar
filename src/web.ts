import { WebPlugin } from '@capacitor/core';

import { NavigationBarButtonStyle, NavigationBarPlugin } from './definitions';

export class NavigationBarWeb extends WebPlugin implements NavigationBarPlugin {
  async setNavigationBarColor(options: { color: string }): Promise<void> {
    console.log('Cannot setNavigationBarColor on web', options);
    return;
  }
  async getNavigationBarColor(): Promise<{ color: string; buttonStyle: NavigationBarButtonStyle }> {
    console.log('Cannot getNavigationBarColor on web');
    return { color: '#000000', buttonStyle: NavigationBarButtonStyle.LIGHT }; // added default value
  }
}
