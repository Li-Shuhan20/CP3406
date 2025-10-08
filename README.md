# AndroidStarter

最小可运行的安卓应用框架（Kotlin + ViewBinding + Material3）。

## 要求
- Android Studio（含 Android Gradle Plugin 8+）
- JDK 17

## 快速开始
1. 使用 Android Studio 打开项目目录。
2. 同步 Gradle（必要时让 IDE 生成 `local.properties`）。
3. 运行 `app` 模块到模拟器或真机。

## 结构
- 根：`settings.gradle`、`build.gradle`、`gradle.properties`
- 模块 `app/`
  - `build.gradle.kts`
  - `src/main/AndroidManifest.xml`
  - `src/main/java/com/example/androidstarter/MainActivity.kt`
  - `src/main/res/layout/activity_main.xml`
  - `src/main/res/values/{strings,colors,themes}.xml`

## 许可证
MIT
