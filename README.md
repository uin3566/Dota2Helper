## 掌刀
一款提供dota2游戏相关资讯，视频，攻略，更新日志的Android App，基于Retrofit&RxJava&Mvp&GreenDao

## 效果图
 ![screenshot](/screenshots/screenshot.jpg)
 ![screenshot](/screenshots/screengif.gif)

## App下载
[下载地址](http://fir.im/dota2helper)  
![image](/screenshots/download.png)

## 描述
App数据均来源于网络，接口都由自己写的后台提供，后台在[这儿](https://github.com/uin3566/Dota2Server)，使用python编写，由爬虫进程与服务进程组成，部署在百度BAE平台，爬虫每隔一段时间爬取最新的资讯，视频，攻略，更新数据并存入数据库，服务进程等待客户端请求，当有客户端请求时，去数据库取数据并转换成json格式返回给客户端。  

资讯的展示使用webview控件，内容是后台返回的html，这个html经过了后台处理以适应手机屏幕。  

视频采用优酷sdk播放，需要传入视频vid，视频vid是由后台爬虫抓取并存入数据库，在客户端请求视频列表时返回给客户端的。不过优酷sdk里面的播放器控制界面太丑了，我就改掉了，已经看不到带有优酷logo的控制图标了^_^，优酷sdk在这http://cloud.youku.com/down/play

## 特点
* 视频播放，播放记录，视频缓存功能
* 基于Retrofit&RxJava&Mvp&GreenDao，代码结构清晰，易读
* 不依赖于第三方数据接口，均由自己的后台提供，除了获取视频信息的优酷api
* Material Design

## 后记
* 其实在很早就想做一个自己的app了，不过一直没啥好的想法，因为没有后台，总是想着做一个单机的+﹏+，这次就尝试一下自己写后台，给客户端提供数据，做一个能联网的app！作为一个资深dota玩家，自然就打起了dota的主意，从而诞生了“掌刀”这个app。
* 感谢Github上各位大牛的第三方库，所用到的第三方库已在下面列出。
* 应用中的图标，大多来自[iconfont](http://www.iconfont.cn/)，很方便的网站，选好图标后能自定义size和color，在此表示感谢。
* 最后，希望大家多多star，多多follow，这对我也是一种鼓励呀^_^！

## 第三方库
* [Retrofit](https://github.com/square/retrofit)
* [RxAndroid](https://github.com/ReactiveX/RxAndroid)
* [Gson](https://github.com/google/gson)
* [GreenDao](https://github.com/greenrobot/greenDAO)
* [ButterKnife](https://github.com/JakeWharton/butterknife)
* [Glide](https://github.com/bumptech/glide)
* [FlycoBanner](https://github.com/H07000223/FlycoBanner_Master)
* [SwipeToLoadLayout](https://github.com/Aspsine/SwipeToLoadLayout)
* [PullZoomView](https://github.com/Frank-Zhu/PullZoomView)
* [sticky-headers-recyclerview](https://github.com/timehop/sticky-headers-recyclerview)
* [SmartTabLayout](https://github.com/ogaclejapan/SmartTabLayout)
* [PickerView](https://github.com/saiwu-bigkoo/Android-PickerView)
* [MaterialLoadingProgressBar](https://github.com/lsjwzh/MaterialLoadingProgressBar)
* [SystemBarTint](https://github.com/jgilfelt/SystemBarTint)
* [leakcanary](https://github.com/square/leakcanary)

## License
```
Copyright (c) 2016 uin3566 <xufang2@foxmail.com>

Licensed under the Apache License, Version 2.0 (the "License”);
you may not use this file except in compliance with the License.
You may obtain a copy of the License at
   
   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
