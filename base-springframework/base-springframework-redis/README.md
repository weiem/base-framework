# redis配置优化
在redis中执行以下命令开启自动清理碎片配置
```
config set activedefrag yes
```
手动清理会阻塞线程不建议手动清理