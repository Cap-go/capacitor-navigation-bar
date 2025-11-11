<a href="https://capgo.app/"><img src='https://raw.githubusercontent.com/Cap-go/capgo/main/assets/capgo_banner.png' alt='Capgo - Instant updates for capacitor'/></a>

<div align="center">
  <h2><a href="https://capgo.app/?ref=plugin_navigation_bar"> ➡️ Get Instant updates for your App with Capgo</a></h2>
  <h2><a href="https://capgo.app/consulting/?ref=plugin_navigation_bar"> Missing a feature? We’ll build the plugin for you 💪</a></h2>
</div>

# capacitor-navigation-bar

Set navigation bar color for android lollipop and higher

## Documentation

The most complete doc is available here: https://capgo.app/docs/plugins/navigation-bar/

## Install

```bash
npm install @capgo/capacitor-navigation-bar
npx cap sync
```

## Example Apps

- `example-app`: Interactive showcase that exercises all plugin options (color presets, custom hex, dark buttons, state reading).


## API

<docgen-index>

* [`setNavigationBarColor(...)`](#setnavigationbarcolor)
* [`getNavigationBarColor()`](#getnavigationbarcolor)
* [`getPluginVersion()`](#getpluginversion)
* [Enums](#enums)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

Capacitor Navigation Bar Plugin for customizing the Android navigation bar.

### setNavigationBarColor(...)

```typescript
setNavigationBarColor(options: { color: NavigationBarColor | string; darkButtons?: boolean; }) => Promise<void>
```

Set the navigation bar color and button theme.

| Param         | Type                                                   | Description                                   |
| ------------- | ------------------------------------------------------ | --------------------------------------------- |
| **`options`** | <code>{ color: string; darkButtons?: boolean; }</code> | - Configuration for navigation bar appearance |

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
