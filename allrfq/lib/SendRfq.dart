import 'package:flutter/material.dart';

class SendRFQ extends StatelessWidget {
  const SendRFQ({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      home: Scaffold(
        body: SendForm(),
      ),
    );
  }
}

class SendForm extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return CreateContainer();
  }
}

class CreateContainer extends State<SendForm> {
  @override
  Widget build(BuildContext context) {
    return LayoutBuilder(
      builder: (context, constraints) {
        final maxWidth = constraints.maxWidth;

        return Row(
          children: [
            Container(
              width: maxWidth,
              height: 700.0,
              decoration: BoxDecoration(
                color: Colors.white70,
                borderRadius: BorderRadius.circular(5.0),
                border: Border.all(color: Colors.grey.shade300, width: 1.0),
              ),
              child: Column(
                children: [
                  SizedBox(height: maxWidth * 0.1),
                  Row(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: [
                      Container(
                        child: Image.asset("images/success@2x.png"),
                      ),
                    ],
                  ),
                  SizedBox(height: maxWidth * 0.02),
                  Row(
                    children: [
                      Container(
                        width: maxWidth * 0.99,
                        child: const Center(
                          child: Text(
                            "Thank You",
                            style: TextStyle(
                                color: Color.fromARGB(255, 13, 218, 115),
                                fontSize: 25),
                            textAlign: TextAlign.center,
                          ),
                        ),
                      ),
                    ],
                  ),
                  SizedBox(height: maxWidth * 0.02),
                  Row(
                    children: [
                      Container(
                        width: maxWidth * 0.99,
                        child: const Center(
                          child: Text(
                            "Successfully Registered RFQ",
                            style: TextStyle(fontSize: 20),
                            textAlign: TextAlign.center,
                          ),
                        ),
                      ),
                    ],
                  ),
                ],
              ),
            ),
          ],
        );
      },
    );
  }
}