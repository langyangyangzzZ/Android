import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class TextPage extends StatefulWidget {
  @override
  _TextPageState createState() => _TextPageState();
}

class _TextPageState extends State<TextPage> {
  //com.flutter.guide.EventChannel 是 EventChannel 的名称，android端要与之对应。



  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("TextPage页面"),
      ),
      body: Column(
        children: [
          Text("TextPage页面"),
          SizedBox(
            height: 30,
          ),
        ],
      ),
    );
  }
}
