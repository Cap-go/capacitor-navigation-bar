package ee.forgr.capacitor_navigation_bar;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.util.WebColor;
import java.util.Locale;

@CapacitorPlugin(name = "NavigationBar")
public class CapgoNavigationBarPlugin extends Plugin {

    private static final String ERROR_COLOR_MISSING = "color must be provided.";
    private static final String ERROR_STYLE_MISSING = "style must be provided.";
    private static final String ERROR_STYLE_INVALID = "style must be DARK, LIGHT, or DEFAULT.";
    private static final String STYLE_DARK = "DARK";
    private static final String STYLE_DEFAULT = "DEFAULT";
    private static final String STYLE_LIGHT = "LIGHT";
    private static final String TAG = "NavigationBar";
    private static final String TRANSPARENT = "transparent";

    private final String pluginVersion = "8.1.8";

    @Override
    public void load() {
        final String color = getConfig().getString("color");
        final String dividerColor = getConfig().getString("dividerColor");
        final String style = getConfig().getString("style");

        if (color == null && style == null) {
            return;
        }

        getBridge().executeOnMainThread(() -> {
            try {
                if (color != null) {
                    applyColor(color, dividerColor);
                }
                if (style != null) {
                    applyStyle(style);
                }
            } catch (Exception ex) {
                Logger.error(TAG, "Failed to apply NavigationBar config", ex);
            }
        });
    }

    @PluginMethod
    public void getColor(PluginCall call) {
        getBridge().executeOnMainThread(() -> {
            JSObject ret = new JSObject();
            ret.put("color", getFormattedNavigationBarColor());
            call.resolve(ret);
        });
    }

    @PluginMethod
    public void getStyle(PluginCall call) {
        getBridge().executeOnMainThread(() -> {
            JSObject ret = new JSObject();
            ret.put("style", areDarkButtonsEnabled() ? STYLE_LIGHT : STYLE_DARK);
            call.resolve(ret);
        });
    }

