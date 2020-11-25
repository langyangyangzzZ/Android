import 'package:flutter/material.dart';
import 'package:flutter_module/ImagPage.dart';
import 'package:flutter_module/TextPage.dart';
import 'dart:ui';

void main() => runApp(MyApp(window.defaultRouteName));

class MyApp extends StatelessWidget {

  String ImageTitle;

  MyApp(this.ImageTitle);

  @override
  Widget build(BuildContext context) {
    print("szjbuild${ImageTitle}");

    return MaterialApp(
      theme: ThemeData(
      ),
      home: MyHomePage(),
      routes: <String,WidgetBuilder>{
        "image_page" : (context) => ImagePage(ImageTitle),
        "text_page" : (context) => TextPage(),
      },
    );
  }
}

class MyHomePage extends StatefulWidget {

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {

  _MyHomePageState();

  String _title;
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
            InkWell(
              onTap: (){
                Navigator.of(context).pushNamed("image_page");
              },
              child: Text("点击进入ImagePge页面"),
            ),

            SizedBox(
              height: 30,
            ),
            InkWell(
              onTap: (){
                Navigator.of(context).pushNamed("text_page");
              },
              child: Text("点击进入TextPge页面"),
            )

          ],
        ),
      ),
    );
  }

}
