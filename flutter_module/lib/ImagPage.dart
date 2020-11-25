import 'package:flutter/material.dart';

import 'dart:ui';
class ImagePage extends StatefulWidget {
  String _title;

  ImagePage(this._title);

  @override
  _ImagePageState createState() => _ImagePageState(_title);
}

class _ImagePageState extends State<ImagePage> {
  String title;

  _ImagePageState(this.title);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Container(
        margin: EdgeInsets.only(top: 30),
       child: Column(
         children: [
           Text("ImagePaeg页面"),
           Container(
             child: InkWell(
               onTap: (){
                 Navigator.pop(context);
               },
               child: Icon(Icons.android,size: 50,),
             ),
           ),
         ],
       ),
      )
    );
  }
}
