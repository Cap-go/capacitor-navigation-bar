<a href="https://capgo.app/"><img src='https://raw.githubusercontent.com/Cap-go/capgo/main/assets/capgo_banner.png' alt='Capgo - Instant updates for capacitor'/></a>

<div align="center">
<h2><a href="https://capgo.app/">Check out: Capgo — Instant updates for capacitor</a></h2>
</div>

# capacitor-navigation-bar

Set navigation bar color for android lolipop and higher

## Install

```bash
npm install @capgo/capacitor-navigation-bar
npx cap sync
```

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
setNavigationBarColor(options: { color: NavigationBarColor | string; buttonStyle?: NavigationBarButtonStyle; }) => Promise<void>
```

| Param         | Type                                                                                                            |
| ------------- | --------------------------------------------------------------------------------------------------------------- |
| **`options`** | <code>{ color: string; buttonStyle?: <a href="#navigationbarbuttonstyle">NavigationBarButtonStyle</a>; }</code> |

--------------------


### getNavigationBarColor()

```typescript
getNavigationBarColor() => Promise<{ color: string; buttonStyle: NavigationBarButtonStyle; }>
```

**Returns:** <code>Promise&lt;{ color: string; buttonStyle: <a href="#navigationbarbuttonstyle">NavigationBarButtonStyle</a>; }&gt;</code>

--------------------


### Enums


#### NavigationBarColor

| Members           | Value                    |
| ----------------- | ------------------------ |
| **`WHITE`**       | <code>"#FFFFFF"</code>   |
| **`BLACK`**       | <code>"#000000"</code>   |
| **`TRANSPARENT`** | <code>"#00000000"</code> |


#### NavigationBarButtonStyle

| Members     | Value                  |
| ----------- | ---------------------- |
| **`LIGHT`** | <code>"#FFFFFF"</code> |
| **`DARK`**  | <code>"#000000"</code> |

</docgen-api>
