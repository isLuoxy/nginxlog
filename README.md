### 一个关于 nginx 日志可视化的工具



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