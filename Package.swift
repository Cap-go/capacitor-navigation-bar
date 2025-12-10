// swift-tools-version:5.9
import PackageDescription

let package = Package(
    name: "CapgoNavigationBar",
    platforms: [
        .iOS(.v14)
    ],
    products: [
        .library(
            name: "CapgoNavigationBar",
            targets: ["CapgoNavigationBar"]
        )
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", from: "8.0.0")
    ],
    targets: [
        .target(
            name: "CapgoNavigationBar",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm")
            ],
            path: "ios/Sources/CapgoNavigationBarPlugin"
        ),
        .testTarget(
            name: "CapgoNavigationBarTests",
            dependencies: ["CapgoNavigationBar"],
            path: "ios/Tests/CapgoNavigationBarTests")
    ],
    swiftLanguageVersions: [
        .v5
    ]
)
