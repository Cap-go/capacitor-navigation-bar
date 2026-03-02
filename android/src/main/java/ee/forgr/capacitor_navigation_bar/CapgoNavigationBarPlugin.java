package ee.forgr.capacitor_navigation_bar;

import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.WindowInsetsController;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.util.WebColor;
import java.util.Locale;

@CapacitorPlugin(name = "NavigationBar")
public class CapgoNavigationBarPlugin extends Plugin {

    private final String pluginVersion = "8.0.23";

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
                if ("transparent".equalsIgnoreCase(color)) {
                    int flags = getActivity().getWindow().getDecorView().getSystemUiVisibility();
                    flags |= View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                    getActivity().getWindow().getDecorView().setSystemUiVisibility(flags);
                    getActivity().getWindow().setNavigationBarColor(Color.TRANSPARENT);
                } else {
                    final int parsedColor = WebColor.parseColor(color);
                    View decor = getActivity().getWindow().getDecorView();
                    int flags = decor.getSystemUiVisibility();
                    flags &= ~View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION & ~View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                    decor.setSystemUiVisibility(flags);
                    getActivity().getWindow().setNavigationBarColor(parsedColor);
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    WindowInsetsController insetsController = getActivity().getWindow().getInsetsController();
                    if (insetsController != null) {
                        if (darkButtons) {
                            insetsController.setSystemBarsAppearance(
                                WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS,
                                WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS
                            );
                        } else {
                            insetsController.setSystemBarsAppearance(0, WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS);
                        }
                    }
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    int flags = getActivity().getWindow().getDecorView().getSystemUiVisibility();
                    if (darkButtons) {
                        flags |= View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
                    } else {
                        flags &= ~View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
                    }
                    getActivity().getWindow().getDecorView().setSystemUiVisibility(flags);
                }
                call.resolve();
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
                int intColor = getActivity().getWindow().getNavigationBarColor();
                String hexColor = String.format("#%06X", (0xFFFFFF & intColor));
                ret.put("color", hexColor);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    WindowInsetsController insetsController = getActivity().getWindow().getInsetsController();
                    if (insetsController != null) {
                        int appearance = insetsController.getSystemBarsAppearance();
                        boolean isLight = (appearance & WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS) != 0;
                        ret.put("darkButtons", !isLight);
                    } else {
                        ret.put("darkButtons", true);
                    }
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    int flags = getActivity().getWindow().getDecorView().getSystemUiVisibility();
                    boolean isLight = (flags & View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR) != 0;
                    ret.put("darkButtons", !isLight);
                } else {
                    ret.put("darkButtons", true);
                }

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
}
