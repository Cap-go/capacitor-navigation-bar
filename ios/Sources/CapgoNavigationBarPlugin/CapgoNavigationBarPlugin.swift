import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(CapgoNavigationBarPlugin)
public class CapgoNavigationBarPlugin: CAPPlugin, CAPBridgedPlugin {
    private let pluginVersion: String = "8.1.8"
    public let identifier = "CapgoNavigationBarPlugin"
    public let jsName = "NavigationBar"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "getColor", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getStyle", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "hide", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setColor", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setStyle", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "show", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setNavigationBarColor", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getNavigationBarColor", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getPluginVersion", returnType: CAPPluginReturnPromise)
    ]

    @objc func getColor(_ call: CAPPluginCall) {
        rejectUnsupported(call)
    }

    @objc func getStyle(_ call: CAPPluginCall) {
        rejectUnsupported(call)
    }

    @objc func hide(_ call: CAPPluginCall) {
        rejectUnsupported(call)
    }

    @objc func setColor(_ call: CAPPluginCall) {
        rejectUnsupported(call)
    }

    @objc func setStyle(_ call: CAPPluginCall) {
        rejectUnsupported(call)
    }

    @objc func show(_ call: CAPPluginCall) {
        rejectUnsupported(call)
    }

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

    private func rejectUnsupported(_ call: CAPPluginCall) {
        call.reject("Navigation bar is only available on Android")
    }

}
