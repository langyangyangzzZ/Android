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
      theme: ThemeData(
      ),
      home: MyHomePage(initParam),
      routes: <String,WidgetBuilder>{
        "image_page" : (context) => ImagePage(),
        "text_page" : (context) => TextPage(),
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
        showMessage = 'BasicMessageChannel:'+message;
      });
      return "收到Native的消息：" + message;
    }));
    super.initState();
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
              onPressed: (){
                Navigator.of(context).pushNamed("image_page");
              },
              child: Text("点击进入Image页面"),
            ),

            SizedBox(
              height: 30,
            ),

            //Text页面
            RaisedButton(
              onPressed: (){
                Navigator.of(context).pushNamed("text_page");
              },
              child: Text("点击进入TextPge页面"),
            ),


            SizedBox(
              height: 30,
            ),

            RaisedButton(onPressed: () {
              _basicMessageChannel.send("我是Flutter的数据!!!");
            },
            child: Text("发送消息给native"),
            ),
            Text("${initParam}")

          ],
        ),
      ),
    );
  }

}
