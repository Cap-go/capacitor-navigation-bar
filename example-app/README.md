# Navigation Bar Demo App

This sample Capacitor application showcases every option exposed by `@capgo/capacitor-navigation-bar`.
It lets you pick preset colors, supply any hex value, and toggle the `darkButtons` flag before invoking
`setNavigationBarColor`. You can also read back the current state using `getNavigationBarColor`.

## Getting started

```bash
npm install
npm run dev
```

When you are ready to test on an Android device or emulator:

```bash
npm run sync
# then open the generated android project and run it from Android Studio
```

> The navigation bar APIs only have an effect on platforms with a visible navigation bar (currently Android).
