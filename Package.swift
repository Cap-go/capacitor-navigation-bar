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
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", from: "8.0.0")
    ],
    targets: [
        .target(
            name: "CapgoCapacitorNavigationBar",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm")
            ],
            path: "ios/Sources/CapgoNavigationBarPlugin"
        ),
        .testTarget(
            name: "CapgoCapacitorNavigationBarTests",
            dependencies: ["CapgoCapacitorNavigationBar"],
            path: "ios/Tests/CapgoNavigationBarTests")
    ],
    swiftLanguageVersions: [
        .v5
    ]
)
