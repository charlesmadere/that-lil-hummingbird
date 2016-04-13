# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/cmadere/SDKs/android-sdk-macosx/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Crashlytics
## Preserving content that Crashlytics needs to collect useful reports.
-keepattributes SourceFile,LineNumberTable,*Annotation*
-keep public class * extends java.lang.Exception
## Disabling proguard on Crashlytics to speed up builds.
-keep class com.crashlytics.** { *; }
-dontwarn com.crashlytics.**

# Retrofit
-dontwarn retrofit.**
-keep class retrofit.** { *; }
-keepattributes Signature
-keepattributes Exceptions

# Butterknife
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }
-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}
-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}
