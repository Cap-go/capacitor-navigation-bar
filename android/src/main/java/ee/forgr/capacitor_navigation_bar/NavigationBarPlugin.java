package ee.forgr.capacitor_navigation_bar;

import android.graphics.Color;
import android.os.Build;
import android.view.View;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.util.WebColor;
import java.util.Locale;

@CapacitorPlugin(name = "NavigationBar")
public class NavigationBarPlugin extends Plugin {

  @PluginMethod
  public void setNavigationBarColor(PluginCall call) {
    final String color = call.getString("color");
    final String buttonStyle = call.getString("buttonStyle", "#FFFFFF");

    if (color == null) {
      call.reject("Color must be provided");
      return;
    }

    getBridge()
      .executeOnMainThread(() -> {
        try {
          if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if ("transparent".equals(color.toLowerCase())) {
              int flags = getActivity().getWindow().getDecorView().getSystemUiVisibility();
              flags |= View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
              getActivity().getWindow().getDecorView().setSystemUiVisibility(flags);
              getActivity().getWindow().setNavigationBarColor(Color.TRANSPARENT);
            } else {
              final int parsedColor = WebColor.parseColor(color.toUpperCase(Locale.ROOT));
              getActivity().getWindow().setNavigationBarColor(parsedColor);
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
              int flags = getActivity()
                .getWindow()
                .getDecorView()
                .getSystemUiVisibility();
              if (buttonStyle.equals("#000000")) {
                flags |= View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
              } else {
                flags &= ~View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
              }
              getActivity()
                .getWindow()
                .getDecorView()
                .setSystemUiVisibility(flags);
            }
          }
          call.resolve();
        } catch (IllegalArgumentException ex) {
          call.reject("Invalid color provided. Must be a hex color (#RRGGBB) or 'transparent'");
        }
      });
  }

  @PluginMethod
  public void getNavigationBarColor(PluginCall call) {
    getBridge()
      .executeOnMainThread(() -> {
        try {
          JSObject ret = new JSObject();
          if (
            android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
          ) {
            int intColor = getActivity().getWindow().getNavigationBarColor();
            String hexColor = String.format("#%06X", (0xFFFFFF & intColor));
            ret.put("color", hexColor);

            String buttonStyle = "#FFFFFF"; // LIGHT
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
              int flags = getActivity()
                .getWindow()
                .getDecorView()
                .getSystemUiVisibility();
              if ((flags & View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR) != 0) {
                buttonStyle = "#000000"; // DARK
              }
            }
            ret.put("buttonStyle", buttonStyle);

            call.resolve(ret);
            return;
          }
          ret.put("color", "#000000");
          ret.put("buttonStyle", "#FFFFFF");
          call.resolve(ret);
        } catch (IllegalArgumentException ex) {
          call.reject(
            "Invalid color provided. Must be a hex color (#RRGGBB or #RRGGBBAA for transparency)"
          );
        }
      });
  }
}
