## Android通用框架设计与完整电商APP开发
---
* gogs 搭建git
* 单Activity架构,通过 fragmentation库实现
* AVLoadingIndicatorView 加载动画库
* Android-ConvenientBanner 滑动库
*  android-iconify 图标库  [fontzwesome图标搜索库](http://fontawesome.io/icons/)
* 完成微信登录功能(由于未注册账号,无法测试)

* 使用IndexBean时无法监听宽度(待测试 )

* 异常 点击事件混乱 (shopcartdelegate)

*(暂不实现支付)

* 方法数超上限
    > 在build.gradle 的android{}内添加
        ```   dexOptions {
                   jumboMode true
               }
         ```

* 依赖倒置
    >反射,接口,消息机制
    
---
### 9/7 
 >初步完成,缺少 贝塞尔曲线动画,分享,第三方登录
[下载链接](./outputs/example-release.apk)