    @PluginMethod
    public void hide(PluginCall call) {
        getBridge().executeOnMainThread(() -> {
            Window window = getActivity().getWindow();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                WindowInsetsController insetsController = window.getInsetsController();
                if (insetsController != null) {
                    insetsController.hide(WindowInsets.Type.navigationBars());
                }
            } else {
                int flags = window.getDecorView().getSystemUiVisibility();
                flags |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
                window.getDecorView().setSystemUiVisibility(flags);
            }
            call.resolve();
        });
    }

    @PluginMethod
    public void setColor(PluginCall call) {
        final String color = call.getString("color");
        final String dividerColor = call.getString("dividerColor");

        if (color == null) {
            call.reject(ERROR_COLOR_MISSING);
            return;
        }

        getBridge().executeOnMainThread(() -> {
            try {
                applyColor(color, dividerColor);
                call.resolve();
            } catch (IllegalArgumentException ex) {
                call.reject("Invalid color provided. color and dividerColor must be hex colors (#RRGGBB) or 'transparent'.");
            }
        });
    }

    @PluginMethod
    public void setStyle(PluginCall call) {
        final String style = call.getString("style");

        if (style == null) {
            call.reject(ERROR_STYLE_MISSING);
            return;
        }

        getBridge().executeOnMainThread(() -> {
            try {
                applyStyle(style);
                call.resolve();
            } catch (IllegalArgumentException ex) {
                call.reject(ex.getMessage());
            }
        });
    }

    @PluginMethod
    public void show(PluginCall call) {
        getBridge().executeOnMainThread(() -> {
            Window window = getActivity().getWindow();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                WindowInsetsController insetsController = window.getInsetsController();
                if (insetsController != null) {
                    insetsController.show(WindowInsets.Type.navigationBars());
                }
            } else {
                int flags = window.getDecorView().getSystemUiVisibility();
                flags &= ~View.SYSTEM_UI_FLAG_HIDE_NAVIGATION & ~View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
                window.getDecorView().setSystemUiVisibility(flags);
            }
            call.resolve();
        });
    }

    @PluginMethod
    public void setNavigationBarColor(PluginCall call) {
        final String color = call.getString("color");
        final boolean darkButtons = Boolean.TRUE.equals(call.getBoolean("darkButtons", true));
        final String dividerColor = call.getString("dividerColor");

        if (color == null) {
            call.reject("Color must be provided");
            return;
        }

        getBridge().executeOnMainThread(() -> {
            try {
                applyColor(color, dividerColor);
                applyDarkButtons(darkButtons);
                call.resolve();
            } catch (IllegalArgumentException ex) {
                call.reject("Invalid color provided. color and dividerColor must be hex colors (#RRGGBB) or 'transparent'.");
            }
        });
    }

    @PluginMethod
    public void getNavigationBarColor(PluginCall call) {
        getBridge().executeOnMainThread(() -> {
            try {
                JSObject ret = new JSObject();
                ret.put("color", getFormattedNavigationBarColor());
                ret.put("darkButtons", areDarkButtonsEnabled());
                call.resolve(ret);
            } catch (Exception ex) {
                call.reject("Failed to get navigation bar color or button style", ex);
            }
        });
    }

    @PluginMethod
    public void getPluginVersion(final PluginCall call) {
        try {
            final JSObject ret = new JSObject();
            ret.put("version", this.pluginVersion);
            call.resolve(ret);
        } catch (final Exception e) {
            call.reject("Could not get plugin version", e);
        }
    }

    private void applyColor(String color, String dividerColor) {
        final Window window = getActivity().getWindow();
        final Integer parsedDividerColor =
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.P && dividerColor != null ? parseColor(dividerColor) : null;

        if (TRANSPARENT.equalsIgnoreCase(color)) {
            setTransparentLayoutEnabled(window, true);
            window.setNavigationBarColor(Color.TRANSPARENT);
        } else {
            setTransparentLayoutEnabled(window, false);
            window.setNavigationBarColor(parseColor(color));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P && parsedDividerColor != null) {
            window.setNavigationBarDividerColor(parsedDividerColor);
        }
    }

    private void applyStyle(String style) {
        final String resolvedStyle = resolveStyle(style);
        applyDarkButtons(STYLE_LIGHT.equals(resolvedStyle));
    }

    private void applyDarkButtons(boolean darkButtons) {
        final Window window = getActivity().getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowInsetsController insetsController = window.getInsetsController();
            if (insetsController != null) {
                insetsController.setSystemBarsAppearance(
                    darkButtons ? WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS : 0,
                    WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS
                );
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int flags = window.getDecorView().getSystemUiVisibility();
            if (darkButtons) {
                flags |= View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
            } else {
                flags &= ~View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
            }
            window.getDecorView().setSystemUiVisibility(flags);
        }
    }

    private boolean areDarkButtonsEnabled() {
        final Window window = getActivity().getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowInsetsController insetsController = window.getInsetsController();
            if (insetsController != null) {
                int appearance = insetsController.getSystemBarsAppearance();
                return (appearance & WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS) != 0;
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int flags = window.getDecorView().getSystemUiVisibility();
            return (flags & View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR) != 0;
        }
        return false;
    }

    private String getFormattedNavigationBarColor() {
        int intColor = getActivity().getWindow().getNavigationBarColor();
        if (Color.alpha(intColor) == 0) {
            return TRANSPARENT;
        }
        return String.format("#%06X", (0xFFFFFF & intColor));
    }

    private boolean isNightMode() {
        int nightModeFlags = getContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        return nightModeFlags == Configuration.UI_MODE_NIGHT_YES;
    }

    private int parseColor(String color) {
        if (TRANSPARENT.equalsIgnoreCase(color)) {
            return Color.TRANSPARENT;
        }
        return WebColor.parseColor(color);
    }

    private String resolveStyle(String style) {
        final String normalizedStyle = style.toUpperCase(Locale.ROOT);
        if (STYLE_DEFAULT.equals(normalizedStyle)) {
            return isNightMode() ? STYLE_DARK : STYLE_LIGHT;
        }
        if (STYLE_DARK.equals(normalizedStyle) || STYLE_LIGHT.equals(normalizedStyle)) {
            return normalizedStyle;
        }
        throw new IllegalArgumentException(ERROR_STYLE_INVALID);
    }

    private void setTransparentLayoutEnabled(Window window, boolean enabled) {
        int flags = window.getDecorView().getSystemUiVisibility();
        if (enabled) {
            flags |= View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        } else {
            flags &= ~View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION & ~View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        }
        window.getDecorView().setSystemUiVisibility(flags);
    }
}
