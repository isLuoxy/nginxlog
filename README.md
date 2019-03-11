### 一个关于 nginx 日志可视化的工具



:happy: <a href="#v1.0.1">v1.0.1</a>

:slightly_smiling_face: <a href="#v1.0.0">v1.0.0</a>



## v1.0.1

在该版本中支持以绝对路径访问 日志文件。

使用方法：

1. 下载项目到本地

2. 在 pom.xml 同级目录使用命令行 mvn clean package，会在 /target 生成对应的 jar 包

3. 可以将 jar 包移到任何一个位置

4. 创建一个文件名为 *application.properties* 的配置文件，内容为

   ~~~properties
   log.location=xxx
   ~~~

   > ps：
   >
   > 1、这里的xxx 是日志的绝对路径名，一般是 **F://log//text.log**  ( 因为在 java 中/是转义字符，所以要 // 表示正确路径，一定要双斜杠 )
   >
   > 2、日志的绝对路径**不能包含中文，不能包含中文，不能包含中文**

5. 将4步骤创建的配置文件跟 jar 包放在同一目录下

6. 在该级使用命令行启动 jar 包

   ~~~base
   java -jar jarName application.properties
   ~~~

7. 访问 **localhost:8080** 看下成果





## v1.0.0

1. 下载项目到本地

2. 在 pom.xml 同级中使用命令行 mvn clean package ，会在 /target 生成对应的 jar 包

3. 可以将 jar 包移到任何一个位置 

4. 创建一个文件名为 *application.properties* 的配置文件，内容为

   ~~~properties
   log.location = xxx
   ~~~

   **其中的 xxx 为你的 nginx 日志名称**

5. 4 步骤创建的配置文件和你的日志文件放在与 jar 包同级下

6. 在该级命令行启动 jar 包

   ~~~cmd
   java -jar jarName application.properties
   ~~~

7. 不妨 **localhost:8080** 看下成果