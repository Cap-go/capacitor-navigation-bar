import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(NavigationBarPlugin)
public class NavigationBarPlugin: CAPPlugin {

    @objc func setNavigationBarColor(_ call: CAPPluginCall) {
        let color = call.getString("color") ?? ""
        print("Cannot set navigation bar color in ios \(color)")
        call.resolve([
            "value": false
        ])
    }
    @objc func getNavigationBarColor(_ call: CAPPluginCall) {
        print("Cannot get navigation bar color in ios")
        call.resolve([
            "color": "#000000"
        ])
    }
}
