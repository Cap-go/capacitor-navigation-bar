<a href="https://capgo.app/"><img src='https://raw.githubusercontent.com/Cap-go/capgo/main/assets/capgo_banner.png' alt='Capgo - Instant updates for capacitor'/></a>

<div align="center">
  <h2><a href="https://capgo.app/?ref=plugin"> ➡️ Get Instant updates for your App with Capgo</a></h2>
  <h2><a href="https://capgo.app/consulting/?ref=plugin"> Missing a feature? We’ll build the plugin for you 💪</a></h2>
</div>

# capacitor-navigation-bar

Set navigation bar color for android lolipop and higher

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
* [Enums](#enums)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### setNavigationBarColor(...)

```typescript
setNavigationBarColor(options: { color: NavigationBarColor | string; darkButtons?: boolean; }) => Promise<void>
```

| Param         | Type                                                   |
| ------------- | ------------------------------------------------------ |
| **`options`** | <code>{ color: string; darkButtons?: boolean; }</code> |

--------------------


### getNavigationBarColor()

```typescript
getNavigationBarColor() => Promise<{ color: string; darkButtons: boolean; }>
```

**Returns:** <code>Promise&lt;{ color: string; darkButtons: boolean; }&gt;</code>

--------------------


### Enums


#### NavigationBarColor

| Members           | Value                      |
| ----------------- | -------------------------- |
| **`WHITE`**       | <code>'#FFFFFF'</code>     |
| **`BLACK`**       | <code>'#000000'</code>     |
| **`TRANSPARENT`** | <code>'transparent'</code> |

</docgen-api>
