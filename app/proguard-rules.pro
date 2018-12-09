# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-dontwarn **

-keep class me.jessyan.autosize.** { *; }
-keep interface me.jessyan.autosize.** { *; }

# 设置混淆的压缩比率
-optimizationpasses 7
# Aa aA
-dontusemixedcaseclassnames
# 如果应用程序引入的有jar包,并且想混淆jar包里面的class
-dontskipnonpubliclibraryclasses
# 混淆前认证，可去掉加快混淆速度
-dontpreverify
# 混淆的log 帮助排错
-verbose
# 混淆采用的算法，一般不改变，用谷歌推荐算即可
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
-dontwarn android.webkit.WebView
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
    public *;
}
# 和javascript交互的方法不能混淆
-keepattributes JavascriptInterface
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}
-keepclassmembers class * extends android.webkit.WebChromeClient {
    public *;
}

# 不混淆注解
-keepattributes *Annotation*

#  不混淆签名
-keepattributes Signature

-keepclasseswithmembernames class * {
    native <methods>;
}
-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSett, int);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context);
}


# 第三方类库不混淆
# javax
-dontwarn javax.annotation.**

# Gradle Retrolambda Plugin
# https://github.com/evant/gradle-retrolambda#user-content-proguard
-dontwarn java.lang.invoke.*

# 错误和内部类不混淆
-keepattributes Exceptions,InnerClasses

-dontwarn org.apache.**
-keep class org.apache.** { *; }

# Remove logging calls
-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int v(...);
    public static int i(...);
    public static int w(...);
    public static int d(...);
    public static int e(...);
}

# bean 类不要混淆
-keep class com.zhixingjia.*.bean.** { *; }

# viewmodel 类不要混淆
-keep class com.zhixingjia.*.viewmodel.** { *; }

# 自定义view 类不要混淆
-keep class com.zhixingjia.*.view.** {*;}

# 四大组件不混淆
-keep public class * extends android.app.**
-keep public class * extends android.content.**
-keep public class * extends android.preference.Preference
-keep public class * extends android.support.**

# start databinding
-dontwarn android.databinding.**
-keep class android.databinding.** { *; }
-keep class com.zhihangjia.*.binding.** { *; }
-keep class com.nmssdmf.*.binding.** { *; }
-keep class * extends android.databinding.ViewDataBinding {*;}
-keep class com.zhihangjia.*.BR {*;}
-keep class com.nmssdmf.*.BR {*;}
# end databinding

#表示不混淆任何一个View中的setXxx()和getXxx()方法，
#因为属性动画需要有相应的setter和getter的方法实现，混淆了就无法工作了。
-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

# 保留R下面的资源
-keep class **.R$* {
 *;
}

# star retrofit
-dontwarn rx.**
-dontwarn com.squareup.okhttp.**
-dontwarn retrofit.appengine.UrlFetchClient

-keep class com.squareup.okhttp.** { *; }
-keep interface com.squareup.okhttp.** { *; }
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

-dontwarn javax.inject.**
# OkHttp3
-dontwarn okhttp3.logging.**
-keep class okhttp3.internal.**{*;}
-dontwarn okio.**
# Retrofit
-dontnote retrofit2.**
-keep class retrofit2.** { *; }
# RxJava RxAndroid
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

# Gson
-keep class com.google.gson.stream.** { *; }
-keepattributes EnclosingMethod
# end retrofit