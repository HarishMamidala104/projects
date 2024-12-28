import 'package:allrfq/Appcolor.dart';
import 'package:flutter/material.dart';

class ClaimsDetails extends StatefulWidget {
  const ClaimsDetails({super.key});

  @override
  State<ClaimsDetails> createState() => _ClaimsDetailsState();
}

class _ClaimsDetailsState extends State<ClaimsDetails> {
  @override
  Widget build(BuildContext context) {
    //double height = MediaQuery.of(context).size.height;
    double width = MediaQuery.of(context).size.width;
    return 
      SingleChildScrollView(
        scrollDirection: Axis.vertical,
        child:Padding(
          padding: const EdgeInsets.all(20.0),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.start,
              crossAxisAlignment: CrossAxisAlignment.start,
            children: [
            Text(
                  "Claim Details",
                  style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold),
                ),
                SizedBox(height: 8.0),
                Container(
                  child: Padding(
                    padding: const EdgeInsets.all(8.0),
                    child: Text(
                      "Policy Period",
                      style: TextStyle(
                        fontSize: 15.0,
                        color: Colors.black,
                        fontWeight: FontWeight.normal,
                      ),
                    ),
                  ),
                ),
                Container(
                  width: width * 0.4,
                  height: 50.0,
                  decoration: BoxDecoration(
                    borderRadius: BorderRadius.circular(8.0),
                    border: Border.all(
                      color: Colors.black12,
                      width: 2,
                    ),
                  ),
                  child: TextFormField(
                   // controller: policynumber,
                    // readOnly: true,
                    decoration: InputDecoration(
                      border: InputBorder.none,
                      hintText: "  Policy Period",
                      hintStyle: TextStyle(
                        color: AppColor.textColor,
                        fontWeight: FontWeight.normal,
                        fontSize: 12.0,
                      ),
                    ),
                  ),
                ),
                SizedBox(height: 8.0),
                Container(
                  child: Padding(
                    padding: const EdgeInsets.all(8.0),
                    child: Text(
                      "Premium Paid",
                      style: TextStyle(
                        fontSize: 15.0,
                        color: Colors.black,
                        fontWeight: FontWeight.normal,
                      ),
                    ),
                  ),
                ),
                Container(
                  width: width * 0.4,
                  height: 50.0,
                  decoration: BoxDecoration(
                    borderRadius: BorderRadius.circular(8.0),
                    border: Border.all(
                      color: Colors.black12,
                      width: 2,
                    ),
                  ),
                  child: TextFormField(
                   // controller: policynumber,
                    // readOnly: true,
                    decoration: InputDecoration(
                      border: InputBorder.none,
                      hintText: "  Premium Paid",
                      hintStyle: TextStyle(
                        color: AppColor.textColor,
                        fontWeight: FontWeight.normal,
                        fontSize: 12.0,
                      ),
                    ),
                  ),
                ),
                SizedBox(height: 8.0),
                Container(
                  child: Padding(
                    padding: const EdgeInsets.all(8.0),
                    child: Text(
                      "Total Sum Insured",
                      style: TextStyle(
                        fontSize: 15.0,
                        color: Colors.black,
                        fontWeight: FontWeight.normal,
                      ),
                    ),
                  ),
                ),
                Container(
                  width: width * 0.4,
                  height: 50.0,
                  decoration: BoxDecoration(
                    borderRadius: BorderRadius.circular(8.0),
                    border: Border.all(
                      color: Colors.black12,
                      width: 2,
                    ),
                  ),
                  child: TextFormField(
                   // controller: policynumber,
                    // readOnly: true,
                    decoration: InputDecoration(
                      border: InputBorder.none,
                      hintText: "  Total Sum Insured",
                      hintStyle: TextStyle(
                        color: AppColor.textColor,
                        fontWeight: FontWeight.normal,
                        fontSize: 12.0,
                      ),
                    ),
                  ),
                ),
                SizedBox(height: 8.0),
                Container(
                  child: Padding(
                    padding: const EdgeInsets.all(8.0),
                    child: Text(
                      "No.of Claims",
                      style: TextStyle(
                        fontSize: 15.0,
                        color: Colors.black,
                        fontWeight: FontWeight.normal,
                      ),
                    ),
                  ),
                ),
                Container(
                  width: width * 0.4,
                  height: 50.0,
                  decoration: BoxDecoration(
                    borderRadius: BorderRadius.circular(8.0),
                    border: Border.all(
                      color: Colors.black12,
                      width: 2,
                    ),
                  ),
                  child: TextFormField(
                   // controller: policynumber,
                    // readOnly: true,
                    decoration: InputDecoration(
                      border: InputBorder.none,
                      hintText: " No.of Claims",
                      hintStyle: TextStyle(
                        color: AppColor.textColor,
                        fontWeight: FontWeight.normal,
                        fontSize: 12.0,
                      ),
                    ),
                  ),
                ),
                SizedBox(height: 8.0),
                Container(
                  child: Padding(
                    padding: const EdgeInsets.all(8.0),
                    child: Text(
                      "Claimed Amount",
                      style: TextStyle(
                        fontSize: 15.0,
                        color: Colors.black,
                        fontWeight: FontWeight.normal,
                      ),
                    ),
                  ),
                ),
                Container(
                  width: width * 0.4,
                  height: 50.0,
                  decoration: BoxDecoration(
                    borderRadius: BorderRadius.circular(8.0),
                    border: Border.all(
                      color: Colors.black12,
                      width: 2,
                    ),
                  ),
                  child: TextFormField(
                   // controller: policynumber,
                    // readOnly: true,
                    decoration: InputDecoration(
                      border: InputBorder.none,
                      hintText: " Claimed Amount",
                      hintStyle: TextStyle(
                        color: AppColor.textColor,
                        fontWeight: FontWeight.normal,
                        fontSize: 12.0,
                      ),
                    ),
                  ),
                ),
                SizedBox(height: 8.0),
                Container(
                  child: Padding(
                    padding: const EdgeInsets.all(8.0),
                    child: Text(
                      "Settled Amount",
                      style: TextStyle(
                        fontSize: 15.0,
                        color: Colors.black,
                        fontWeight: FontWeight.normal,
                      ),
                    ),
                  ),
                ),
                Container(
                  width: width * 0.4,
                  height: 50.0,
                  decoration: BoxDecoration(
                    borderRadius: BorderRadius.circular(8.0),
                    border: Border.all(
                      color: Colors.black12,
                      width: 2,
                    ),
                  ),
                  child: TextFormField(
                   // controller: policynumber,
                    // readOnly: true,
                    decoration: InputDecoration(
                      border: InputBorder.none,
                      hintText: "  Settled Amount",
                      hintStyle: TextStyle(
                        color: AppColor.textColor,
                        fontWeight: FontWeight.normal,
                        fontSize: 12.0,
                      ),
                    ),
                  ),
                ),
                SizedBox(height: 8.0),
                Container(
                  child: Padding(
                    padding: const EdgeInsets.all(8.0),
                    child: Text(
                      "Pending Amount",
                      style: TextStyle(
                        fontSize: 15.0,
                        color: Colors.black,
                        fontWeight: FontWeight.normal,
                      ),
                    ),
                  ),
                ),
                Container(
                  width: width * 0.4,
                  height: 50.0,
                  decoration: BoxDecoration(
                    borderRadius: BorderRadius.circular(8.0),
                    border: Border.all(
                      color: Colors.black12,
                      width: 2,
                    ),
                  ),
                  child: TextFormField(
                   // controller: policynumber,
                    // readOnly: true,
                    decoration: InputDecoration(
                      border: InputBorder.none,
                      hintText: "  Pending Amount",
                      hintStyle: TextStyle(
                        color: AppColor.textColor,
                        fontWeight: FontWeight.normal,
                        fontSize: 12.0,
                      ),
                    ),
                  ),
                ),
                 Center(
                child: Container(
                  // color: AppColor.blueDarkColor,
                  child: ElevatedButton(
                    onPressed: () {},
                    style: ElevatedButton.styleFrom(
                      primary: Colors.blue,
                    ),
                    child: Text(
                      "Next",
                      style: TextStyle(color: Colors.white),
                    ),
                  ),
                ),
              )
            ],
          ),
        ),
      
    );
  }
}