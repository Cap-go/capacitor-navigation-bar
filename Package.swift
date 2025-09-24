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
        .package(url: "https://github.com/ionic-team/capacitor-swift-package.git", from: "7.0.0")
    ],
    targets: [
        .target(
            name: "CapgoCapacitorNavigationBar",
            dependencies: [
                "CapgoCapacitorNavigationBarObjc",
                .product(name: "Capacitor", package: "capacitor-swift-package"),
                .product(name: "CapacitorPlugin", package: "capacitor-swift-package")
            ],
            path: "ios/Sources/CapgoCapacitorNavigationBar"
        ),
        .target(
            name: "CapgoCapacitorNavigationBarObjc",
            dependencies: [
                .product(name: "CapacitorPlugin", package: "capacitor-swift-package")
            ],
            path: "ios/Sources/CapgoCapacitorNavigationBarObjc",
            publicHeadersPath: ".",
            cSettings: [
                .headerSearchPath(".")
            ]
        )
    ],
    swiftLanguageVersions: [
        .v5
    ]
)
