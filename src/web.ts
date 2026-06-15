import { WebPlugin } from '@capacitor/core';

import type { NavigationBarPlugin, SetColorOptions, SetStyleOptions } from './definitions';
import { Style } from './definitions';

export class NavigationBarWeb extends WebPlugin implements NavigationBarPlugin {
  async getColor(): Promise<{ color: string }> {
    console.log('Cannot getColor on web');
    return { color: '#000000' };
  }

  async getStyle(): Promise<{ style: Style }> {
    console.log('Cannot getStyle on web');
    return { style: Style.Light };
  }

  async hide(): Promise<void> {
    console.log('Cannot hide navigation bar on web');
  }

  async setColor(options: SetColorOptions): Promise<void> {
    console.log('Cannot setColor on web', options);
  }

  async setStyle(options: SetStyleOptions): Promise<void> {
    console.log('Cannot setStyle on web', options);
  }

  async show(): Promise<void> {
    console.log('Cannot show navigation bar on web');
  }

  async setNavigationBarColor(options: { color: string; darkButtons?: boolean; dividerColor?: string }): Promise<void> {
    console.log('Cannot setNavigationBarColor on web', options);
  }

  async getNavigationBarColor(): Promise<{
    color: string;
    darkButtons: boolean;
  }> {
    console.log('Cannot getNavigationBarColor on web');
    return { color: '#000000', darkButtons: true };
  }

  async getPluginVersion(): Promise<{ version: string }> {
    return { version: 'web' };
  }
}
