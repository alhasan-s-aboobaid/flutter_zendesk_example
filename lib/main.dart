import 'package:flutter/material.dart';
import 'package:zendesk/zendesk.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return const MaterialApp(
      home: HomePage(),
    );
  }
}

class HomePage extends StatefulWidget {
  const HomePage({Key? key}) : super(key: key);

  @override
  State<HomePage> createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(child: GestureDetector(
        onTap: () async {
          await ZendeskHelper.initialize("324324235352", "hfhfg453535435");
          await ZendeskHelper.setVisitorInfo(
            name: "ffffffff",
            phoneNumber: "123",
          );
          await ZendeskHelper.startChat(
            primaryColor: Colors.red
          );
        },
        child: Text("Start chat"),
      ),),
    );
  }
}
