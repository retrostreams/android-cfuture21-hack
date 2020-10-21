[![Maven Central](https://img.shields.io/maven-central/v/net.sourceforge.streamsupport/android-cfuture21-hack.svg)](http://mvnrepository.com/artifact/net.sourceforge.streamsupport/android-cfuture21-hack)
[![javadoc.io](https://javadoc.io/badge2/net.sourceforge.streamsupport/android-cfuture21-hack/javadoc.svg)](https://javadoc.io/doc/net.sourceforge.streamsupport/android-cfuture21-hack)

# android-cfuture21-hack

An unsupported hackish attempt to provide CompletableFuture on Android API level 21 to level 23 in conjunction with google/desugar_jdk_libs and Android Gradle Plugin 4.x

Android Studio will display an error "Call requires API level 24 (current min is 21)". This error is just a warning that can be ignored. If you want to get rid of the error, just add `@SuppressLint("NewApi")` to your code.

Use at your **own risk**. This hack **won't** get support in any way!  


### app/build.gradle:

```groovy
apply plugin: 'com.android.application'


android {

    defaultConfig {
        ...
        minSdkVersion 21 // has to be >= 21 and < 24, if you have 24+ this hack is not needed
        ...
    }

    compileOptions {
        coreLibraryDesugaringEnabled true
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
}

dependencies {
    coreLibraryDesugaring 'com.android.tools:desugar_jdk_libs:1.0.10'
    implementation 'net.sourceforge.streamsupport:android-cfuture21-hack:1.0.2'
}
```

## LICENSE

GNU General Public License, version 2, [with the Classpath Exception](https://github.com/retrostreams/android-cfuture21-hack/blob/master/LICENSE)  (and [CC0 1.0](https://creativecommons.org/publicdomain/zero/1.0/) for JSR-166 derived code)
