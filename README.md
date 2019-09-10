Kepler泛化客户端<br>
[![Java](https://img.shields.io/badge/Language-Java-blue.svg)](https://www.java.com/)
[![Maven](https://img.shields.io/badge/Build-Maven-green.svg)](http://maven.apache.org/)
------------------------------------
# 简介
Kepler服务通用发包器。用命令行的方式读取文件Json格式的请求包，向Kepler服务端发送请求，并将返回包以Json格式打印。可用于Kepler服务端开发测试。

# 构建
`
sh build.sh
`

# 使用
`
sh run.sh reqpacks.txt 
`

# 请求包文件格式
- kepler请求文件定义，一行对应一个kepler请求
- 格式: serviceClassName \t serviceVersion \t serviceCatalog \t methodName \t jsonRequest

示例:
`
com.xxx.iface.service.FacadeService	1.0.0	dev	getHomepageInfo	[1001,"5.0.0","aabbccdd"]
`
