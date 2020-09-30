# consul
## consul安装
去官网https://www.consul.io/ 下载最新版本或者使用项目里的安装包
将consul_1.8.0_linux_amd64.zip copy至linux后进行安装并运行

```
#将安装包解压至指定目录
unzip consul_1.8.0_linux_amd64.zip -d /usr/local/consul
cd /usr/local/consul
sudo nohup ./consul agent -dev -ui -client 0.0.0.0 &
```
访问 ip:8500 界面显示表明运行成功

### 通过docker安装
```
docker pull consul
docker run -d --name consul -p 8500:8500 consul agent -dev -client 0.0.0.0 -ui
```

## git2consul安装
```
curl --silent --location https://rpm.nodesource.com/setup_14.x | sudo -E bash -
sudo yum -y install nodejs
```
如果yum下载失败的话执行以下代码后在重试
```
sudo yum clean all
```
如果存在多个 nodesoucre，执行以下命令删除，然后重新执行第一第二步：
```
sudo rm -fv /etc/yum.repos.d/nodesource*
```
接下来安装git2consul
通过阿里下载
```
npm install -g git2consul --registry=https://registry.npm.taobao.org
```
下载成功后创建git2consul的配置文件
```
mkdir /usr/local/gitconsul
cd /usr/local/gitconsul
vi git2consul.json
```
将以下内容复制进去并做修改
version  配置版本
name 名称，指的是在consul里面的目录名称
url 要同步的Git仓库
branches 要同步的分支
hooks.type 拉取模式
hooks.interval 同步的间隔（分钟） 只能输入整数
```
{
  "version": "1.0",
  "repos": [
    {
      "name": "config",
      "url": "你的git仓库",
      "branches": [
        "master"
      ],
      "include_branch_name": false,
      "hooks": [
        {
          "type": "polling",
          "interval": "1"
        }
      ]
    }
  ]
}
```
设置服务器记住git地址账号密码
```
git config --global credential.helper store
```
启动git2consul后第一次需要输入账号密码后面就不需要在输入
```
git2consul --config-file /usr/local/git2consul/git2consul.json
```
录入账号密码后看运行日志启动成功后在consul上可以看到配置后可以换成守护进程形式启动
```
nohup git2consul --config-file /usr/local/git2consul/git2consul.json &
```