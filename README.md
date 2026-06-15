<a href="https://capgo.app/"><img src="https://capgo.app/readme-banner.svg?repo=Cap-go/capacitor-navigation-bar" alt="Capgo - Instant updates for Capacitor" /></a>

<div align="center">
  <h2><a href="https://capgo.app/?ref=plugin_navigation_bar"> ➡️ Get Instant updates for your App with Capgo</a></h2>
  <h2><a href="https://capgo.app/consulting/?ref=plugin_navigation_bar"> Missing a feature? We’ll build the plugin for you 💪</a></h2>
</div>

# capacitor-navigation-bar

Set navigation bar color for android lollipop and higher

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

```bash
npm install @capgo/capacitor-navigation-bar
npx cap sync
```

## Example Apps

- `example-app`: Interactive showcase that exercises all plugin options (color presets, custom hex, dark buttons, state reading).


## API

<docgen-index>

* [`getColor()`](#getcolor)
* [`getStyle()`](#getstyle)
* [`hide()`](#hide)
* [`setColor(...)`](#setcolor)
* [`setStyle(...)`](#setstyle)
* [`show()`](#show)
* [`setNavigationBarColor(...)`](#setnavigationbarcolor)
* [`getNavigationBarColor()`](#getnavigationbarcolor)
* [`getPluginVersion()`](#getpluginversion)
* [Interfaces](#interfaces)
* [Enums](#enums)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

Capacitor Navigation Bar Plugin for customizing the Android navigation bar.

### getColor()

```typescript
getColor() => Promise<GetColorResult>
```

Get the current background color of the navigation bar.

Only available on Android.

**Returns:** <code>Promise&lt;<a href="#getcolorresult">GetColorResult</a>&gt;</code>

**Since:** 8.2.0

--------------------


### getStyle()

```typescript
getStyle() => Promise<GetStyleResult>
```

Get the current style of the navigation bar buttons.

Only available on Android.

**Returns:** <code>Promise&lt;<a href="#getstyleresult">GetStyleResult</a>&gt;</code>

**Since:** 8.2.0

--------------------


### hide()

```typescript
hide() => Promise<void>
```

Hide the navigation bar.

Only available on Android.

**Since:** 8.2.0

--------------------


### setColor(...)

```typescript
setColor(options: SetColorOptions) => Promise<void>
```

Set the background color of the navigation bar.

Only available on Android.

| Param         | Type                                                        |
| ------------- | ----------------------------------------------------------- |
| **`options`** | <code><a href="#setcoloroptions">SetColorOptions</a></code> |

**Since:** 8.2.0

--------------------


### setStyle(...)

```typescript
setStyle(options: SetStyleOptions) => Promise<void>
```

Set the style of the navigation bar buttons.

Only available on Android.

| Param         | Type                                                        |
| ------------- | ----------------------------------------------------------- |
| **`options`** | <code><a href="#setstyleoptions">SetStyleOptions</a></code> |

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


### Interfaces


#### GetColorResult

| Prop        | Type                | Description                                                                                                 | Since |
| ----------- | ------------------- | ----------------------------------------------------------------------------------------------------------- | ----- |
| **`color`** | <code>string</code> | The hexadecimal color of the navigation bar, or `'transparent'` if the navigation bar is fully transparent. | 8.2.0 |


#### GetStyleResult

| Prop        | Type                                    | Description                              | Since |
| ----------- | --------------------------------------- | ---------------------------------------- | ----- |
| **`style`** | <code><a href="#style">Style</a></code> | The style of the navigation bar buttons. | 8.2.0 |


#### SetColorOptions

| Prop               | Type                | Description                                                                                                                                                  | Since |
| ------------------ | ------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------ | ----- |
| **`color`**        | <code>string</code> | The hexadecimal color to set as the background color of the navigation bar. Use `'transparent'` to make the navigation bar transparent.                      | 8.2.0 |
| **`dividerColor`** | <code>string</code> | The hexadecimal color to set as the color of the navigation bar divider. Use `'transparent'` to hide the divider. Only available on Android (API level 28+). | 8.2.0 |


#### SetStyleOptions

| Prop        | Type                                    | Description                              | Since |
| ----------- | --------------------------------------- | ---------------------------------------- | ----- |
| **`style`** | <code><a href="#style">Style</a></code> | The style of the navigation bar buttons. | 8.2.0 |


### Enums


#### Style

| Members       | Value                  | Description                                  | Since |
| ------------- | ---------------------- | -------------------------------------------- | ----- |
| **`Dark`**    | <code>'DARK'</code>    | Light icons on a dark background.            | 8.2.0 |
| **`Default`** | <code>'DEFAULT'</code> | Resolved from the current device appearance. | 8.2.0 |
| **`Light`**   | <code>'LIGHT'</code>   | Dark icons on a light background.            | 8.2.0 |


#### NavigationBarColor

| Members           | Value                      | Description       |
| ----------------- | -------------------------- | ----------------- |
| **`WHITE`**       | <code>'#FFFFFF'</code>     | White color       |
| **`BLACK`**       | <code>'#000000'</code>     | Black color       |
| **`TRANSPARENT`** | <code>'transparent'</code> | Transparent color |

</docgen-api>
