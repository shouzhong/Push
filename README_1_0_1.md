# PUSH
## 说明
小米推送和华为推送整合
## 使用
### 依赖
华为maven
```
maven {url 'http://developer.huawei.com/repo/'}
```
```
// 基础包
implementation 'com.shouzhong:Push:1.0.1'
```
以下选择自己需要的
```
// 小米推送资源包
implementation 'com.shouzhong:PushXiaomiLib:1.0.4'
// 华为推送资源包
implementation 'com.shouzhong:PushHuaweiLib:1.0.1'
```
### 代码
在Application的onCreate中
```
/**
 * type取值：
 * PushUtils.TYPE_XIAOMI只用小米推送
 * PushUtils.TYPE_HUAWEI只用华为推送
 * PushUtils.TYPE_MIX混合模式（华为手机上用华为推送，其他用小米推送）
 */
PushUtils.init(app, type);
```
自定义广播继承BasePushReceiver
```
public class PushReceiver extends BasePushReceiver {
    /**
     *
     *
     * @param context
     * @param type PushUtils.TYPE_XIAOMI：小米推送，PushUtils.TYPE_HUAWEI：华为推送
     * @param action PushUtils.ACTION_PASS_THROUGH_MESSAGE：透传消息
     *               PushUtils.ACTION_NOTIFICATION_MESSAGE：通知栏消息，华为推送不支持
     *               PushUtils.ACTION_NOTIFICATION_MESSAGE_CLICKED：通知栏点击，华为推送不支持
     *               PushUtils.ACTION_TOKEN：小米推送regId，华为推送是token
     * @param data action对应的数据，透传消息，通知栏消息，通知栏点击为json，token为字符串
     */
    @Override
    public void onReceive(Context context, int type, String action, String data) {

    }
}
```
在AndroidManifest的application标签中注册
```
// 小米推送
<meta-data
    android:name="MIPUSH_APP_ID"
    android:value="\你的小米推送appId"/>
<meta-data
    android:name="MIPUSH_APP_KEY"
    android:value="\你的小米推送appKey"/>

// 华为推送
<meta-data
    android:name="com.huawei.hms.client.appid"
    android:value="appid=你的华为推送appId"/>

// 自定义广播，用于接收推送消息
<receiver android:name="com.shouzhong.push.demo.PushReceiver"
    android:permission="${applicationId}.permission.PUSH_RECEIVE">
    <intent-filter >
        <action android:name="${applicationId}.RECEIVE_PUSH_MESSAGE"/>
    </intent-filter>
</receiver>
```
### 方法说明

PushUtils

方法名 | 说明
------------ | -------------
reconnect | 重新连接
getAllAlias | 获取所有别名
isAlias | 是不是别名
setAlias | 设置别名
unsetAlias | 取消别名
clearAlias | 清空别名
getAllUserAccount | 获取所有账户
isUserAccount | 是不是账户
setUserAccount | 设置账户
unsetUserAccount | 取消账户
clearUserAccount | 清空账户

## 混淆
```
-keep class com.shouzhong.** {*;}
-dontwarn com.shouzhong.**
// 华为
-keepattributes *Annotation*
-keepattributes Exceptions
-keepattributes InnerClasses
-keepattributes Signature
-keepattributes SourceFile,LineNumberTable
-keep class com.hianalytics.android.**{*;}
-keep class com.huawei.updatesdk.**{*;}
-keep class com.huawei.hms.**{*;}
-keep class com.huawei.android.hms.agent.**{*;}
-dontwarn com.huawei.*
-dontwarn com.hianalytics.android.*
// 小米
-keep class com.xiaomi.** {*;}
-dontwarn com.xiaomi.push.**
-keep class com.google.protobuf.micro.**{*;}
-dontwarn com.google.protobuf.micro.**
-keep class org.apache.thrift.**{*;}
-dontwarn org.apache.thrift.**
```