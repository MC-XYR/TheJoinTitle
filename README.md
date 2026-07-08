# TheJoinTitle

自定义玩家加入消息的 Minecraft Paper 插件，支持 1.21.11。

## 功能

- 自定义全体玩家加入消息
- 自定义特定玩家加入消息（统一消息）
- 自定义特定玩家加入消息（每人单独消息）
- 游戏内指令添加/移除/开关
- 多语言支持，可自定义语言文件

## 指令

| 指令 | 说明 |
|------|------|
| `/thejointitle add <玩家> <消息>` | 添加特定玩家的加入消息 |
| `/thejointitle remove <玩家>` | 移除特定玩家的加入消息 |
| `/thejointitle toggle <all\|specificall\|specific>` | 开关对应功能 |
| `/thejointitle language <语言代码>` | 切换语言 |
| `/thejointitle about` | 查看插件信息 |

别名：`/tjt`、`/jointitle`、`/玩家加入标题`

## 配置文件

`config.yml`：

```yaml
#TheJoinTitle配置文件
#支持格式化代码（颜色代码）
#各个配置的等级specific＞specificall＞all


#这个是语言默认zh_cn，语言文件可以自定义，安装只需把语言文件直接放到language文件夹然后在config.yml中切换。原本的zh_cn和en_us不用动。别的语言的翻译可以交给ai。
language: zh_cn


#这里是加入后自定义提示消息，消息示例：更新公告：移除了herobrine
#all是是否更改全部玩家的加入消息；on打开off关闭；默认off
all:
  enabled: off
  message: "更新公告：移除了herobrine"


#这个是给特定玩家的自定义提示消息开关
#下面的是自定义提示消息的玩家；示例herobrine和notch
#这个是 没办法 单独设置 特定玩家 特定加入消息 的
specificall:
  enabled: off
  message: "更新公告：移除了herobrine"
  players:
    - herobrine
    - notch


#这个是给特定玩家的自定义提示消息开关
#下面的是自定义提示消息的玩家；示例dog和MC_XYR
#这个是 可以 单独设置 特定玩家 特定加入消息 的；格式："玩家名:自定义提示消息"
specific:
  enabled: off
  players:
    - "dog:&c汪汪汪"
    - "MC_XYR:我是作者，我添加了114514个高危漏洞[doge]"
