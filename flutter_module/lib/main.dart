import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_module/ImagPage.dart';
import 'package:flutter_module/TextPage.dart';
import 'dart:ui';

void main() => runApp(MyApp(window.defaultRouteName));

class MyApp extends StatelessWidget {
  String initParam;

  MyApp(this.initParam);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      theme: ThemeData(),
      home: MyHomePage(initParam),
      routes: <String, WidgetBuilder>{
        "image_page": (context) => ImagePage(),
        "text_page": (context) => TextPage(),
      },
    );
  }
}

class MyHomePage extends StatefulWidget {
  String initParam;

  MyHomePage(this.initParam);

  @override
  _MyHomePageState createState() => _MyHomePageState(initParam);
}

class _MyHomePageState extends State<MyHomePage> {
  String initParam;

  _MyHomePageState(this.initParam);

  /// BasicMessageChannel互相传递消息
  BasicMessageChannel<String> _basicMessageChannel =
      BasicMessageChannel('BasicMessageChannelPlugin', StringCodec());

  /// EventChannel接收Android发送来的电量
  EventChannel _eventChannelPlugin =
      EventChannel("demo.ht.com.androidproject/EventChannelPlugin");

  MethodChannel _methodChannel = new MethodChannel("MethodChannelPlugin");

  String _basicMessage; //用来接收BasicMsg消息
  String _eventMessage; //用来接收EventMes电量
  String _methodMessage; //用来获取当前给Android发送的MethodMessage数据

  @override
  void initState() {
    //使用BasicMessageChannel接受来自Native的消息，并向Native回复
    _basicMessageChannel
        .setMessageHandler((String message) => Future<String>(() {
              setState(() {
                //Android --> Flutter
                _basicMessage = 'BasicMessageChannel:' + message;
              });
              return "BasicMessageChannel收到android的消息：" + message;
            }));

    //使用Event来接收电量消息
    _eventChannelPlugin.receiveBroadcastStream().listen((event) {
      setState(() {
        _eventMessage = event;
      });
    });
    super.initState();
  }

  void _onTextChange(value) async {
    String response;
    /**
     * 在android对应的是 reply.reply()
     */
    response = await _basicMessageChannel.send(value);
    setState(() {
      _basicMessage = response;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Flutter页面"),
      ),
      body: Container(
        margin: EdgeInsets.all(30),
        child: Column(
          children: <Widget>[
            //Image页面
            RaisedButton(
              onPressed: () {
                Navigator.of(context).pushNamed("image_page");
              },
              child: Text("点击进入Image页面"),
            ),

            SizedBox(
              height: 30,
            ),

            //Text页面
            RaisedButton(
              onPressed: () {
                Navigator.of(context).pushNamed("text_page");
              },
              child: Text("点击进入TextPge页面"),
            ),

            SizedBox(
              height: 30,
            ),

            RaisedButton(
              onPressed: () {
                _basicMessageChannel.send("我是Flutter的数据!!!");
              },
              child: Text("发送消息给native"),
            ),
            TextField(
              onChanged: _onTextChange,
              decoration: InputDecoration(
                hintText: "使用BasicMessageChannel给原生发送的消息",
              ),
            ),

            SizedBox(
              height: 30,
            ),
            Text("接收android初始化数据为: ${initParam}"),
            Text("BasicMessageChannel接收数据为: $_basicMessage"),
            Text("EventChannel接收数据为: $_eventMessage"),
            TextField(
              onChanged: _onMethodChannelTextChange,
              decoration: InputDecoration(
                hintText: "使用MethodChannel给原生发送的消息",
              ),
            ),
            Text("MethodChannel接收数据为:$_methodMessage")
          ],
        ),
      ),
    );
  }

  void _onMethodChannelTextChange(String value) async {
    var content = await _methodChannel.invokeMethod("send", value);
    print("szjmethodChannel$content");

    setState(() {
      _methodMessage = content ?? "MethodMessage消息为空啦";
    });
  }
}
