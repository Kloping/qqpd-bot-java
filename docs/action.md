## 动作文档

- send 发送消息动作
    - 所有实现[Sender](../src/main/java/io/github/kloping/qqbot/api/Sender.java)的对象(Object)
    - 常用: 通过消息事件获取的 [Message](../src/main/java/io/github/kloping/qqbot/entities/qqpd/message/Message.java)
- delete 撤回事件
    - 所有实现[DeleteAble](../src/main/java/io/github/kloping/qqbot/api/DeleteAble.java) 的对象
    - 常用: 通过消息事件获取的 [Message](../src/main/java/io/github/kloping/qqbot/entities/qqpd/message/Message.java)
- at 获取at可发送的消息
    - 所有实现[AtAble](../src/main/java/io/github/kloping/qqbot/api/AtAble.java) 的对象
