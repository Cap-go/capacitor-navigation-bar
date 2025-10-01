package ee.forgr.capacitor_navigation_bar;

import android.graphics.Color;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.util.WebColor;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import java.util.Map;
import java.util.WeakHashMap;

@CapacitorPlugin(name = "NavigationBar")
public class NavigationBarPlugin extends Plugin {

  private static final String NAV_BAR_OVERLAY_TAG = "capgo-navigation-bar-overlay";
  private View navigationBarOverlay;
  private Integer currentNavigationBarColor;
  private Boolean currentDarkButtons;
  private final Map<View, int[]> originalPaddingMap = new WeakHashMap<>();

  @PluginMethod
  public void setNavigationBarColor(PluginCall call) {
    final String color = call.getString("color");
    final boolean darkButtons = Boolean.TRUE.equals(
      call.getBoolean("darkButtons", true)
    );

    if (color == null) {
      call.reject("Color must be provided");
      return;
    }

    getBridge()
      .executeOnMainThread(() -> {
        try {
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getActivity().getWindow();
            WindowCompat.setDecorFitsSystemWindows(window, false);

            View overlay = ensureNavigationBarOverlay(window);
            if (overlay == null) {
              call.reject("Unable to prepare navigation bar overlay view");
              return;
            }

            if ("transparent".equalsIgnoreCase(color)) {
              overlay.setBackgroundColor(Color.TRANSPARENT);
              overlay.setVisibility(View.GONE);
              currentNavigationBarColor = Color.TRANSPARENT;
            } else {
              final int parsedColor = WebColor.parseColor(color);
              overlay.setBackgroundColor(parsedColor);
              overlay.setVisibility(View.VISIBLE);
              currentNavigationBarColor = parsedColor;
            }

            WindowInsetsControllerCompat controller =
              WindowCompat.getInsetsController(window, window.getDecorView());
            if (controller != null) {
              controller.setAppearanceLightNavigationBars(darkButtons);
            }
            currentDarkButtons = darkButtons;

            ViewCompat.requestApplyInsets(window.getDecorView());
            call.resolve();
          } else {
            call.reject(
              "Navigation bar color customization is not supported on this Android version."
            );
          }
        } catch (IllegalArgumentException ex) {
          call.reject(
            "Invalid color provided. Must be a hex color (#RRGGBB) or 'transparent'"
          );
        }
      });
  }

  @PluginMethod
  public void getNavigationBarColor(PluginCall call) {
    getBridge()
      .executeOnMainThread(() -> {
        try {
          JSObject ret = new JSObject();
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getActivity().getWindow();
            int resolvedColor = resolveCurrentNavigationBarColor(window);
            ret.put("color", formatColorHex(resolvedColor));

            Boolean darkButtonsState = currentDarkButtons;
            if (darkButtonsState == null) {
              WindowInsetsControllerCompat controller =
                WindowCompat.getInsetsController(window, window.getDecorView());
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

  private View ensureNavigationBarOverlay(Window window) {
    View decor = window.getDecorView();
    if (!(decor instanceof ViewGroup)) {
      return null;
    }

    ViewGroup decorGroup = (ViewGroup) decor;
    View existing = decorGroup.findViewWithTag(NAV_BAR_OVERLAY_TAG);
    if (existing instanceof View) {
      navigationBarOverlay = existing;
      return navigationBarOverlay;
    }

    View overlay = new View(getContext());
    overlay.setTag(NAV_BAR_OVERLAY_TAG);
    overlay.setClickable(false);
    overlay.setFocusable(false);
    overlay.setImportantForAccessibility(View.IMPORTANT_FOR_ACCESSIBILITY_NO);
    FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
      ViewGroup.LayoutParams.MATCH_PARENT,
      0,
      Gravity.BOTTOM
    );
    overlay.setLayoutParams(params);

    ViewCompat.setOnApplyWindowInsetsListener(overlay, (v, insets) -> {
      Insets navInsets = insets.getInsets(WindowInsetsCompat.Type.navigationBars());
      Insets tappableInsets = insets.getInsets(WindowInsetsCompat.Type.tappableElement());
      int bottomInset = Math.max(navInsets.bottom, tappableInsets.bottom);
      updateContentPadding(window, bottomInset);

      ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
      if (layoutParams instanceof FrameLayout.LayoutParams) {
        FrameLayout.LayoutParams frameParams = (FrameLayout.LayoutParams) layoutParams;
        if (frameParams.height != bottomInset) {
          frameParams.height = bottomInset;
          v.setLayoutParams(frameParams);
        }
      }

      return insets;
    });

    decorGroup.addView(overlay);
    navigationBarOverlay = overlay;
    return navigationBarOverlay;
  }

  private void updateContentPadding(Window window, int bottomInset) {
    View content = window.findViewById(android.R.id.content);
    if (content == null) {
      return;
    }

    int[] originalPadding = originalPaddingMap.get(content);
    if (originalPadding == null) {
      originalPadding = new int[] {
        content.getPaddingLeft(),
        content.getPaddingTop(),
        content.getPaddingRight(),
        content.getPaddingBottom(),
      };
      originalPaddingMap.put(content, originalPadding);
    }

    content.setPadding(
      originalPadding[0],
      originalPadding[1],
      originalPadding[2],
      originalPadding[3] + bottomInset
    );
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
