# 玄子思维导图 

[![玄子思维导图](https://xuanzi.ltd/styles/xzmind.png "玄子思维导图")](http://xzmind.xuanzi.ltd "玄子思维导图")

### 应用简介
国产开源开箱即用的在线思维导图笔记应用。

### 使用方法
- 访问[玄子思维导图主页](https://xzmind.xuanzi.ltd "应用访问地址")（稳定版本）
- 访问[开源项目托管主页](http://xuanzi_code.gitee.io/xzmind "应用访问地址")（最新版本）

### 主要特性
- 支持在Markdown中嵌入思维导图；
- 支持通过文本方便修改思维导图内容；
- 支持离线和在线管理本机的脑图文档；
- 支持主流浏览器开箱即用随时随地编写脑图和文档，无需安装下载；
- 编写的文档可以通过选择-复制，然后粘贴到微信公众号等编辑器中使用；
- 支持“标准”Markdown / CommonMark和Github风格的语法，也可变身为代码编辑器；
- 支持实时预览、图片（跨域）上传、预格式文本/代码/表格插入、代码折叠、搜索替换、只读模式、自定义样式主题和多语言语法高亮等功能；
- 支持ToC（Table of Contents）、Emoji表情、Task lists、@链接等Markdown扩展语法；
- 支持TeX科学公式（基于KaTeX）、流程图 Flowchart 和 时序图 Sequence Diagram;


### 联系方式
- [开源项目首页](https://gitee.com/xuanzi_code/xzmind "开源项目首页")
- [应用访问地址](http://xzmind.xuanzi.ltd "应用访问地址")
- [开发者主页](https://xuanzi.ltd "开发者主页")
- 开发者交流Q群: 345694159
- 电子邮箱 : app@sbaike.com

### 主要使用到的开源项目
- [XZMindMap](https://gitee.com/xuanzi_code/XZMindMap "XZMindMap")
- [IndexedFSView](https://gitee.com/xuanzi_code/IndexedFSView "IndexedFSView")
- [IndexeddbFS](https://gitee.com/xuanzi_code/IndexeddbFS "IndexeddbFS")
- [Indexeddb_gwt](https://gitee.com/xuanzi_code/Indexeddb_gwt "Indexeddb_gwt")
- [Editor.md](https://pandao.github.io/editor.md/ "Editor.md")


### 嵌入思维导图1

``` 
 ```mind
:逻辑图
样例 : 在MD文档中插入思维导图
	第一项 : 样例节点
		子节点
		子节点
	第二项 : 样例节点
	第三项 : 样例节点
 ```
```

> 说明：用TAB键划分节点层次

** 渲染的显示效果 **

![sample-1](https://xzmind.xuanzi.ltd/assets/samples/sample-1.svg)

### 嵌入思维导图2
说明：用TAB键划分节点层次

```
 ```mind
:树杈图
百科词条:中华人民共和国
	首都:北京
		故宫
		天安门
		鸟巢
	面积:960万平方公里
		陆地面积960万平方公里
		领海约470万平方公里
	主要城市
		天津
		江城:武汉
		成都
		花城:广州
	主要节日:春节\n元宵节\n清明节\n端午节\n中秋节
	---文化思想
		诸子百家
		汉服文化
		唐诗宋词
 ```
```

** 渲染的显示效果 **

![sample-2](https://xzmind.xuanzi.ltd/assets/samples/sample-2.svg)

### 常用技巧
- 支持MD语法输入，例如以下示例创建一幅简单的思维导图：

 ```
    ```mind
    :逻辑图
    样例 : 在MD文档中插入思维导图
    	第一项 : 样例节点
    		子节点
    		子节点
    	第二项 : 样例节点
    	第三项 : 样例节点
    ````
````

- 思维导图每一个节点已换行分隔，节点级别已TAB键符为层次划分，下级比上级多一个TAB键符。

### 项目未来路线图
- 支持更多思维导图结构布局
- 支持设置节点颜色
- 支持导入和导出文档
- 支持多设备客户端，如PC、安卓、iphone、微信小程序等
- 支持文件同步

### 项目编译方法
- 这是基于GWT插件的项目，请先在eclipse中安装GWT开发插件。
- 下载本项目的源代码，并导入eclipse项目即可。
- 编译思维导图渲染和文件管理模块等源码请在以下项目中下载，编译方法同上：
- [XZMindMap](https://gitee.com/xuanzi_code/XZMindMap "XZMindMap")
- [IndexedFSView](https://gitee.com/xuanzi_code/IndexedFSView "IndexedFSView")
- [IndexeddbFS](https://gitee.com/xuanzi_code/IndexeddbFS "IndexeddbFS")
- [Indexeddb_gwt](https://gitee.com/xuanzi_code/Indexeddb_gwt "Indexeddb_gwt")
