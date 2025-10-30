import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(CapgoNavigationBarPlugin)
public class CapgoNavigationBarPlugin: CAPPlugin, CAPBridgedPlugin {
    private let pluginVersion: String = "7.3.9"
    public let identifier = "CapgoNavigationBarPlugin"
    public let jsName = "NavigationBar"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "setNavigationBarColor", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getNavigationBarColor", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getPluginVersion", returnType: CAPPluginReturnPromise)
    ]
    @objc func setNavigationBarColor(_ call: CAPPluginCall) {
        let color = call.getString("color") ?? ""
        print("Cannot set navigation bar color in ios \(color)")
        call.reject("Cannot set navigation bar color in ios")
    }
    @objc func getNavigationBarColor(_ call: CAPPluginCall) {
        print("Cannot get navigation bar color in ios")
        call.reject("Cannot get navigation bar color in ios")
    }

    @objc func getPluginVersion(_ call: CAPPluginCall) {
        call.resolve(["version": self.pluginVersion])
    }

}
