# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Applications/Android Studio.app/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the ProGuard
# include property in project.properties.
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

# Obfuscation parameters:
#-dontobfuscate
-useuniqueclassmembernames
-keepattributes SourceFile,LineNumberTable
-allowaccessmodification

# Ignore warnings:
#-dontwarn org.mockito.**
#-dontwarn org.junit.**
#-dontwarn com.robotium.**
#-dontwarn org.joda.convert.**

# Ignore warnings: We are not using DOM model
-dontwarn com.fasterxml.jackson.databind.ext.DOMSerializer
# Ignore warnings: https://github.com/square/okhttp/wiki/FAQs
-dontwarn com.squareup.okhttp.internal.huc.**
# Ignore warnings: https://github.com/square/okio/issues/60
-dontwarn okio.**
# Ignore warnings: https://github.com/square/retrofit/issues/435
-dontwarn com.google.appengine.api.urlfetch.**

-libraryjars libs

# Keep the pojos used by GSON or Jackson
-keep class com.futurice.project.models.pojo.** { *; }

# Keep GSON stuff
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.** { *; }

# Keep Jackson stuff
-keep class org.codehaus.** { *; }
-keep class com.fasterxml.jackson.annotation.** { *; }

# Keep these for GSON and Jackson
-keepattributes Signature
-keepattributes *Annotation*
-keepattributes EnclosingMethod




-keep class com.google.gson.** { *; }
-keep class com.google.inject.** { *; }
-keep class org.apache.http.** { *; }
-keep class org.apache.james.mime4j.** { *; }
-keep class javax.inject.** { *; }


# Keep Picasso
-keep class com.squareup.picasso.** { *; }
-keepclasseswithmembers class * {
    @com.squareup.picasso.** *;
}

-keep public class com.crittercism.**
-keepclassmembers public class com.crittercism.* {
    *;
}

#-keepclassmembers class ** {
#    public void onEvent*(***);
#}

# Only required if you use AsyncExecutor
#-keepclassmembers class * extends de.greenrobot.event.util.ThrowableFailureEvent {
#    <init>(java.lang.Throwable);
#}

-keep class dmax.dialog.** {
    *;
}


-keepattributes Signature
-keepattributes *Annotation*
-keep class com.squareup.okhttp.** { *; }
-keep interface com.squareup.okhttp.** { *; }
-dontwarn com.squareup.okhttp.**

-dontwarn rx.**

-keep class com.squareup.** { *; }
-keep interface com.squareup.** { *; }
-dontwarn com.squareup.okhttp.**
-keep class retrofit.** { *; }

-keepclasseswithmembers class * {
    @retrofit.http.* <methods>;
}

-keep interface retrofit.** { *;}
-keep interface com.squareup.** { *; }
-dontwarn rx.**
-dontwarn retrofit.**

-keep class sun.misc.Unsafe { *; }
#your package path where your gson models are stored
-keep class com.example.model.** { *; }

-keep class de.greenrobot.dao.** { *; }
-keep interface de.greenrobot.dao.** { *; }





# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/liang/Documents/android-sdk-macosx/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
   public *;
}

#指定代码的压缩级别
-optimizationpasses 5
#包明不混合大小写
-dontusemixedcaseclassnames
#不去忽略非公共的库类
-dontskipnonpubliclibraryclasses
#优化  不优化输入的类文件
-dontoptimize
#预校验
-dontpreverify
#混淆时是否记录日志
-verbose
# 混淆时所采用的算法
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
#保护注解
-keepattributes *Annotation*

#保持哪些类不被混淆
#-keep public class * extends android.app.Application
#-keep public class * extends android.app.AppCompatActivity
#-keep public class * extends android.app.Activity
#-keep public class * extends android.app.Fragment


-keep class de.greenrobot.dao.** { *; }
-keep interface de.greenrobot.dao.** { *; }
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.billing.IInAppBillingService
-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclassmembers class * extends android.content.Context {
    public void *(android.view.View);
    public void *(android.view.MenuItem);
}



-keepclassmembers class * {
   public <init>(org.json.JSONObject);
}

