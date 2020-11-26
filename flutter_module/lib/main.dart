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

  BasicMessageChannel<String> _basicMessageChannel =
      BasicMessageChannel('BasicMessageChannelPlugin', StringCodec());

  String showMessage;

  @override
  void initState() {
    //使用BasicMessageChannel接受来自Native的消息，并向Native回复
    _basicMessageChannel
        .setMessageHandler((String message) => Future<String>(() {
              setState(() {
                //Android --> Flutter
                showMessage = 'BasicMessageChannel:' + message;
              });
              return "BasicMessageChannel收到android的消息：" + message;
            }));
    super.initState();
  }

  void _onTextChange(value) async {
    String response;
    /**
     * 在android对应的是 reply.reply()
     */
    response = await _basicMessageChannel.send(value);
    setState(() {
      showMessage =  response ;
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
                hintText: "请输入给原生发送的消息",
              ),
            ),

            SizedBox(
              height: 30,
            ),
            Text("接收android初始化数据为: ${initParam}"),
            Text("BasicMessageChannel接收android原生数据为: ${showMessage}"),
          ],
        ),
      ),
    );
  }
}
