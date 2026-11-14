# Cannon Chest Companion

Android companion app starter for tracking a Fortnite cannon/chest farming route.

## Included
- Dark mobile dashboard built with Kotlin + Jetpack Compose
- Start/stop session controls
- Auto-farm and auto-detection preference toggles (UI/session state only)
- Tracking interval control
- Chest discovered/collected counters
- Companion map grid and manual marker counters
- Integration status clearly separates companion features from game-side features
- GitHub Actions workflow that builds and uploads a debug APK

## Important integration boundary
This repository is an external companion app. It does not inject into Fortnite, inspect game memory, detect live chests, launch a cannon, teleport a character, or collect chests. Those capabilities are not implemented by the Android companion and require a supported game-side integration/data interface. The map is currently an illustrative local grid, not a live Fortnite map.

## Build locally
Requires JDK 17 and Android SDK (platform 35).

```bash
gradle :app:assembleDebug
```

APK output: `app/build/outputs/apk/debug/app-debug.apk`

## Build with GitHub Actions
1. Create a GitHub repository named `CannonChestCompanion`.
2. Upload/push the contents of this folder to the repository root.
3. Open **Actions** and run **Build Android APK**, or push to `main`.
4. Open the completed workflow run and download the `CannonChestCompanion-debug` artifact.
