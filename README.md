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
implementation 'com.shouzhong:Push:1.1.1'
```
以下选择自己需要的
```
// 小米推送资源包，请在本项目或者官网下载
// oppo推送资源包，请在本项目或者官网下载
// vivo推送资源包，请在本项目或者官网下载
// 华为推送资源包
implementation 'com.huawei.hms:push:4.0.2.300'
// 魅族推送资源包
implementation 'com.meizu.flyme.internet:push-internal:3.9.0'
```
### 代码
在Application的onCreate中
```
/**
 * @param type 取值如下，多个请用|，如：Constants.TYPE_XIAOMI | Constants.TYPE_HUAWEI
 *        Constants.TYPE_XIAOMI：小米推送
 *        Constants.TYPE_HUAWEI：华为推送
 *        Constants.TYPE_OPPO：oppo推送
 *        Constants.TYPE_VIVO：vivo推送
 *        Constants.TYPE_MEIZU：魅族推送
 * @param defaultType 如果无法找到该手机对应的推送，默认使用哪个，取值同type，只能选一个
 */
PushUtils.init(context, type, defaultType);
```
自定义广播继承BasePushReceiver
```
public class PushReceiver extends BasePushReceiver {
    /**
     * @param context
     * @param type Constants.TYPE_XIAOMI：小米推送
     *             Constants.TYPE_HUAWEI：华为推送
     *             Constants.TYPE_OPPO：oppo推送
     *             Constants.TYPE_VIVO：vivo推送
     *             Constants.TYPE_MEIZU：魅族推送
     * @param action Constants.ACTION_PASS_THROUGH_MESSAGE：透传消息
     *               Constants.ACTION_NOTIFICATION_MESSAGE：通知栏消息
     *               Constants.ACTION_NOTIFICATION_MESSAGE_CLICKED：通知栏点击
     *               Constants.ACTION_TOKEN：推送唯一标识
     * @param data action对应的数据，透传消息，通知栏消息，通知栏点击为json，token为字符串，json格式如下，不能保证每个字段都有值
     *             {"title":"","content":"","description":"","message_id":"","notify_id":1,"extra":{"key":"value",...}}
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
    android:name="XIAOMI_PUSH_APP_ID"
    android:value="value=xxxxxx"/>
<meta-data
    android:name="XIAOMI_PUSH_APP_KEY"
    android:value="value=xxxxxx"/>

// 华为推送
<meta-data
    android:name="HUAWEI_PUSH_APP_ID"
    android:value="value=xxxxx"/>

// oppo推送
<meta-data
    android:name="OPPO_PUSH_APP_KEY"
    android:value="value=xxxxx"/>
<meta-data
    android:name="OPPO_PUSH_APP_SECRET"
    android:value="value=xxxxx"/>

// vivo推送
<meta-data
    android:name="com.vivo.push.app_id"
    android:value="xxxxx"/>
<meta-data
    android:name="com.vivo.push.api_key"
    android:value="xxxxx"/>

// 魅族推送
<meta-data
    android:name="MEIZU_PUSH_APP_ID"
    android:value="value=xxxxxx"/>
<meta-data
    android:name="MEIZU_PUSH_APP_KEY"
    android:value="value=xxxxxx"/>

// 自定义广播，用于接收推送消息
<receiver android:name="com.shouzhong.push.demo.PushReceiver"
    android:permission="${applicationId}.permission.PUSH_RECEIVE">
    <intent-filter >
        <action android:name="${applicationId}.RECEIVE_PUSH_MESSAGE"/>
    </intent-filter>
</receiver>
```
### 方法说明

PushUtils，以下方法并不是每个推送都支持，请参考源码

方法名 | 说明
------------ | -------------
getToken | 获取推送唯一标识，请在子线程运行
turnOnPush | 开启推送
turnOffPush | 关闭推送
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
setTopic | 设置主题
unsetTopic | 删除主题
isTopic | 是不是主题
getAllTopic | 获取主题
clearTopic | 清空主题
setTag | 设置标签
unsetTag | 删除标签
clearTag | 清空标签

## 混淆
代码已为您混淆，如果使用AndResGuard，请把以下加入白名单
```
"R.string.agc*",
"R.string.hms*",
"R.string.connect_server_fail_prompt_toast",
"R.string.getting_message_fail_prompt_toast",
"R.string.no_available_network_prompt_toast",
"R.string.third_app_*",
"R.string.upsdk_*",
"R.layout.hms*",
"R.layout.upsdk_*",
"R.drawable.upsdk*",
"R.color.upsdk*",
"R.dimen.upsdk*",
"R.style.upsdk*"
```