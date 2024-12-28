import 'package:flutter/material.dart';

class Coporates extends StatefulWidget {
  const Coporates({super.key});

  @override
  State<Coporates> createState() => _CoporatesState();
}

class _CoporatesState extends State<Coporates> {
  @override
  Widget build(BuildContext context) {
    //double height = MediaQuery.of(context).size.height;
    double width = MediaQuery.of(context).size.width;
    return Container(
      width: width * 0.5,
      child: Column(
        children: [
          Row(
            children: [
              Text(
                "Details",
                style: TextStyle(fontSize: 30),
              ),
            ],
          ),
          SizedBox(height: 20),
          const Row(
            children: [
              Text(
                "Name of the Insured/Proposed",
                style: TextStyle(fontSize: 18),
              ),
              SizedBox(width: 150),
              Text(
                "Address",
                style: TextStyle(fontSize: 18),
              ),
              SizedBox(width: 300),
              Text(
                "Name of the Business",
                style: TextStyle(fontSize: 18),
              ),
              SizedBox(width: 150),
              Text(
                "Name of the Business",
                style: TextStyle(fontSize: 18),
              ),
            ],
          ),
          SizedBox(height: 10),
          Row(
            children: [
              Expanded(
                child: Container(
                  width: double.infinity,
                  child: const TextField(
                    decoration: InputDecoration(
                      hintText: "Name of the Insured/Proposed",
                      hintStyle: TextStyle(fontSize: 12),
                      border: OutlineInputBorder(
                        borderRadius: BorderRadius.all(Radius.circular(15)),
                      ),
                      contentPadding:
                          EdgeInsets.symmetric(vertical: 1, horizontal: 5),
                    ),
                  ),
                ),
              ),
              const SizedBox(
                width: 50,
              ),
              Expanded(
                child: Container(
                  width: double.infinity,
                  child: const TextField(
                    decoration: InputDecoration(
                      hintText: "Address",
                      hintStyle: TextStyle(fontSize: 12),
                      border: OutlineInputBorder(
                        borderRadius: BorderRadius.all(Radius.circular(15)),
                      ),
                      contentPadding:
                          EdgeInsets.symmetric(vertical: 1, horizontal: 5),
                    ),
                  ),
                ),
              ),
              const SizedBox(
                width: 80,
              ),
              Expanded(
                child: Container(
                  width: double.infinity,
                  child: const TextField(
                    decoration: InputDecoration(
                      hintText: "Name of the Business",
                      hintStyle: TextStyle(fontSize: 12),
                      border: OutlineInputBorder(
                        borderRadius: BorderRadius.all(Radius.circular(15)),
                      ),
                      contentPadding:
                          EdgeInsets.symmetric(vertical: 1, horizontal: 5),
                    ),
                  ),
                ),
              ),
              const SizedBox(
                width: 80,
              ),
              Expanded(
                child: Container(
                  width: double.infinity,
                  child: const TextField(
                    decoration: InputDecoration(
                      hintText: "Name of the Business",
                      hintStyle: TextStyle(fontSize: 12),
                      border: OutlineInputBorder(
                        borderRadius: BorderRadius.all(Radius.circular(15)),
                      ),
                      contentPadding:
                          EdgeInsets.symmetric(vertical: 1, horizontal: 5),
                    ),
                  ),
                ),
              ),
            ],
          ),
          SizedBox(height: 35),
          const Row(
            children: [
              Text(
                "Email Id",
                style: TextStyle(fontSize: 18),
              ),
              SizedBox(
                width: 800,
              ),
              Text(
                "Phone Number",
                style: TextStyle(fontSize: 18),
              ),
            ],
          ),
          SizedBox(height: 10),
          Row(
            children: [
              Expanded(
                child: Container(
                  width: double.infinity,
                  child: const TextField(
                    decoration: InputDecoration(
                      hintText: "Email Id",
                      hintStyle: TextStyle(fontSize: 12),
                      border: OutlineInputBorder(
                        borderRadius: BorderRadius.all(Radius.circular(15)),
                      ),
                      contentPadding:
                          EdgeInsets.symmetric(vertical: 1, horizontal: 5),
                    ),
                  ),
                ),
              ),
              const SizedBox(
                width: 80,
              ),
              Expanded(
                child: Container(
                  width: double.infinity,
                  child: const TextField(
                    decoration: InputDecoration(
                      hintText: "Phone Number",
                      hintStyle: TextStyle(fontSize: 12),
                      border: OutlineInputBorder(
                        borderRadius: BorderRadius.all(Radius.circular(15)),
                      ),
                      contentPadding:
                          EdgeInsets.symmetric(vertical: 1, horizontal: 5),
                    ),
                  ),
                ),
              ),
            ],
          ),
          SizedBox(height: 10),
          const Row(
            children: [
              Text(
                "Intermediary Details",
                style: TextStyle(fontSize: 25),
              ),
            ],
          ),
          SizedBox(height: 35),
          const Row(
            children: [
              Text(
                "Name of the Intermediary",
                style: TextStyle(fontSize: 18),
              ),
              SizedBox(width: 210),
              Text(
                "Contact Name",
                style: TextStyle(fontSize: 18),
              ),
              SizedBox(width: 250),
              Text(
                "Email Id",
                style: TextStyle(fontSize: 18),
              ),
              SizedBox(width: 250),
              Text(
                "Phone Number",
                style: TextStyle(fontSize: 18),
              ),
            ],
          ),
          SizedBox(
            height: 10,
          ),
          Row(
            children: [
              Expanded(
                child: Container(
                  width: double.infinity,
                  child: const TextField(
                    decoration: InputDecoration(
                      hintText: "Name of the Intermediary",
                      hintStyle: TextStyle(fontSize: 12),
                      border: OutlineInputBorder(
                        borderRadius: BorderRadius.all(Radius.circular(15)),
                      ),
                      contentPadding:
                          EdgeInsets.symmetric(vertical: 1, horizontal: 5),
                    ),
                  ),
                ),
              ),
              const SizedBox(
                width: 80,
              ),
              Expanded(
                child: Container(
                  width: double.infinity,
                  child: const TextField(
                    decoration: InputDecoration(
                      hintText: "Contact Name",
                      hintStyle: TextStyle(fontSize: 12),
                      border: OutlineInputBorder(
                        borderRadius: BorderRadius.all(Radius.circular(15)),
                      ),
                      contentPadding:
                          EdgeInsets.symmetric(vertical: 1, horizontal: 5),
                    ),
                  ),
                ),
              ),
              const SizedBox(
                width: 80,
              ),
              Expanded(
                child: Container(
                  width: double.infinity,
                  child: const TextField(
                    decoration: InputDecoration(
                      hintText: "Email Id",
                      hintStyle: TextStyle(fontSize: 12),
                      border: OutlineInputBorder(
                        borderRadius: BorderRadius.all(Radius.circular(15)),
                      ),
                      contentPadding:
                          EdgeInsets.symmetric(vertical: 1, horizontal: 5),
                    ),
                  ),
                ),
              ),
              const SizedBox(
                width: 80,
              ),
              Expanded(
                child: Container(
                  width: double.infinity,
                  child: const TextField(
                    decoration: InputDecoration(
                      hintText: "Phone Number",
                      hintStyle: TextStyle(fontSize: 12),
                      border: OutlineInputBorder(
                        borderRadius: BorderRadius.all(Radius.circular(15)),
                      ),
                      contentPadding:
                          EdgeInsets.symmetric(vertical: 1, horizontal: 5),
                    ),
                  ),
                ),
              ),
            ],
          ),
        ],
      ),
    );

    //             ],
    //           ),

    //         //
    //       ],

    //   ),
    // );;
  }
}
