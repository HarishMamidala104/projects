import 'package:flutter/material.dart';

class PolicyType extends StatefulWidget {
  @override
  _PolicyTypeState createState() => _PolicyTypeState();
}

class _PolicyTypeState extends State<PolicyType> {
  List<List<String>> tableData = [];
  int rowCounter = 1;

  @override
  Widget build(BuildContext context) {
    //double height = MediaQuery.of(context).size.height;
    double width = MediaQuery.of(context).size.width;
    return  Container(
        width: width,
      //  height: height,
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            const Row(children: [
              Text(
                "Proposed Scope of Cover",
                style: TextStyle(fontSize: 25),
              ),
            ]),
            SizedBox(
              height: 20,
            ),
            Container(
              color: Colors.blue,
              child: const Padding(
                padding: EdgeInsets.symmetric(vertical: 10),
                child: Expanded(
                  child: Row(
                    children: [
                      Text(
                        'Coverage',
                        style: TextStyle(
                          color: Color.fromARGB(255, 249, 249, 249),
                          fontWeight: FontWeight.bold,
                        ),
                      ),
                      SizedBox(
                        width: 700,
                      ),
                      Text(
                        'Limits/Remarks',
                        style: TextStyle(
                          color: Color.fromARGB(255, 249, 249, 249),
                          fontWeight: FontWeight.bold,
                        ),
                      ),
                      SizedBox(width: 400),
                      Text(
                        'Actions',
                        style: TextStyle(
                          color: Color.fromARGB(255, 249, 249, 249),
                          fontWeight: FontWeight.bold,
                        ),
                      ),
                    ],
                  ),
                ),
              ),
            ),

            for (var i = 0; i < tableData.length; i++)
              Row(
                children: [
                  Expanded(
                    child: Container(
                      margin: EdgeInsets.only(right: 16),
                      width: 450,
                      height: 33,
                      child: const TextField(
                        decoration: InputDecoration(
                          border: OutlineInputBorder(
                            borderRadius: BorderRadius.all(Radius.circular(15)),
                          ),
                          hintText: 'Enter a search term',
                          hintStyle: TextStyle(fontSize: 12),
                        ),
                      ),
                    ),
                  ),
                  SizedBox(
                    width: 200,
                  ),
                  Expanded(
                    child: Container(
                      margin: EdgeInsets.only(right: 16),
                      width: 450,
                      height: 33,
                      child: const TextField(
                        decoration: InputDecoration(
                          border: OutlineInputBorder(
                            borderRadius: BorderRadius.all(Radius.circular(15)),
                          ),
                          hintText: 'Enter a search term',
                          hintStyle: TextStyle(fontSize: 12),
                        ),
                      ),
                    ),
                  ),
                  SizedBox(
                    width: 60,
                  ),
                  IconButton(
                    onPressed: () {
                      setState(() {
                        // Handle edit button action for the current row
                        // Example: Update the data at index i
                        tableData[i] = ['New Data 1', 'New Data 2'];
                      });
                    },
                    icon: Icon(Icons.edit_note),
                  ),
                  IconButton(
                    onPressed: () {
                      setState(() {
                        // Handle delete button action for the current row
                        // Example: Remove the data at index i
                        tableData.removeAt(i);
                      });
                    },
                    icon: Icon(
                      Icons.delete,
                      color: Color.fromARGB(255, 252, 0, 0),
                    ),
                  ),
                ],
              ),

            Row(
              children: [
                Expanded(
                  child: Container(
                    margin: EdgeInsets.only(right: 16),
                    width: 450,
                    height: 33,
                    child: TextField(
                      decoration: InputDecoration(
                        border: OutlineInputBorder(),
                        hintText: 'Enter a search term',
                      ),
                    ),
                  ),
                ),
                SizedBox(
                  width: 200,
                ),
                Expanded(
                  child: Container(
                    margin: EdgeInsets.only(right: 16),
                    width: 450,
                    height: 33,
                    child: TextField(
                      decoration: InputDecoration(
                        border: OutlineInputBorder(),
                        hintText: 'Enter a search term',
                      ),
                    ),
                  ),
                ),
                SizedBox(
                  width: 85,
                ),
                IconButton(
                  onPressed: () {
                    setState(() {
                      tableData.add(['Data 1', 'Data 2']);
                      rowCounter++;
                    });
                  },
                  icon: Icon(Icons.add_circle_outline),
                ),
              ],
            ),
          
          ],
        ),
      );
   
  }
}