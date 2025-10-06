package ee.forgr.capacitor_navigation_bar;

import android.graphics.Color;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.webkit.WebView;
import android.widget.FrameLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.util.WebColor;

@CapacitorPlugin(name = "NavigationBar")
public class NavigationBarPlugin extends Plugin {

    private static final String NAV_BAR_OVERLAY_TAG = "capgo-navigation-bar-overlay";
    private View navigationBarOverlay;
    private Integer currentNavigationBarColor;
    private Boolean currentDarkButtons;

    @PluginMethod
    public void setNavigationBarColor(PluginCall call) {
        final String color = call.getString("color");
        final boolean darkButtons = Boolean.TRUE.equals(call.getBoolean("darkButtons", true));

        if (color == null) {
            call.reject("Color must be provided");
            return;
        }

        getBridge().executeOnMainThread(() -> {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Window window = getActivity().getWindow();

                    final boolean isTransparent = "transparent".equalsIgnoreCase(color);

                    if (isTransparent) {
                        WindowCompat.setDecorFitsSystemWindows(window, true);
                        removeNavigationBarOverlay(window);
                        window.setNavigationBarColor(Color.TRANSPARENT);
                        currentNavigationBarColor = null;
                    } else {
                        View overlay = ensureNavigationBarOverlay(window, this.bridge.getWebView());
                        if (overlay == null) {
                            call.reject("Unable to prepare navigation bar overlay view");
                            return;
                        }

                        final int parsedColor = WebColor.parseColor(color);
                        overlay.setBackgroundColor(parsedColor);
                        overlay.setVisibility(View.VISIBLE);
                        overlay.bringToFront();
                        window.setNavigationBarColor(Color.TRANSPARENT);
                        currentNavigationBarColor = parsedColor;
                        configureNavigationBarOverlay(window);
                    }

                    WindowInsetsControllerCompat controller = WindowCompat.getInsetsController(window, window.getDecorView());
                    if (controller != null) {
                        controller.setAppearanceLightNavigationBars(darkButtons);
                    }
                    currentDarkButtons = darkButtons;

                    ViewCompat.requestApplyInsets(window.getDecorView());
                    call.resolve();
                } else {
                    call.reject("Navigation bar color customization is not supported on this Android version.");
                }
            } catch (IllegalArgumentException ex) {
                call.reject("Invalid color provided. Must be a hex color (#RRGGBB) or 'transparent'");
            }
        });
    }

    @PluginMethod
    public void getNavigationBarColor(PluginCall call) {
        getBridge().executeOnMainThread(() -> {
            try {
                JSObject ret = new JSObject();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Window window = getActivity().getWindow();
                    int resolvedColor = resolveCurrentNavigationBarColor(window);
                    ret.put("color", formatColorHex(resolvedColor));

                    Boolean darkButtonsState = currentDarkButtons;
                    if (darkButtonsState == null) {
                        WindowInsetsControllerCompat controller = WindowCompat.getInsetsController(window, window.getDecorView());
                        if (controller != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            darkButtonsState = !controller.isAppearanceLightNavigationBars();
                        } else {
                            darkButtonsState = true;
                        }
                    }

                    ret.put("darkButtons", darkButtonsState);
                    call.resolve(ret);
                } else {
                    ret.put("color", "#000000");
                    ret.put("darkButtons", true);
                    call.resolve(ret);
                }
            } catch (Exception ex) {
                call.reject("Failed to get navigation bar color or button style", ex);
            }
        });
    }

    private View ensureNavigationBarOverlay(Window window, WebView webView) {
        ViewGroup contentLayout = webView.getParent() instanceof ViewGroup ? (ViewGroup) webView.getParent() : null;
        if (contentLayout == null) {
            return null;
        }

        View existing = contentLayout.findViewWithTag(NAV_BAR_OVERLAY_TAG);
        if (existing instanceof View) {
            navigationBarOverlay = existing;
        }

        if (navigationBarOverlay == null) {
            View overlay = new View(getContext());
            overlay.setTag(NAV_BAR_OVERLAY_TAG);
            overlay.setClickable(false);
            overlay.setFocusable(false);
            overlay.setImportantForAccessibility(View.IMPORTANT_FOR_ACCESSIBILITY_NO);
            overlay.setFitsSystemWindows(false);
            CoordinatorLayout.LayoutParams params = new CoordinatorLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
            params.gravity = Gravity.BOTTOM;
            overlay.setLayoutParams(params);
            contentLayout.addView(overlay);
            navigationBarOverlay = overlay;
        } else if (navigationBarOverlay.getParent() != contentLayout) {
            if (navigationBarOverlay.getParent() instanceof ViewGroup) {
                ((ViewGroup) navigationBarOverlay.getParent()).removeView(navigationBarOverlay);
            }
            CoordinatorLayout.LayoutParams params = new CoordinatorLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                navigationBarOverlay.getLayoutParams() != null ? navigationBarOverlay.getLayoutParams().height : 0
            );
            params.gravity = Gravity.BOTTOM;
            navigationBarOverlay.setLayoutParams(params);
            contentLayout.addView(navigationBarOverlay);
        }

        return navigationBarOverlay;
    }

    private void configureNavigationBarOverlay(Window window) {
        if (navigationBarOverlay == null) {
            return;
        }

        View overlay = navigationBarOverlay;
        overlay.bringToFront();
        ViewCompat.setOnApplyWindowInsetsListener(overlay, (v, insets) -> {
            Insets navInsets = insets.getInsets(WindowInsetsCompat.Type.navigationBars());
            Insets tappableInsets = insets.getInsets(WindowInsetsCompat.Type.tappableElement());
            int bottomInset = Math.max(navInsets.bottom, tappableInsets.bottom);

            ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
            if (layoutParams != null && layoutParams.height != bottomInset) {
                layoutParams.height = bottomInset;

                // Ensure gravity is set to bottom for CoordinatorLayout.LayoutParams
                if (layoutParams instanceof CoordinatorLayout.LayoutParams) {
                    ((CoordinatorLayout.LayoutParams) layoutParams).gravity = Gravity.BOTTOM;
                }

                v.setLayoutParams(layoutParams);
            }

            return consumeNavigationBarInsets(insets);
        });

        ViewCompat.requestApplyInsets(overlay);
    }

    private void removeNavigationBarOverlay(Window window) {
        if (navigationBarOverlay == null) {
            return;
        }

        ViewParent parent = navigationBarOverlay.getParent();
        if (parent instanceof ViewGroup) {
            ((ViewGroup) parent).removeView(navigationBarOverlay);
        }
        navigationBarOverlay = null;
    }

    @SuppressWarnings("deprecation")
    private WindowInsetsCompat consumeNavigationBarInsets(WindowInsetsCompat insets) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowInsetsCompat.Builder builder = new WindowInsetsCompat.Builder(insets);
            builder.setInsets(WindowInsetsCompat.Type.navigationBars(), Insets.NONE);
            builder.setInsets(WindowInsetsCompat.Type.tappableElement(), Insets.NONE);
            return builder.build();
        }

        WindowInsetsCompat.Builder builder = new WindowInsetsCompat.Builder(insets);
        builder.setSystemWindowInsets(
            Insets.of(insets.getSystemWindowInsetLeft(), insets.getSystemWindowInsetTop(), insets.getSystemWindowInsetRight(), 0)
        );
        return builder.build();
    }

    private int resolveCurrentNavigationBarColor(Window window) {
        if (currentNavigationBarColor != null) {
            return currentNavigationBarColor;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return window.getNavigationBarColor();
        }

        return Color.BLACK;
    }

    private String formatColorHex(int color) {
        if ((color >>> 24) == 0) {
            return "#00000000";
        }
        return String.format("#%06X", (0xFFFFFF & color));
    }
}
