import XCTest
import Capacitor
@testable import CapgoNavigationBar

final class CapgoNavigationBarTests: XCTestCase {
    private func makeCall(method: String, options: [String: Any] = [:], errorHandler: @escaping (CAPPluginCallError) -> Void) -> CAPPluginCall {
        CAPPluginCall(
            callbackId: "test",
            methodName: method,
            options: options as NSDictionary,
            success: { _, _ in },
            error: errorHandler
        )
    }

    func testSetNavigationBarColorRejectsOnIOS() {
        let plugin = NavigationBarPlugin()
        var capturedError: CAPPluginCallError?
        let call = makeCall(method: "setNavigationBarColor", options: ["color": "#FFFFFF"]) { error in
            capturedError = error
        }

        plugin.setNavigationBarColor(call)

        XCTAssertEqual(capturedError?.message, "Cannot set navigation bar color in ios")
    }

    func testGetNavigationBarColorRejectsOnIOS() {
        let plugin = NavigationBarPlugin()
        var capturedError: CAPPluginCallError?
        let call = makeCall(method: "getNavigationBarColor") { error in
            capturedError = error
        }

        plugin.getNavigationBarColor(call)

        XCTAssertEqual(capturedError?.message, "Cannot get navigation bar color in ios")
    }
}
