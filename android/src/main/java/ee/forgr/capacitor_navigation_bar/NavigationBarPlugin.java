package ee.forgr.capacitor_navigation_bar;

import android.os.Build;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.util.WebColor;
import com.getcapacitor.annotation.CapacitorPlugin;
import java.util.Locale;

@CapacitorPlugin(name = "NavigationBar")
public class NavigationBarPlugin extends Plugin {

    @PluginMethod
    public void setNavigationBarColor(PluginCall call) {
        final String color = call.getString("color");
        if (color == null) {
            call.reject("Color must be provided");
            return;
        }

        getBridge()
            .executeOnMainThread(
                () -> {
                    try {
                        final int parsedColor = WebColor.parseColor(color.toUpperCase(Locale.ROOT));
                        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            getActivity().getWindow().setNavigationBarColor(parsedColor);
                        }
                        call.resolve();
                    } catch (IllegalArgumentException ex) {
                        call.reject("Invalid color provided. Must be a hex string (ex: #ff0000");
                    }
                }
            );
    }

    @PluginMethod
    public void getNavigationBarColor(PluginCall call) {
        getBridge()
            .executeOnMainThread(
                () -> {
                    try {
                        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {                
                            JSObject ret = new JSObject();
                            int intColor = getActivity().getWindow().getNavigationBarColor();
                            String hexColor = String.format("#%06X", (0xFFFFFF & intColor));
                            ret.put("color", hexColor);
                            call.resolve(ret);
                            return;
                        }
                        JSObject ret = new JSObject();
                        ret.put("color", "#000000");
                        call.resolve(ret);
                    } catch (IllegalArgumentException ex) {
                        call.reject("Invalid color provided. Must be a hex string (ex: #ff0000");
                    }
                }
            );
    }
}
