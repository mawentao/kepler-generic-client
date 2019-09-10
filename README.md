Kepler-Generic-Client
------------------------------------
[![Java](https://img.shields.io/badge/Language-Java-blue.svg)](https://www.java.com/)
[![Maven](https://img.shields.io/badge/Build-Maven-green.svg)](http://maven.apache.org/)

Kepler服务泛化发包器。用命令行的方式读取文件Json格式的请求包，向Kepler服务端发送请求，并将返回包以Json格式打印。可用于Kepler服务端开发测试。


## 构建
```bash
sh build.sh
```

## 使用
```bash
sh run.sh reqpacks.txt 
```

## 请求包文件格式
- kepler请求文件定义，一行对应一个kepler请求
- 格式: serviceClassName \t serviceVersion \t serviceCatalog \t methodName \t jsonRequest

示例:
```bash
com.xxx.iface.service.FacadeService	1.0.0	dev	getHomepageInfo	[1001,"5.0.0","aabbccdd"]
```
