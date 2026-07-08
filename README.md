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
language: zh_cn

all:
  enabled: off
  message: "§e欢迎回来"

specificall:
  enabled: off
  message: "更新公告：移除了herobrine"
  players:
    - herobrine
    - notch

specific:
  enabled: off
  players:
    - "dog:汪汪汪"
    - "MC_XYR:我是作者"
