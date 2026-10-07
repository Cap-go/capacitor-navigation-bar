# capacitor-navigation-bar

Control the Android navigation bar from your Capacitor app: set its color and button style, make it transparent, or hide it for immersive screens.

<a href="https://capgo.app/?ref=plugin_navigation_bar"><img src="https://capgo.app/readme-banner.svg?repo=Cap-go/capacitor-navigation-bar" alt="Capgo - Instant updates for Capacitor" /></a>

<div align="center">
  <p><b>Capgo</b>: open-source live updates for Ionic and Capacitor apps. Ship OTA fixes and features instantly, without waiting for app store review.</p>
  <h2><a href="https://capgo.app/register/?ref=plugin_navigation_bar">➡️ Get started for free</a></h2>
  <p>14-day unlimited free trial. No credit card required</p>
  <p><a href="https://capgo.app/consulting/?ref=plugin_navigation_bar">Missing a feature? We'll build the plugin for you 💪</a></p>
</div>

<p align="center">
  <img src="https://raw.githubusercontent.com/Cap-go/capacitor-navigation-bar/main/assets/github-social-preview.png" alt="@capgo/capacitor-navigation-bar for Capacitor apps" width="300" />
</p>

## Key features

- **Color**: `setNavigationBarColor()` sets the bar color, including `transparent`, and light or dark buttons.
- **Read state**: `getNavigationBarColor()` returns the current color and button theme.
- **Hide and show**: `hide()` and `show()` for fullscreen content.
- **Config defaults**: set `color`, `dividerColor` and `style` in `capacitor.config` to apply them at launch.
- **Platforms**: Android. Android only. iOS rejects the calls, and web only logs them.

## Documentation

The most complete doc is available here: https://capgo.app/docs/plugins/navigation-bar/

## Compatibility

| Plugin version | Capacitor compatibility | Maintained |
| -------------- | ----------------------- | ---------- |
| v8.\*.\*       | v8.\*.\*                | ✅          |
| v7.\*.\*       | v7.\*.\*                | On demand   |
| v6.\*.\*       | v6.\*.\*                | ❌          |
| v5.\*.\*       | v5.\*.\*                | ❌          |

> **Note:** The major version of this plugin follows the major version of Capacitor. Use the version that matches your Capacitor installation (e.g., plugin v8 for Capacitor 8). Only the latest major version is actively maintained.

## Install

You can use our AI-Assisted Setup to install the plugin. Add the Capgo skills to your AI tool using the following command:

```bash
npx skills add https://github.com/cap-go/capacitor-skills --skill capacitor-plugins
```

Then use the following prompt:

```text
Use the `capacitor-plugins` skill from `cap-go/capacitor-skills` to install the `@capgo/capacitor-navigation-bar` plugin in my project.
```

If you prefer Manual Setup, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capgo/capacitor-navigation-bar
npx cap sync
```

## Example Apps

- `example-app`: Interactive showcase that exercises all plugin options (color presets, custom hex, dark buttons, state reading).

## Configuration

You can apply navigation bar defaults when the native plugin loads:

```typescript
import type { CapacitorConfig } from '@capacitor/cli';

const config: CapacitorConfig = {
  plugins: {
    NavigationBar: {
      color: '#ffffff',
      dividerColor: '#d9d9d9',
      style: 'LIGHT',
    },
  },
};

export default config;
```

`style` accepts `LIGHT`, `DARK`, or `DEFAULT`. Use `LIGHT` for dark buttons, `DARK` for light buttons, and `DEFAULT` to follow the current device appearance.

## API

<docgen-index>

* [`hide()`](#hide)
* [`show()`](#show)
* [`setNavigationBarColor(...)`](#setnavigationbarcolor)
* [`getNavigationBarColor()`](#getnavigationbarcolor)
* [`getPluginVersion()`](#getpluginversion)
* [Enums](#enums)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

Capacitor Navigation Bar Plugin for customizing the Android navigation bar.

### hide()

```typescript
hide() => Promise<void>
```

Hide the navigation bar.

Only available on Android.

**Since:** 8.2.0

--------------------


### show()

```typescript
show() => Promise<void>
```

Show the navigation bar.

Only available on Android.

**Since:** 8.2.0

--------------------


### setNavigationBarColor(...)

```typescript
setNavigationBarColor(options: { color: NavigationBarColor | string; darkButtons?: boolean; dividerColor?: NavigationBarColor | string; }) => Promise<void>
```

Set the navigation bar color and button theme.

| Param         | Type                                                                          | Description                                   |
| ------------- | ----------------------------------------------------------------------------- | --------------------------------------------- |
| **`options`** | <code>{ color: string; darkButtons?: boolean; dividerColor?: string; }</code> | - Configuration for navigation bar appearance |

**Since:** 1.0.0

--------------------


### getNavigationBarColor()

```typescript
getNavigationBarColor() => Promise<{ color: string; darkButtons: boolean; }>
```

Get the current navigation bar color and button theme.

**Returns:** <code>Promise&lt;{ color: string; darkButtons: boolean; }&gt;</code>

**Since:** 1.0.0

--------------------


### getPluginVersion()

```typescript
getPluginVersion() => Promise<{ version: string; }>
```

Get the native Capacitor plugin version.

**Returns:** <code>Promise&lt;{ version: string; }&gt;</code>

**Since:** 1.0.0

--------------------


### Enums


#### NavigationBarColor

| Members           | Value                      | Description       |
| ----------------- | -------------------------- | ----------------- |
| **`WHITE`**       | <code>'#FFFFFF'</code>     | White color       |
| **`BLACK`**       | <code>'#000000'</code>     | Black color       |
| **`TRANSPARENT`** | <code>'transparent'</code> | Transparent color |

</docgen-api>
