# Travel App

A modern Android application designed to help users discover, visit, and track interesting places in their vicinity. The app provides an interactive experience by combining location-based services with photo capture and augmented reality.

## Features

- **Place Discovery**: Browse and search for interesting landmarks and locations nearby based on your current position.
- **Interactive Maps**: Full integration with Google Maps to visualize locations, set custom search radii, and navigate efficiently.
- **Visit Tracking**: Real-time location tracking that allows users to monitor their travels and "collect" visited spots.
- **Photo Capture**: Integrated CameraX support for taking photos of visited places to keep as digital memories.
- **AR Visualization**: Advanced Augmented Reality (AR) support using Sceneform to view and interact with places in a 3D space.
- **Local Persistence**: Save your favorite places and visited history directly on your device.
- **Filtering**: Customize your search results by adjusting radius and result limits.

## Tech Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Dependency Injection**: Koin
- **Local Database**: Room (with SQLite)
- **Networking**: Retrofit & OkHttp
- **Maps & Location**: Google Maps Compose, Google Play Services Location
- **Images**: Coil
- **Camera**: CameraX & ML Kit Vision
- **AR**: Sceneform
- **Architecture**: MVVM / Clean Architecture principles
- **Other**: DataStore (Preferences), Coroutines & Flow, AppIntro

## Permissions

To provide the best experience, the app requires:
- **Location**: To find places nearby and track your visits.
- **Camera**: To capture photos of the places you visit.
- **Internet**: To fetch map data and place information.
