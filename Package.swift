// swift-tools-version:5.9
import PackageDescription

let package = Package(
    name: "CapgoCapacitorNavigationBar",
    platforms: [
        .iOS(.v14)
    ],
    products: [
        .library(
            name: "CapgoCapacitorNavigationBar",
            targets: ["CapgoCapacitorNavigationBar"]
        )
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", from: "7.4.3")
    ],
    targets: [
        .target(
            name: "CapgoCapacitorNavigationBar",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm")
            ],
            path: "ios/Sources/CapgoCapacitorNavigationBar"
        ),
        .testTarget(
            name: "CapgoCapacitorNavigationBarTests",
            dependencies: ["CapgoCapacitorNavigationBar"],
            path: "ios/Tests/CapgoCapacitorNavigationBarTests")
    ],
    swiftLanguageVersions: [
        .v5
    ]
)
