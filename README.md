# ChatServer

Always wondered how online chats work, so I decided make one myself. It's pretty basic rn, but anyway, have fun.

## Download

Latest Windows build: [ChatServer.exe](https://github.com/DariaZakr/Chat_Server/releases/latest/download/ChatServer.exe)

Works on Windows 10/11. Java 11 or newer should be installed.

## How to run

1. Download `ChatServer.exe`.
2. Open it.
3. Connect from another terminal:

```bash
telnet localhost 1234
```

or:

```bash
nc localhost 1234
```

You can open a few clients and send messages between them.

## Run from source

```bash
javac -d out src/*.java
java -cp out ChatServer
```