-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
}


#忽略警告
-ignorewarning
##记录生成的日志数据,gradle build时在本项目根目录输出##
#apk 包内所有 class 的内部结构
-dump class_files.txt
#未混淆的类和成员
-printseeds seeds.txt
#列出从 apk 中删除的代码
-printusage unused.txt
#混淆前后的映射
-printmapping mapping.txt

########记录生成的日志数据，gradle build时 在本项目根目录输出-end######

#如果引用了v4或者v7包
-dontwarn android.support.**

#####混淆保护自己项目的部分代码以及引用的第三方jar包library#######
##百度地图
#-libraryjars libs/baidumapapi_v3_5_0.jar
-keep class com.baidu.** {*;}
-keep class vi.com.gdi.bgl.android.**{*;}
-dontwarn com.baidu.**

-keep class com.umeng.**{*;}
-keep class com.alimama.**{*;}

-keepattributes Exceptions
## GreenRobot EventBus specific rules ##
-keepclassmembers class ** {
    public void onEvent*(**);
}
## Only required if you use AsyncExecutor
#-keepclassmembers class * extends de.greenrobot.event.util.ThrowableFailureEvent {
#    public <init>(java.lang.Throwable);
#}
-dontwarn de.greenrobot.event.util.*$Support
-dontwarn de.greenrobot.event.util.*$SupportManagerFragment

# # -------------------------------------------
# #  ######## picasso混淆  ##########
# # -------------------------------------------
-dontwarn com.squareup.okhttp.**

# # -------------------------------------------
# #  ######## fastJson混淆  ##########
# # -------------------------------------------
-dontwarn com.alibaba.fastjson.**
-keep class com.alibaba.fastjson.** { *; }
#-keepclassmembers class * {
#    public <methods>;
#}

# # -------------------------------------------
# #  ######## greenDao Generator混淆  ##########
# # -------------------------------------------
# GreenDao Generator rules
#
-keepclassmembers class * extends de.greenrobot.dao.AbstractDao {
    public static java.lang.String TABLENAME;
}
-keep class **$Properties

-keepclassmembers class * extends de.greenrobot.dao.AbstractDao {
    public static java.lang.String CuisineFilter;
    public static java.lang.String Group;
    public static java.lang.String Cuisine;
    public static java.lang.String Category;
    public static java.lang.String Products;
    public static java.lang.String IngredientsCategory;
    public static java.lang.String Ingredients;
    public static java.lang.String CartProducts;
    public static java.lang.String CartProductsIngredients;
    public static java.lang.String RestaurantBranch;
    public static java.lang.String UserAddress;
    public static java.lang.String User;
    public static java.lang.String AppSettings;
}
-keep class **$Properties

#-keepclassmembers class **$Properties {
#    public static <fields>;
#}


## GreenRobot EventBus specific rules ##
-keepclassmembers class ** {
    public void onEvent*(**);
}
## Only required if you use AsyncExecutor
#-keepclassmembers class * extends de.greenrobot.event.util.ThrowableFailureEvent {
#    public <init>(java.lang.Throwable);
#}
-dontwarn de.greenrobot.event.util.*$Support
-dontwarn de.greenrobot.event.util.*$SupportManagerFragment

-keep class com.tabaogo.model.** { *; }
-keep class com.tabaogo.modeltab.** { *; }

-adaptresourcefilenames **.xsd,**.wsdl,**.xml,**.properties,**.gif,**.jpg,**.png

-keep class android.support.v7.widget.SearchView { *; }

-keep class android.support.v4.widget.SearchView { *; }

-keep class com.sothree.slidinguppanel { *; }

-keep public class com.google.android.gms.* { public *; }
-dontwarn com.google.android.gms.**

-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

-keepattributes JavascriptInterface
-keep public class MyPaymentOnline_PayU$MyJavaScriptInterface
-keep public class * implements MyPaymentOnline_PayU$MyJavaScriptInterface
-keepclassmembers class MyPaymentOnline_PayU$MyJavaScriptInterface {
    <methods>;
}
