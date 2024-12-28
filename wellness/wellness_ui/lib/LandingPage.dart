import 'package:flutter/material.dart';
import 'package:wellness_ui/login_screen.dart';

class LandingPage extends StatefulWidget {
  const LandingPage({super.key});

  @override
  State<LandingPage> createState() => _LandingPageState();
}

class _LandingPageState extends State<LandingPage> {
  @override
  Widget build(BuildContext context) {
    double screenWidth = MediaQuery.of(context).size.width;
    double screenHeight = MediaQuery.of(context).size.height;
    return Scaffold(
      body: LayoutBuilder(
        builder: (context, constraints) {
          if (constraints.maxWidth < 600) {
            // Mobile layout
            return buildMobileLayout();
          } else {
            // Tablet and larger layout
            return buildWebLayout();
          }
        },
      ),
    );
  }

  Widget buildMobileLayout() {
    double screenWidth = MediaQuery.of(context).size.width;
    double maxHeight = MediaQuery.of(context).size.height;
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.white,
        toolbarHeight: maxHeight * 0.13,
        title: Row(
          children: [
            Padding(
              padding: EdgeInsets.only(left: screenWidth * 0.01),
              child: Image.asset(
                "assets/images/logo_wellness.png",
                width: 60,
                height: 50,
              ),
            ),
            Padding(
                padding: EdgeInsets.only(left: screenWidth * 0.3),
                child: const Icon(Icons.menu,
                    color: Color.fromRGBO(113, 114, 111, 1)))
          ],
        ),
      ),
      body: SingleChildScrollView(
        child: Column(
          children: [
            Container(
              color: const Color.fromRGBO(110, 201, 241, 1),
              height: maxHeight * 0.30,
              child: const Row(
                children: [
                  Column(
                    children: [
                      Text(
                        '''Get Insurance Policy 
and Save 20%!''',
                        style: TextStyle(color: Colors.white),
                      )
                    ],
                  )
                ],
              ),
            )
          ],
        ),
      ),
    );
  }

  Widget buildWebLayout() {
    double screenWidth = MediaQuery.of(context).size.width;
    double screenHeight = MediaQuery.of(context).size.height;
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.white,
        toolbarHeight: screenHeight * 0.112,
        title: SingleChildScrollView(
          scrollDirection: Axis.horizontal,
          child: Row(
            children: <Widget>[
              Container(
                child: Padding(
                    padding: EdgeInsets.only(
                      left: screenWidth * 0.01,
                    ),
                    child: Image.asset(
                      "assets/images/logo_wellness.png",
                    )),
              ),
              Container(
                child: Padding(
                  padding: EdgeInsets.only(right: screenWidth * 0.01),
                  child: TextButton(
                    onPressed: () {
                      // Add your button press logic here
                    },
                    child: const Text(
                      'PRICING',
                      style: TextStyle(
                        color: Color.fromRGBO(32, 37, 41, 1.0),
                      ),
                    ),
                  ),
                ),
              ),
              Container(
                padding: EdgeInsets.only(
                  left: screenWidth * 0.0,
                ),
                child: TextButton(
                  onPressed: () {
                    // Add your button press logic here
                  },
                  child: const Text('GET A QUOTE',
                      style:
                      TextStyle(color: Color.fromRGBO(32, 37, 41, 1.0))),
                ),
              ),
              Container(
                child: Padding(
                  padding: EdgeInsets.only(right: screenWidth * 0.01),
                  child: TextButton(
                    onPressed: () {
                      // Add your button press logic here
                    },
                    child: const Text(
                      'GET IN TOUCH',
                      style: TextStyle(color: Color.fromRGBO(32, 37, 41, 1.0)),
                    ),
                  ),
                ),
              ),
              Container(
                child: Padding(
                  padding: EdgeInsets.only(left: screenWidth * 0.02),
                  child: SizedBox(
                    width: 160,
                    height: 50,
                    child: ElevatedButton(
                      onPressed: () {
                        Navigator.push(
                          context,
                          MaterialPageRoute(builder: (context) => const LoginScreen()),
                        );
                      },
                      style: ElevatedButton.styleFrom(
                        backgroundColor: Colors.white,
                        shape: RoundedRectangleBorder(
                          borderRadius: BorderRadius.circular(50),
                        ),
                      ),
                      child: const Row(
                        mainAxisAlignment: MainAxisAlignment.center,
                        children: [
                          Text('LOGIN',
                              style: TextStyle(
                                  color: Color.fromRGBO(113, 114, 111, 1))),
                          SizedBox(width: 18),
                          Icon(
                            Icons.arrow_circle_right_rounded,
                            size: 40,
                            color: Color.fromRGBO(110, 201, 241, 1),
                          ),
                        ],
                      ),
                    ),
                  ),
                ),
              ),
              Container(
                child: Padding(
                  padding: EdgeInsets.only(left: screenWidth * 0.02),
                  child: SizedBox(
                    width: 160,
                    height: 50,
                    child: ElevatedButton(
                      onPressed: () {
                        // Add your button press logic here
                      },
                      style: ElevatedButton.styleFrom(
                        backgroundColor: Colors.white,
                        shape: RoundedRectangleBorder(
                          borderRadius: BorderRadius.circular(50),
                        ),
                      ),
                      child: const Row(
                        mainAxisAlignment: MainAxisAlignment.center,
                        children: [
                          Text('9000000000',
                              style: TextStyle(
                                  color: Color.fromRGBO(113, 114, 111, 1))),
                          //SizedBox(width: 8),
                          Icon(
                            Icons.phone_in_talk_rounded,
                            color: Color.fromRGBO(110, 201, 241, 1),
                          )
                        ],
                      ),
                    ),
                  ),
                ),
              )
            ],
          ),
        ),
      ),
      body: SingleChildScrollView(
        child: Column(
          children: [
            Container(
              // color: const Color.fromRGBO(110, 201, 241, 1),
              height: screenHeight * 0.80,
              decoration: const BoxDecoration(
                image: DecorationImage(
                    image: AssetImage("assets/images/background_latest.png"),
                    fit: BoxFit.cover),
              ),

              child: Row(
                mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                children: [
                  Column(
                    mainAxisAlignment: MainAxisAlignment.start,
                    children: [
                      const Padding(
                        padding: EdgeInsets.only(top: 160.0),
                        child: Text(
                          '''Buy this offer
@25% off !
on Medicine Orders''',
                          style: TextStyle(
                            color: Colors.white,
                            height: 1.3,
                            fontSize: 60,
                            fontWeight: FontWeight.bold,
                            letterSpacing: 0.5,
                          ),
                        ),
                      ),
                      Padding(
                        padding: const EdgeInsets.only(top: 20.0, right: 290.0),
                        child: SizedBox(
                          height: 64.0,
                          width: 304.0,
                          child: FloatingActionButton.extended(
                            onPressed: () {
                              // Add your onPressed code here!
                            },
                            backgroundColor: Colors.deepOrange,
                            label: Row(
                              children: [
                                const Text(
                                  'Buy Now',
                                  style: TextStyle(
                                      color: Color.fromRGBO(255, 255, 255, 1.0),
                                      fontSize: 24,
                                      fontWeight: FontWeight.bold),
                                ),
                              ],
                            ),
                          ),
                        ),
                      ),
                    ],
                  ),
                  Column(
                    children: [
                      // Image(image: AssetImage('assets/images/Caspture.png')),
                      Padding(
                        padding: const EdgeInsets.only(top: 46.0, left: 1),
                        child: Image.asset(
                          'assets/images/medicine.png',
                          height: 400,
                          width: 494,
                        ),
                      ),
                    ],
                  )
                ],
              ),
            ),
            Container(
              child: Padding(
                padding: const EdgeInsets.only(top: 50.0, bottom: 40.0),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                  children: [
                    Column(
                      children: [
                        Padding(
                          padding: const EdgeInsets.only(bottom: 15.0),
                          child: Container(
                            decoration: const BoxDecoration(boxShadow: [
                              BoxShadow(
                                color: Color.fromARGB(255, 250, 244, 244),
                                blurRadius: 15.0,
                              ),
                            ]),
                            child: const CircleAvatar(
                              backgroundColor: Colors.white,
                              radius: 72,
                              child: CircleAvatar(
                                radius: 36,
                                backgroundColor: Colors.white,
                                foregroundImage:
                                AssetImage("assets/images/healthcare.png"),
                              ),
                            ),
                          ),
                        ),
                        const Padding(
                          padding: EdgeInsets.only(top: 15.0),
                          child: Text(
                            "Reliable Partner",
                            style: TextStyle(
                                fontWeight: FontWeight.bold,
                                fontSize: 24,
                                color: Color.fromRGBO(113, 114, 111, 1)),
                          ),
                        ),
                        const Padding(
                          padding: EdgeInsets.only(top: 5.0),
                          child: Text(
                            '''Lorem Ipsum is simply dummy text
of the printing and typesetting 
industry.''',
                            textAlign: TextAlign.center,
                            style: TextStyle(
                                height: 1.5,
                                color: Color.fromRGBO(113, 114, 111, 1),
                                fontSize: 24),
                          ),
                        )
                      ],
                    ),
                    Column(
                      children: [
                        Padding(
                          padding: const EdgeInsets.only(bottom: 15.0),
                          child: Container(
                            decoration: const BoxDecoration(boxShadow: [
                              BoxShadow(
                                color: Color.fromARGB(255, 250, 244, 244),
                                blurRadius: 15.0,
                              ),
                            ]),
                            child: const CircleAvatar(
                              backgroundColor: Colors.white,
                              radius: 72,
                              child: CircleAvatar(
                                radius: 36,
                                backgroundColor: Colors.white,
                                foregroundImage:
                                AssetImage("assets/images/healthcare.png"),
                              ),
                            ),
                          ),
                        ),
                        const Padding(
                          padding: EdgeInsets.only(top: 15.0),
                          child: Text(
                            "Tailored Plans",
                            style: TextStyle(
                                fontWeight: FontWeight.bold,
                                fontSize: 24,
                                color: Color.fromRGBO(113, 114, 111, 1)),
                          ),
                        ),
                        const Padding(
                          padding: EdgeInsets.only(top: 5.0),
                          child: Text(
                            '''Lorem Ipsum is simply dummy text
of the printing and typesetting 
industry.''',
                            textAlign: TextAlign.center,
                            style: TextStyle(
                                height: 1.5,
                                color: Color.fromRGBO(113, 114, 111, 1),
                                fontSize: 24),
                          ),
                        )
                      ],
                    ),
                    Column(
                      children: [
                        Padding(
                          padding: const EdgeInsets.only(bottom: 15.0),
                          child: Container(
                            decoration: const BoxDecoration(boxShadow: [
                              BoxShadow(
                                color: Color.fromARGB(255, 250, 244, 244),
                                blurRadius: 15.0,
                              ),
                            ]),
                            child: const CircleAvatar(
                              backgroundColor: Colors.white,
                              radius: 72,
                              child: CircleAvatar(
                                radius: 36,
                                backgroundColor: Colors.white,
                                foregroundImage:
                                AssetImage("assets/images/healthcare.png"),
                              ),
                            ),
                          ),
                        ),
                        const Padding(
                          padding: EdgeInsets.only(top: 15.0),
                          child: Text(
                            "Services",
                            style: TextStyle(
                                fontWeight: FontWeight.bold,
                                fontSize: 24,
                                color: Color.fromRGBO(113, 114, 111, 1)),
                          ),
                        ),
                        const Padding(
                          padding: EdgeInsets.only(top: 5.0),
                          child: Text(
                            '''Lorem Ipsum is simply dummy text
of the printing and typesetting 
industry.''',
                            textAlign: TextAlign.center,
                            style: TextStyle(
                                height: 1.5,
                                color: Color.fromRGBO(113, 114, 111, 1),
                                fontSize: 24),
                          ),
                        )
                      ],
                    ),
                  ],
                ),
              ),
            ),
            Material(
              elevation: 10,
              child: Container(
                color: const Color.fromARGB(255, 255, 254, 254),
                height: screenHeight * 0.10,
                child: Padding(
                  padding: const EdgeInsets.only(left: 50.0),
                  child: Row(
                    children: [
                      Image.asset(
                        "assets/images/plane.png",
                        height: 50.0,
                        width: 50.0,
                      ),
                      const Padding(
                        padding: EdgeInsets.only(left: 15.0),
                        child: Text(
                          "TRAVEL INSURANCE",
                          style: TextStyle(
                              fontWeight: FontWeight.bold,
                              fontSize: 20,
                              color: Color.fromRGBO(113, 114, 111, 1)),
                        ),
                      ),
                      Padding(
                        padding: const EdgeInsets.only(left: 60.0),
                        child: Image.asset(
                          "assets/images/healthcare.png",
                          height: 50.0,
                          width: 50.0,
                        ),
                      ),
                      const Padding(
                        padding: EdgeInsets.only(left: 15.0),
                        child: Text(
                          "HEALTH INSURANCE",
                          style: TextStyle(
                              fontWeight: FontWeight.bold,
                              fontSize: 20,
                              color: Color.fromRGBO(113, 114, 111, 1)),
                        ),
                      ),
                      Padding(
                        padding: const EdgeInsets.only(left: 60.0),
                        child: Image.asset(
                          "assets/images/employees.png",
                          height: 50.0,
                          width: 50.0,
                        ),
                      ),
                      const Padding(
                        padding: EdgeInsets.only(left: 15.0),
                        child: Text(
                          "EMPLOYEE INSURANCE",
                          style: TextStyle(
                              fontWeight: FontWeight.bold,
                              fontSize: 20,
                              color: Color.fromRGBO(113, 114, 111, 1)),
                        ),
                      ),
                      Padding(
                        padding: const EdgeInsets.only(left: 60.0),
                        child: Image.asset(
                          "assets/images/sedan.png",
                          height: 50.0,
                          width: 50.0,
                        ),
                      ),
                      const Padding(
                        padding: EdgeInsets.only(left: 15.0),
                        child: Text(
                          "CAR INSURANCE",
                          style: TextStyle(
                              fontWeight: FontWeight.bold,
                              fontSize: 20,
                              color: Color.fromRGBO(113, 114, 111, 1)),
                        ),
                      ),
                      Padding(
                        padding: const EdgeInsets.only(left: 60.0),
                        child: Image.asset(
                          "assets/images/home.png",
                          height: 50.0,
                          width: 50.0,
                        ),
                      ),
                      const Padding(
                        padding: EdgeInsets.only(left: 15.0),
                        child: Text(
                          "HOME INSURANCE",
                          style: TextStyle(
                              fontWeight: FontWeight.bold,
                              fontSize: 20,
                              color: Color.fromRGBO(113, 114, 111, 1)),
                        ),
                      )
                    ],
                  ),
                ),
              ),
            ),
            Material(
              child: Container(
                height: screenHeight * 0.7,
                child: Row(
                  //mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                  children: [
                    Column(
                      children: [
                        // Image(image: AssetImage('assets/images/Caspture.png')),

                        Padding(
                          padding: const EdgeInsets.only(top: 35.0, left: 30),
                          child: Image.asset(
                            'assets/images/Group.png',
                            height: 460.0,
                            width: 640.0,
                          ),
                        ),
                      ],
                    ),
                    Column(
                      // mainAxisAlignment: MainAxisAlignment.end,
                      children: [
                        Padding(
                          padding: const EdgeInsets.only(top: 45.0, right: 280),
                          child: Image.asset(
                            "assets/images/plane@2x.png",
                            width: 100,
                            height: 100,
                          ),
                        ),
                        const Padding(
                          padding: EdgeInsets.only(top: 20.0, right: 120),
                          child: Text(
                            "TRAVEL INSURANCE",
                            style: TextStyle(
                              color: Color.fromRGBO(113, 114, 111, 1),
                              // height: 1.3,
                              fontSize: 30,
                              fontWeight: FontWeight.bold,
                              //letterSpacing: 0.5,
                            ),
                          ),
                        ),
                        const Padding(
                          padding: EdgeInsets.only(top: 10.0, left: 180.0),
                          child: Text(
                            '''Lorem Ipsum is simply dummy text of the printing and
typesetting industry and uses Latin words combined with a
handful a model words''',
                            style: TextStyle(
                                color: Color.fromRGBO(113, 114, 111, 1),
                                fontSize: 22,
                                height: 1.6),
                          ),
                        ),
                        Padding(
                          padding:
                          const EdgeInsets.only(top: 20.0, right: 95.0),
                          child: SizedBox(
                            height: 64.0,
                            width: 304.0,
                            child: FloatingActionButton.extended(
                              onPressed: () {
                                // Add your onPressed code here!
                              },
                              backgroundColor: Colors.white,
                              label: Row(
                                children: [
                                  const Text(
                                    'Get a quote',
                                    style: TextStyle(
                                        color: Color.fromRGBO(113, 114, 111, 1),
                                        fontSize: 24,
                                        fontWeight: FontWeight.bold),
                                  ),
                                  Padding(
                                    padding: EdgeInsets.only(
                                        left: screenWidth * 0.02),
                                    child: const Icon(
                                      Icons.arrow_circle_right_rounded,
                                      color: Color.fromRGBO(110, 201, 241, 1),
                                      size: 60,
                                    ),
                                  ),
                                ],
                              ),
                            ),
                          ),
                        ),
                      ],
                    ),
                  ],
                ),
              ),
            ),
            Material(
              child: Container(
                height: screenHeight * 0.7,
                child: Row(
                  //mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                  children: [
                    Column(
                      // mainAxisAlignment: MainAxisAlignment.end,
                      children: [
                        Padding(
                          padding: const EdgeInsets.only(top: 45.0, right: 300),
                          child: Image.asset(
                            "assets/images/healthcare@2x.png",
                            width: 100,
                            height: 100,
                          ),
                        ),
                        const Padding(
                          padding: EdgeInsets.only(top: 20.0, right: 120),
                          child: Text(
                            "HEALTH INSURANCE",
                            style: TextStyle(
                              color: Color.fromRGBO(113, 114, 111, 1),
                              // height: 1.3,
                              fontSize: 30,
                              fontWeight: FontWeight.bold,
                              //letterSpacing: 0.5,
                            ),
                          ),
                        ),
                        const Padding(
                          padding: EdgeInsets.only(top: 10.0, left: 170.0),
                          child: Text(
                            '''Lorem Ipsum is simply dummy text of the printing and
typesetting industry and uses Latin words combined with a
handful a model words''',
                            style: TextStyle(
                                color: Color.fromRGBO(113, 114, 111, 1),
                                fontSize: 22,
                                height: 1.6),
                          ),
                        ),
                        Padding(
                          padding:
                          const EdgeInsets.only(top: 20.0, right: 95.0),
                          child: SizedBox(
                            height: 64.0,
                            width: 304.0,
                            child: FloatingActionButton.extended(
                              onPressed: () {
                                // Add your onPressed code here!
                              },
                              backgroundColor: Colors.white,
                              label: Row(
                                children: [
                                  const Text(
                                    'Get a quote',
                                    style: TextStyle(
                                        color: Color.fromRGBO(113, 114, 111, 1),
                                        fontSize: 24,
                                        fontWeight: FontWeight.bold),
                                  ),
                                  Padding(
                                    padding: EdgeInsets.only(
                                        left: screenWidth * 0.02),
                                    child: const Icon(
                                      Icons.arrow_circle_right_rounded,
                                      color: Color.fromRGBO(110, 201, 241, 1),
                                      size: 60,
                                    ),
                                  ),
                                ],
                              ),
                            ),
                          ),
                        ),
                      ],
                    ),
                    Column(
                      children: [
                        // Image(image: AssetImage('assets/images/Caspture.png')),

                        Padding(
                          padding: const EdgeInsets.only(top: 35.0, left: 150),
                          child: Image.asset(
                            'assets/images/Group.png',
                            height: 460.0,
                            width: 640.0,
                          ),
                        ),
                      ],
                    ),
                  ],
                ),
              ),
            ),
            Material(
              child: Container(
                height: screenHeight * 0.7,
                child: Row(
                  //mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                  children: [
                    Column(
                      children: [
                        // Image(image: AssetImage('assets/images/Caspture.png')),

                        Padding(
                          padding: const EdgeInsets.only(top: 35.0, left: 30),
                          child: Image.asset(
                            'assets/images/Group.png',
                            height: 460.0,
                            width: 640.0,
                          ),
                        ),
                      ],
                    ),
                    Column(
                      // mainAxisAlignment: MainAxisAlignment.end,
                      children: [
                        Padding(
                          padding: const EdgeInsets.only(top: 45.0, right: 280),
                          child: Image.asset(
                            "assets/images/employees@2x.png",
                            width: 100,
                            height: 100,
                          ),
                        ),
                        const Padding(
                          padding: EdgeInsets.only(top: 20.0, right: 80),
                          child: Text(
                            "EMPLOYEE INSURANCE",
                            style: TextStyle(
                              color: Color.fromRGBO(113, 114, 111, 1),
                              // height: 1.3,
                              fontSize: 30,
                              fontWeight: FontWeight.bold,
                              //letterSpacing: 0.5,
                            ),
                          ),
                        ),
                        const Padding(
                          padding: EdgeInsets.only(top: 10.0, left: 180.0),
                          child: Text(
                            '''Lorem Ipsum is simply dummy text of the printing and
typesetting industry and uses Latin words combined with a
handful a model words''',
                            style: TextStyle(
                                color: Color.fromRGBO(113, 114, 111, 1),
                                fontSize: 22,
                                height: 1.6),
                          ),
                        ),
                        Padding(
                          padding:
                          const EdgeInsets.only(top: 20.0, right: 95.0),
                          child: SizedBox(
                            height: 64.0,
                            width: 304.0,
                            child: FloatingActionButton.extended(
                              onPressed: () {
                                // Add your onPressed code here!
                              },
                              backgroundColor: Colors.white,
                              label: Row(
                                children: [
                                  const Text(
                                    'Get a quote',
                                    style: TextStyle(
                                        color: Color.fromRGBO(113, 114, 111, 1),
                                        fontSize: 24,
                                        fontWeight: FontWeight.bold),
                                  ),
                                  Padding(
                                    padding: EdgeInsets.only(
                                        left: screenWidth * 0.02),
                                    child: const Icon(
                                      Icons.arrow_circle_right_rounded,
                                      color: Color.fromRGBO(110, 201, 241, 1),
                                      size: 60,
                                    ),
                                  ),
                                ],
                              ),
                            ),
                          ),
                        ),
                      ],
                    ),
                  ],
                ),
              ),
            ),
            Material(
              child: Container(
                height: screenHeight * 0.7,
                child: Row(
                  //mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                  children: [
                    Column(
                      // mainAxisAlignment: MainAxisAlignment.end,
                      children: [
                        Padding(
                          padding: const EdgeInsets.only(top: 45.0, right: 280),
                          child: Image.asset(
                            "assets/images/sedan@2x.png",
                            width: 100,
                            height: 100,
                          ),
                        ),
                        const Padding(
                          padding: EdgeInsets.only(top: 15.0, right: 220),
                          child: Text(
                            "CAR Insurance",
                            style: TextStyle(
                              color: Color.fromRGBO(113, 114, 111, 1),
                              // height: 1.3,
                              fontSize: 30,
                              fontWeight: FontWeight.bold,
                              //letterSpacing: 0.5,
                            ),
                          ),
                        ),
                        const Padding(
                          padding: EdgeInsets.only(top: 10.0, left: 170.0),
                          child: Text(
                            '''Lorem Ipsum is simply dummy text of the printing and
typesetting industry and uses Latin words combined with a
handful a model words''',
                            style: TextStyle(
                                color: Color.fromRGBO(113, 114, 111, 1),
                                fontSize: 22,
                                height: 1.6),
                          ),
                        ),
                        Padding(
                          padding:
                          const EdgeInsets.only(top: 20.0, right: 95.0),
                          child: SizedBox(
                            height: 64.0,
                            width: 304.0,
                            child: FloatingActionButton.extended(
                              onPressed: () {
                                // Add your onPressed code here!
                              },
                              backgroundColor: Colors.white,
                              label: Row(
                                children: [
                                  const Text(
                                    'Get a quote',
                                    style: TextStyle(
                                        color: Color.fromRGBO(113, 114, 111, 1),
                                        fontSize: 24,
                                        fontWeight: FontWeight.bold),
                                  ),
                                  Padding(
                                    padding: EdgeInsets.only(
                                        left: screenWidth * 0.02),
                                    child: const Icon(
                                      Icons.arrow_circle_right_rounded,
                                      color: Color.fromRGBO(110, 201, 241, 1),
                                      size: 60,
                                    ),
                                  ),
                                ],
                              ),
                            ),
                          ),
                        ),
                      ],
                    ),
                    Column(
                      children: [
                        // Image(image: AssetImage('assets/images/Caspture.png')),

                        Padding(
                          padding: const EdgeInsets.only(top: 35.0, left: 150),
                          child: Image.asset(
                            'assets/images/Group.png',
                            height: 460.0,
                            width: 640.0,
                          ),
                        ),
                      ],
                    ),
                  ],
                ),
              ),
            ),
            Material(
              child: Container(
                height: screenHeight * 0.7,
                child: Row(
                  //mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                  children: [
                    Column(
                      children: [
                        // Image(image: AssetImage('assets/images/Caspture.png')),

                        Padding(
                          padding: const EdgeInsets.only(top: 35.0, left: 30),
                          child: Image.asset(
                            'assets/images/Group.png',
                            height: 460.0,
                            width: 640.0,
                          ),
                        ),
                      ],
                    ),
                    Column(
                      // mainAxisAlignment: MainAxisAlignment.end,
                      children: [
                        Padding(
                          padding: const EdgeInsets.only(top: 45.0, right: 300),
                          child: Image.asset(
                            "assets/images/home@2x.png",
                            width: 100,
                            height: 100,
                          ),
                        ),
                        const Padding(
                          padding: EdgeInsets.only(top: 20.0, right: 140),
                          child: Text(
                            "HOME INSURANCE",
                            style: TextStyle(
                              color: Color.fromRGBO(113, 114, 111, 1),
                              // height: 1.3,
                              fontSize: 30,
                              fontWeight: FontWeight.bold,
                              //letterSpacing: 0.5,
                            ),
                          ),
                        ),
                        const Padding(
                          padding: EdgeInsets.only(top: 10.0, left: 180.0),
                          child: Text(
                            '''Lorem Ipsum is simply dummy text of the printing and
typesetting industry and uses Latin words combined with a
handful a model words''',
                            style: TextStyle(
                                color: Color.fromRGBO(113, 114, 111, 1),
                                fontSize: 22,
                                height: 1.6),
                          ),
                        ),
                        Padding(
                          padding:
                          const EdgeInsets.only(top: 20.0, right: 95.0),
                          child: SizedBox(
                            height: 64.0,
                            width: 304.0,
                            child: FloatingActionButton.extended(
                              onPressed: () {},
                              backgroundColor: Colors.white,
                              label: Row(
                                children: [
                                  const Text(
                                    'Get a quote',
                                    style: TextStyle(
                                        color: Color.fromRGBO(113, 114, 111, 1),
                                        fontSize: 24,
                                        fontWeight: FontWeight.bold),
                                  ),
                                  Padding(
                                    padding: EdgeInsets.only(
                                        left: screenWidth * 0.02),
                                    child: const Icon(
                                      Icons.arrow_circle_right_rounded,
                                      color: Color.fromRGBO(110, 201, 241, 1),
                                      size: 60,
                                    ),
                                  ),
                                ],
                              ),
                            ),
                          ),
                        ),
                      ],
                    ),
                  ],
                ),
              ),
            ),
            Material(
              child: Container(
                height: screenHeight * 1.55,
                color: const Color.fromRGBO(121, 122, 119, 1),
                child: Row(
                  children: [
                    Column(
                      children: [
                        const Padding(
                          padding: EdgeInsets.only(left: 100, top: 80),
                          child: Text(
                            '''WE WILL HELP YOUR FAMILY IN YOUR 
CONSECTETUR ADIPISCING ELIT''',
                            style: TextStyle(
                                fontSize: 32,
                                fontWeight: FontWeight.bold,
                                letterSpacing: 0.5,
                                color: Colors.white),
                          ),
                        ),
                        const Padding(
                          padding: EdgeInsets.only(left: 100, top: 15),
                          child: Text(
                            '''Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod
tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim 
veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea 
commodo consequat.''',
                            style: TextStyle(
                                fontSize: 16,
                                letterSpacing: 0.5,
                                color: Colors.white),
                          ),
                        ),
                        Padding(
                          padding: const EdgeInsets.only(
                            left: 90,
                          ),
                          child: Image.asset("assets/images/1111.png",
                              height: 900, width: 600),
                        )
                      ],
                    ),
                    Column(
                      children: [
                        Padding(
                          padding: const EdgeInsets.only(
                            top: 90,
                            left: 90,
                          ),
                          child: Image.asset(
                            "assets/images/2222.png",
                            width: 750,
                            // height: 500,
                          ),
                        ),
                        const Padding(
                          padding: EdgeInsets.only(
                            top: 30,
                            right: 90,
                          ),
                          child: Text(
                            '''WE WILL HELP YOUR FAMILY IN YOUR
CONSECTETUR ADIPISCING ELIT''',
                            style: TextStyle(
                                fontSize: 32,
                                fontWeight: FontWeight.bold,
                                letterSpacing: 0.5,
                                color: Colors.white),
                          ),
                        ),
                        Padding(
                          padding: const EdgeInsets.only(
                            top: 8,
                            // right: 90,
                          ),
                          child: Card(
                            shape: RoundedRectangleBorder(
                              borderRadius: BorderRadius.circular(60.0),
                            ),
                            child: const SizedBox(
                              width: 630,
                              height: 60,
                              child: Center(
                                child: Text(
                                  'Who we are?                                                                          ',
                                  textAlign: TextAlign.start,
                                  style: TextStyle(
                                    fontSize: 24,
                                    fontWeight: FontWeight.bold,
                                    color: Color.fromRGBO(121, 122, 119, 1),
                                  ),
                                ),
                              ),
                            ),
                          ),
                        ),
                        Padding(
                          padding: const EdgeInsets.only(
                            top: 8,
                            // right: 90,
                          ),
                          child: Card(
                            shape: RoundedRectangleBorder(
                              borderRadius: BorderRadius.circular(30.0),
                            ),
                            child: const SizedBox(
                              width: 630,
                              height: 90,
                              child: Center(
                                  child: Text(
                                    '''There are many variations of passages of Lorem Ipsum available, but
the majority have suffered alteration in some form by injected 
humour or randomised words.''',
                                    style: TextStyle(
                                        color: Color.fromRGBO(121, 122, 119, 1),
                                        fontSize: 19),
                                  )),
                            ),
                          ),
                        ),
                        Padding(
                          padding: const EdgeInsets.only(
                            top: 8,
                            // right: 90,
                          ),
                          child: Card(
                            shape: RoundedRectangleBorder(
                              borderRadius: BorderRadius.circular(60.0),
                            ),
                            child: const SizedBox(
                              width: 630,
                              height: 60,
                              child: Center(
                                  child: Text(
                                    'What is our mission?                                                           ',
                                    textAlign: TextAlign.start,
                                    style: TextStyle(
                                      fontSize: 24,
                                      fontWeight: FontWeight.bold,
                                      color: Color.fromRGBO(121, 122, 119, 1),
                                    ),
                                  )),
                            ),
                          ),
                        ),
                        Padding(
                          padding: const EdgeInsets.only(
                            top: 8,
                            // right: 90,
                          ),
                          child: Card(
                            shape: RoundedRectangleBorder(
                              borderRadius: BorderRadius.circular(60.0),
                            ),
                            child: const SizedBox(
                              width: 630,
                              height: 60,
                              child: Center(
                                  child: Text(
                                    'What we believe?                                                               ',
                                    textAlign: TextAlign.start,
                                    style: TextStyle(
                                      fontSize: 24,
                                      fontWeight: FontWeight.bold,
                                      color: Color.fromRGBO(121, 122, 119, 1),
                                    ),
                                  )),
                            ),
                          ),
                        ),
                      ],
                    )
                  ],
                ),
              ),
            ),
            Container(
              height: screenHeight * 1.0,
              child: Center(
                child: Column(
                  children: [
                    const Padding(
                      padding: EdgeInsets.only(top: 60),
                      child: Text("CORE FEATURES",
                          style: TextStyle(
                            fontSize: 32,
                            fontWeight: FontWeight.bold,
                            color: Color.fromRGBO(121, 122, 119, 1),
                          )),
                    ),
                    const Padding(
                      padding: EdgeInsets.only(top: 20),
                      child: Text(
                          "Lorem Ipsum is simply dummy text of the printing and typesetting industry",
                          style: TextStyle(
                            fontSize: 20,
                            color: Color.fromRGBO(121, 122, 119, 1),
                          )),
                    ),
                    Padding(
                      padding: const EdgeInsets.only(top: 40),
                      child: Row(
                        children: [
                          Padding(
                            padding: const EdgeInsets.only(left: 100),
                            child: Card(
                              elevation: 4,
                              shape: RoundedRectangleBorder(
                                borderRadius: BorderRadius.circular(20),
                              ),
                              child: Stack(
                                children: [
                                  SizedBox(
                                    width: 394,
                                    height: 236,
                                    child: Row(
                                      children: [
                                        Column(
                                          crossAxisAlignment:
                                          CrossAxisAlignment.start,
                                          children: [
                                            const Padding(
                                              padding: EdgeInsets.only(
                                                  top: 65, left: 35),
                                              child: Text(
                                                'Better Coverage',
                                                style: TextStyle(
                                                  fontSize: 30,
                                                  color: Color.fromRGBO(
                                                      121, 122, 119, 1),
                                                ),
                                              ),
                                            ),
                                            const Padding(
                                              padding: EdgeInsets.only(
                                                  top: 20, left: 35),
                                              child: Text(
                                                '''Lorem Ipsum is simply dummy text 
of the printing and typesetting industry''',
                                                style: TextStyle(
                                                  fontSize: 18,
                                                  color: Color.fromRGBO(
                                                      121, 122, 119, 1),
                                                ),
                                              ),
                                            ),
                                            Padding(
                                              padding: const EdgeInsets.only(
                                                  top: 20, left: 35),
                                              child: TextButton(
                                                onPressed: () {
                                                  // Add your button press logic here
                                                },
                                                child: const Text(
                                                  'Know More',
                                                  style: TextStyle(
                                                    color: Color.fromRGBO(
                                                        110, 201, 241, 1),
                                                  ),
                                                ),
                                              ),
                                            ),
                                          ],
                                        ),
                                      ],
                                    ),
                                  ),
                                  Positioned(
                                    // top: 10,

                                    right: 1,

                                    child: Container(
                                      width: 70,
                                      height: 70,
                                      decoration: const BoxDecoration(
                                        color: Color.fromRGBO(110, 201, 241, 1),
                                        borderRadius: const BorderRadius.only(
                                          topRight: Radius.circular(20),
                                          bottomLeft: Radius.circular(20),
                                        ),
                                      ),
                                      child: const ClipPath(
                                        // clipper: CustomIconClipper(),

                                        child: CustomPaint(
                                          //   painter: CustomIconPainter(),

                                          child: Center(
                                            child: Icon(
                                              Icons.double_arrow_rounded,
                                              size: 30,
                                              color: Colors.white,
                                            ),
                                          ),
                                        ),
                                      ),
                                    ),
                                  ),
                                ],
                              ),
                            ),
                          ),
                          Padding(
                            padding: const EdgeInsets.only(left: 100),
                            child: Card(
                              elevation: 4,
                              shape: RoundedRectangleBorder(
                                borderRadius: BorderRadius.circular(20),
                              ),
                              child: Stack(
                                children: [
                                  SizedBox(
                                    width: 394,
                                    height: 236,
                                    child: Row(
                                      children: [
                                        Column(
                                          crossAxisAlignment:
                                          CrossAxisAlignment.start,
                                          children: [
                                            const Padding(
                                              padding: EdgeInsets.only(
                                                  top: 65, left: 35),
                                              child: Text(
                                                'Cost Effective',
                                                style: TextStyle(
                                                  fontSize: 30,
                                                  color: Color.fromRGBO(
                                                      121, 122, 119, 1),
                                                ),
                                              ),
                                            ),
                                            const Padding(
                                              padding: EdgeInsets.only(
                                                  top: 20, left: 35),
                                              child: Text(
                                                '''Lorem Ipsum is simply dummy text 
of the printing and typesetting industry''',
                                                style: TextStyle(
                                                  fontSize: 18,
                                                  color: Color.fromRGBO(
                                                      121, 122, 119, 1),
                                                ),
                                              ),
                                            ),
                                            Padding(
                                              padding: const EdgeInsets.only(
                                                  top: 20, left: 35),
                                              child: TextButton(
                                                onPressed: () {
                                                  // Add your button press logic here
                                                },
                                                child: const Text(
                                                  'Know More',
                                                  style: TextStyle(
                                                    color: Color.fromRGBO(
                                                        110, 201, 241, 1),
                                                  ),
                                                ),
                                              ),
                                            ),
                                          ],
                                        ),
                                      ],
                                    ),
                                  ),
                                  Positioned(
                                    // top: 10,

                                    right: 1,

                                    child: Container(
                                      width: 70,
                                      height: 70,
                                      decoration: const BoxDecoration(
                                        color: Color.fromRGBO(110, 201, 241, 1),
                                        borderRadius: const BorderRadius.only(
                                          topRight: Radius.circular(20),
                                          bottomLeft: Radius.circular(20),
                                        ),
                                      ),
                                      child: const ClipPath(
                                        // clipper: CustomIconClipper(),

                                        child: CustomPaint(
                                          //   painter: CustomIconPainter(),

                                          child: Center(
                                            child: Icon(
                                              Icons.double_arrow_rounded,
                                              size: 30,
                                              color: Colors.white,
                                            ),
                                          ),
                                        ),
                                      ),
                                    ),
                                  ),
                                ],
                              ),
                            ),
                          ),
                          Padding(
                            padding: const EdgeInsets.only(left: 100),
                            child: Card(
                              elevation: 4,
                              shape: RoundedRectangleBorder(
                                borderRadius: BorderRadius.circular(20),
                              ),
                              child: Stack(
                                children: [
                                  SizedBox(
                                    width: 394,
                                    height: 236,
                                    child: Row(
                                      children: [
                                        Column(
                                          crossAxisAlignment:
                                          CrossAxisAlignment.start,
                                          children: [
                                            const Padding(
                                              padding: EdgeInsets.only(
                                                  top: 65, left: 35),
                                              child: Text(
                                                'Best Practices',
                                                style: TextStyle(
                                                  fontSize: 30,
                                                  color: Color.fromRGBO(
                                                      121, 122, 119, 1),
                                                ),
                                              ),
                                            ),
                                            const Padding(
                                              padding: EdgeInsets.only(
                                                  top: 20, left: 35),
                                              child: Text(
                                                '''Lorem Ipsum is simply dummy text 
of the printing and typesetting industry''',
                                                style: TextStyle(
                                                  fontSize: 18,
                                                  color: Color.fromRGBO(
                                                      121, 122, 119, 1),
                                                ),
                                              ),
                                            ),
                                            Padding(
                                              padding: const EdgeInsets.only(
                                                  top: 20, left: 35),
                                              child: TextButton(
                                                onPressed: () {
                                                  // Add your button press logic here
                                                },
                                                child: const Text(
                                                  'Know More',
                                                  style: TextStyle(
                                                    color: Color.fromRGBO(
                                                        110, 201, 241, 1),
                                                  ),
                                                ),
                                              ),
                                            ),
                                          ],
                                        ),
                                      ],
                                    ),
                                  ),
                                  Positioned(
                                    // top: 10,

                                    right: 1,

                                    child: Container(
                                      width: 70,
                                      height: 70,
                                      decoration: const BoxDecoration(
                                        color: Color.fromRGBO(110, 201, 241, 1),
                                        borderRadius: const BorderRadius.only(
                                          topRight: Radius.circular(20),
                                          bottomLeft: Radius.circular(20),
                                        ),
                                      ),
                                      child: const ClipPath(
                                        // clipper: CustomIconClipper(),

                                        child: CustomPaint(
                                          //   painter: CustomIconPainter(),

                                          child: Center(
                                            child: Icon(
                                              Icons.double_arrow_rounded,
                                              size: 30,
                                              color: Colors.white,
                                            ),
                                          ),
                                        ),
                                      ),
                                    ),
                                  ),
                                ],
                              ),
                            ),
                          ),
                        ],
                      ),
                    ),
                    Padding(
                      padding: const EdgeInsets.only(top: 40),
                      child: Row(
                        children: [
                          Padding(
                            padding: const EdgeInsets.only(left: 100),
                            child: Card(
                              elevation: 4,
                              shape: RoundedRectangleBorder(
                                borderRadius: BorderRadius.circular(20),
                              ),
                              child: Stack(
                                children: [
                                  SizedBox(
                                    width: 394,
                                    height: 236,
                                    child: Row(
                                      children: [
                                        Column(
                                          crossAxisAlignment:
                                          CrossAxisAlignment.start,
                                          children: [
                                            const Padding(
                                              padding: EdgeInsets.only(
                                                  top: 65, left: 35),
                                              child: Text(
                                                'Customer First',
                                                style: TextStyle(
                                                  fontSize: 30,
                                                  color: Color.fromRGBO(
                                                      121, 122, 119, 1),
                                                ),
                                              ),
                                            ),
                                            const Padding(
                                              padding: EdgeInsets.only(
                                                  top: 20, left: 35),
                                              child: Text(
                                                '''Lorem Ipsum is simply dummy text 
of the printing and typesetting industry''',
                                                style: TextStyle(
                                                  fontSize: 18,
                                                  color: Color.fromRGBO(
                                                      121, 122, 119, 1),
                                                ),
                                              ),
                                            ),
                                            Padding(
                                              padding: const EdgeInsets.only(
                                                  top: 20, left: 35),
                                              child: TextButton(
                                                onPressed: () {
                                                  // Add your button press logic here
                                                },
                                                child: const Text(
                                                  'Know More',
                                                  style: TextStyle(
                                                    color: Color.fromRGBO(
                                                        110, 201, 241, 1),
                                                  ),
                                                ),
                                              ),
                                            ),
                                          ],
                                        ),
                                      ],
                                    ),
                                  ),
                                  Positioned(
                                    // top: 10,

                                    right: 1,

                                    child: Container(
                                      width: 70,
                                      height: 70,
                                      decoration: const BoxDecoration(
                                        color: Color.fromRGBO(110, 201, 241, 1),
                                        borderRadius: const BorderRadius.only(
                                          topRight: Radius.circular(20),
                                          bottomLeft: Radius.circular(20),
                                        ),
                                      ),
                                      child: const ClipPath(
                                        // clipper: CustomIconClipper(),

                                        child: CustomPaint(
                                          //   painter: CustomIconPainter(),

                                          child: Center(
                                            child: Icon(
                                              Icons.double_arrow_rounded,
                                              size: 30,
                                              color: Colors.white,
                                            ),
                                          ),
                                        ),
                                      ),
                                    ),
                                  ),
                                ],
                              ),
                            ),
                          ),
                          Padding(
                            padding: const EdgeInsets.only(left: 100),
                            child: Card(
                              elevation: 4,
                              shape: RoundedRectangleBorder(
                                borderRadius: BorderRadius.circular(20),
                              ),
                              child: Stack(
                                children: [
                                  SizedBox(
                                    width: 394,
                                    height: 236,
                                    child: Row(
                                      children: [
                                        Column(
                                          crossAxisAlignment:
                                          CrossAxisAlignment.start,
                                          children: [
                                            const Padding(
                                              padding: EdgeInsets.only(
                                                  top: 65, left: 35),
                                              child: Text(
                                                'Flexible Plans',
                                                style: TextStyle(
                                                  fontSize: 30,
                                                  color: Color.fromRGBO(
                                                      121, 122, 119, 1),
                                                ),
                                              ),
                                            ),
                                            const Padding(
                                              padding: EdgeInsets.only(
                                                  top: 20, left: 35),
                                              child: Text(
                                                '''Lorem Ipsum is simply dummy text 
of the printing and typesetting industry''',
                                                style: TextStyle(
                                                  fontSize: 18,
                                                  color: Color.fromRGBO(
                                                      121, 122, 119, 1),
                                                ),
                                              ),
                                            ),
                                            Padding(
                                              padding: const EdgeInsets.only(
                                                  top: 20, left: 35),
                                              child: TextButton(
                                                onPressed: () {
                                                  // Add your button press logic here
                                                },
                                                child: const Text(
                                                  'Know More',
                                                  style: TextStyle(
                                                    color: Color.fromRGBO(
                                                        110, 201, 241, 1),
                                                  ),
                                                ),
                                              ),
                                            ),
                                          ],
                                        ),
                                      ],
                                    ),
                                  ),
                                  Positioned(
                                    // top: 10,

                                    right: 1,

                                    child: Container(
                                      width: 70,
                                      height: 70,
                                      decoration: const BoxDecoration(
                                        color: Color.fromRGBO(110, 201, 241, 1),
                                        borderRadius: const BorderRadius.only(
                                          topRight: Radius.circular(20),
                                          bottomLeft: Radius.circular(20),
                                        ),
                                      ),
                                      child: const ClipPath(
                                        // clipper: CustomIconClipper(),

                                        child: CustomPaint(
                                          //   painter: CustomIconPainter(),

                                          child: Center(
                                            child: Icon(
                                              Icons.double_arrow_rounded,
                                              size: 30,
                                              color: Colors.white,
                                            ),
                                          ),
                                        ),
                                      ),
                                    ),
                                  ),
                                ],
                              ),
                            ),
                          ),
                          Padding(
                            padding: const EdgeInsets.only(left: 100),
                            child: Card(
                              elevation: 4,
                              shape: RoundedRectangleBorder(
                                borderRadius: BorderRadius.circular(20),
                              ),
                              child: Stack(
                                children: [
                                  SizedBox(
                                    width: 394,
                                    height: 236,
                                    child: Row(
                                      children: [
                                        Column(
                                          crossAxisAlignment:
                                          CrossAxisAlignment.start,
                                          children: [
                                            const Padding(
                                              padding: EdgeInsets.only(
                                                  top: 65, left: 35),
                                              child: Text(
                                                'Premium Service',
                                                style: TextStyle(
                                                  fontSize: 30,
                                                  color: Color.fromRGBO(
                                                      121, 122, 119, 1),
                                                ),
                                              ),
                                            ),
                                            const Padding(
                                              padding: EdgeInsets.only(
                                                  top: 20, left: 35),
                                              child: Text(
                                                '''Lorem Ipsum is simply dummy text 
of the printing and typesetting industry''',
                                                style: TextStyle(
                                                  fontSize: 18,
                                                  color: Color.fromRGBO(
                                                      121, 122, 119, 1),
                                                ),
                                              ),
                                            ),
                                            Padding(
                                              padding: const EdgeInsets.only(
                                                  top: 20, left: 35),
                                              child: TextButton(
                                                onPressed: () {
                                                  // Add your button press logic here
                                                },
                                                child: const Text(
                                                  'Know More',
                                                  style: TextStyle(
                                                    color: Color.fromRGBO(
                                                        110, 201, 241, 1),
                                                  ),
                                                ),
                                              ),
                                            ),
                                          ],
                                        ),
                                      ],
                                    ),
                                  ),
                                  Positioned(
                                    // top: 10,

                                    right: 1,

                                    child: Container(
                                      width: 70,
                                      height: 70,
                                      decoration: const BoxDecoration(
                                        color: Color.fromRGBO(110, 201, 241, 1),
                                        borderRadius: const BorderRadius.only(
                                          topRight: Radius.circular(20),
                                          bottomLeft: Radius.circular(20),
                                        ),
                                      ),
                                      child: const ClipPath(
                                        // clipper: CustomIconClipper(),

                                        child: CustomPaint(
                                          //   painter: CustomIconPainter(),

                                          child: Center(
                                            child: Icon(
                                              Icons.double_arrow_rounded,
                                              size: 30,
                                              color: Colors.white,
                                            ),
                                          ),
                                        ),
                                      ),
                                    ),
                                  ),
                                ],
                              ),
                            ),
                          ),
                        ],
                      ),
                    ),
                  ],
                ),
              ),
            ),
            Container(
                height: screenHeight * 0.75,
                color: const Color.fromRGBO(110, 201, 241, 1),
                child: Center(
                  child: Column(
                    children: [
                      Padding(
                        padding: EdgeInsets.only(top: 50),
                        child: Text("GET A FREE QUOTE",
                            style: TextStyle(
                                fontSize: 32,
                                color: Colors.white,
                                fontWeight: FontWeight.bold)),
                      ),
                      Row(
                        children: [
                          Padding(
                            padding: const EdgeInsets.only(top: 35, left: 80),
                            child: SizedBox(
                              width: 400,
                              height: 70,
                              child: TextField(
                                maxLines: null,
                                expands: true,
                                decoration: InputDecoration(
                                  // border: OutlineInputBorder(),

                                  hintText: 'Name',
                                  filled: true,
                                  fillColor: Colors.white,
                                  labelStyle: TextStyle(color: Colors.white),
                                  // hoverColor: Colors.white
                                ),
                              ),
                            ),
                          ),
                          Padding(
                            padding: const EdgeInsets.only(top: 35, left: 80),
                            child: SizedBox(
                              width: 400,
                              height: 70,
                              child: TextField(
                                maxLines: null,
                                expands: true,
                                decoration: InputDecoration(
                                  // border: OutlineInputBorder(),
                                  hintText: 'Email',
                                  filled: true,
                                  fillColor: Colors.white,
                                  labelStyle: TextStyle(color: Colors.white),
                                  // hoverColor: Colors.white
                                ),
                              ),
                            ),
                          ),
                          Padding(
                            padding: const EdgeInsets.only(top: 35, left: 80),
                            child: SizedBox(
                              width: 400,
                              height: 70,
                              child: TextField(
                                maxLines: null,
                                expands: true,
                                decoration: InputDecoration(
                                  // border: OutlineInputBorder(),
                                  hintText: 'Phone',
                                  filled: true,
                                  fillColor: Colors.white,
                                  labelStyle: TextStyle(color: Colors.white),
                                  // hoverColor: Colors.white
                                ),
                              ),
                            ),
                          ),
                        ],
                      ),
                      Padding(
                        padding: EdgeInsets.only(top: 35, left: 30),
                        child: SizedBox(
                          width: 1300,
                          height: 200,
                          child: TextField(
                            maxLines: null,
                            expands: true,
                            decoration: InputDecoration(
                              // border: OutlineInputBorder(),
                              hintText: 'Message',
                              filled: true,
                              fillColor: Colors.white,
                              labelStyle: TextStyle(color: Colors.white),
                              // hoverColor: Colors.white
                            ),
                          ),
                        ),
                      ),
                      Padding(
                        padding: EdgeInsets.only(top: 35.0, right: 10.0),
                        child: SizedBox(
                          height: 64.0,
                          width: 304.0,
                          child: FloatingActionButton.extended(
                            onPressed: () {},
                            backgroundColor: Colors.white,
                            label: Row(
                              children: [
                                const Text(
                                  'Get a quote',
                                  style: TextStyle(
                                      color: Color.fromRGBO(113, 114, 111, 1),
                                      fontSize: 24,
                                      fontWeight: FontWeight.bold),
                                ),
                                Padding(
                                  padding: EdgeInsets.only(left: 20),
                                  child: const Icon(
                                    Icons.arrow_circle_right_rounded,
                                    color: Color.fromRGBO(110, 201, 241, 1),
                                    size: 60,
                                  ),
                                ),
                              ],
                            ),
                          ),
                        ),
                      ),
                    ],
                  ),
                )),
            //f***************************************footer********************************************
            Container(
              height: screenHeight * 0.4,

              // width: double.infinity,

              color: Colors.grey,

              child: Column(
                children: [
                  const SizedBox(
                    height: 50,
                  ),
                  Row(
                    children: [
                      const SizedBox(
                        width: 100,
                      ),
                      Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          Image.asset(
                            "assets/images/Template.png",
                            fit: BoxFit.cover,
                            color: Colors.white,
                          ),
                          const SizedBox(
                            height: 20,
                          ),
                          const Text('''Securisk Insurance Brokers (P) Ltd.
IRDA Registration # 597,Validity May 1st 2013 – 
April 30th 2023, CIN : U67200PB2013PTC037781''',
                              style:
                              TextStyle(fontSize: 15, color: Colors.white)),
                        ],
                      ),
                      const SizedBox(
                        width: 150,
                      ),
                      const Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          Text("Registered Office",
                              style: TextStyle(
                                fontSize: 20,
                                color: Colors.white,
                              )),
                          SizedBox(
                            height: 20,
                          ),
                          Text('''33, Ground Floor, Nirmal Chhaya 
Apartments, MIG Flats Block – C, 
Rishi Nagar Ludhiana 141001''',
                              style:
                              TextStyle(fontSize: 15, color: Colors.white)),
                          Row(
                            children: [
                              Icon(
                                Icons.phone_in_talk_rounded,
                                color: Color.fromRGBO(110, 201, 241, 1),
                              ),
                              SizedBox(
                                width: 10,
                              ),
                              Text("+91 7498443677",
                                  style: TextStyle(
                                      fontSize: 15, color: Colors.white)),
                            ],
                          ),
                        ],
                      ),
                      const SizedBox(
                        width: 100,
                      ),
                      const Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          Text("Corporate Office",
                              style:
                              TextStyle(fontSize: 20, color: Colors.white)),
                          SizedBox(
                            height: 20,
                          ),
                          Text('''# B – 406, 4th Floor, Mahavir Icon, 
Plot # 89 – 90, Sector 15, CBD 
Belapur, Navi Mumbai – 4000614''',
                              style:
                              TextStyle(fontSize: 15, color: Colors.white)),
                          Row(
                            children: [
                              Icon(
                                Icons.phone_in_talk_rounded,
                                color: Color.fromRGBO(110, 201, 241, 1),
                              ),
                              SizedBox(
                                width: 10,
                              ),
                              Text("022-49707155",
                                  style: TextStyle(
                                      fontSize: 15, color: Colors.white)),
                            ],
                          ),
                        ],
                      ),
                      const SizedBox(
                        width: 100,
                      ),
                      const Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          Text("Branch Office",
                              style:
                              TextStyle(fontSize: 20, color: Colors.white)),
                          SizedBox(
                            height: 20,
                          ),
                          Text('''Shop 3B, Commercial Street, 
Lanco Hills, Manikonda Jagir, 
Hyderabad - 500089, Telangana.''',
                              style: TextStyle(
                                  fontSize: 15, color: Color(0xFFFFFFFF))),
                          Row(
                            children: [
                              Icon(
                                Icons.phone_in_talk_outlined,
                                color: Color.fromRGBO(110, 201, 241, 1),
                              ),
                              SizedBox(
                                width: 10,
                              ),
                              Text("+91 99853035655",
                                  style: TextStyle(
                                      fontSize: 15, color: Colors.white)),
                            ],
                          ),
                        ],
                      ),
                    ],
                  ),
                  const SizedBox(
                    height: 30,
                  ),
                  const Row(
                    children: [
                      const SizedBox(
                        width: 100,
                      ),
                      Text(
                          "© 2023 Securisk Insurance Brokers (P) Ltd. All rights reserved.",
                          style: TextStyle(fontSize: 13, color: Colors.white)),
                      SizedBox(
                        width: 5,
                      ),
                      Text(
                        "Terms and Conditions | Privacy Policies",
                        style: TextStyle(
                            fontSize: 15,
                            color: Colors.white,
                            fontWeight: FontWeight.bold),
                      ),
                    ],
                  )
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}
