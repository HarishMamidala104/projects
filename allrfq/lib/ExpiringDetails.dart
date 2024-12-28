import 'package:allrfq/Appcolor.dart';
import 'package:allrfq/ClaimsDetails.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
//import 'package:table_calendar/table_calendar.dart';

class ExpiringDetails extends StatefulWidget {
  const ExpiringDetails({super.key});

  @override
  State<ExpiringDetails> createState() => _ExpiringDetailsState();
}

class _ExpiringDetailsState extends State<ExpiringDetails> {
  late TextEditingController _dateController = TextEditingController();
  late TextEditingController _enddateController = TextEditingController();
  late TextEditingController policynumber = TextEditingController();
  final GlobalKey<FormState> _formKey = GlobalKey<FormState>();
  //TableCalendarController _calendarController = TableCalendarController();
  DateTime? Date;
  final now = DateTime.now();

  @override
  void dispose() {
    _dateController.dispose();
    // _calendarController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    //double height = MediaQuery.of(context).size.height;
    double width = MediaQuery.of(context).size.width;
    return 
      Form(
        key: _formKey,
        child: SingleChildScrollView(
          scrollDirection: Axis.vertical,
          child: Padding(
            padding: const EdgeInsets.all(28.0),
            child: Column(
              mainAxisAlignment: MainAxisAlignment.start,
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(
                  "Expiry Policy Details",
                  style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold),
                ),
                SizedBox(height: 8.0),
                Container(
                  child: Padding(
                    padding: const EdgeInsets.all(8.0),
                    child: Text(
                      "Policy Number",
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
                    controller: policynumber,
                    // readOnly: true,
                    validator: _validatePolicyNumber,
                    decoration: InputDecoration(
                      border: InputBorder.none,
                      hintText: "   Name of intermediary",
                      hintStyle: TextStyle(
                        color: AppColor.textColor,
                        fontWeight: FontWeight.normal,
                        fontSize: 12.0,
                      ),
                    ),
                  ),
                ),
                SizedBox(height: 8.0),
                Text(
                  "Period of Insurance",
                  style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold),
                ),
                SizedBox(height: 8.0),
                Container(
                  child: Padding(
                    padding: const EdgeInsets.all(8.0),
                    child: Text(
                      "start",
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
                    controller: _dateController,
                    readOnly: true,
                    decoration: InputDecoration(
                      hintText: '   mm/dd/yy',
                      suffixIcon: Icon(Icons.calendar_today),
                    ),
                    onTap: () {
                      _selectDate(context);
                    },
                  ),
                ),
                SizedBox(height: 8.0),
                Container(
                  child: Padding(
                    padding: const EdgeInsets.all(8.0),
                    child: Text(
                      "end",
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
                    controller: _enddateController,
                    readOnly: true,
                    decoration: InputDecoration(
                      hintText: '   mm/dd/yy',
                      suffixIcon: Icon(Icons.calendar_today),
                    ),
                    onTap: () {
                      _endDate(context);
                    },
                  ),
                ),
                SizedBox(height: 16.0),
                Text(
                  "Permium Details",
                  style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold),
                ),
                SizedBox(height: 8.0),
                Container(
                  child: Padding(
                    padding: const EdgeInsets.all(8.0),
                    child: Text(
                      " Premium Paid at Inception",
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
                    //controller: _dateController,
                    //readOnly: true,

                    decoration: InputDecoration(
                      border: InputBorder.none,
                      hintText: "   Premium Paid at Inception",
                      hintStyle: TextStyle(
                        color: AppColor.textColor,
                        fontWeight: FontWeight.normal,
                        fontSize: 12.0,
                      ),
                    ),
                    validator: _validatePermiumInception,
                  ),
                ),
                const SizedBox(height: 8.0),
                Container(
                  child: Padding(
                    padding: const EdgeInsets.all(8.0),
                    child: Text(
                      "As on Date Premium",
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
                    //controller: _dateController,
                    //readOnly: true,
                    validator: _validateDatePremium,
                    decoration: InputDecoration(
                      border: InputBorder.none,
                      hintText: "   As on Date Premium",
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
                      "Addition Premium",
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
                    //controller: _dateController,
                    //readOnly: true,
                    validator: _validateAdditionPremium,
                    decoration: InputDecoration(
                      border: InputBorder.none,
                      hintText: "   Addition Premium",
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
                      "Deletion Premium",
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
                    // controller: _dateController,
                    //readOnly: true,
                    validator: _validateDeletionPremium,
                    decoration: InputDecoration(
                      border: InputBorder.none,
                      hintText: "   Deletion Premium",
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
                      "Policy Type",
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
                    //controller: _dateController,
                    //readOnly: true,
                    validator: _validatePolicyType,
                    decoration: InputDecoration(
                      border: InputBorder.none,
                      hintText: "  Policy Type",
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
                      "Active Years",
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
                    // controller: _dateController,
                    //readOnly: true,
                    validator: _validateActiveYears,
                    decoration: InputDecoration(
                      border: InputBorder.none,
                      hintText: "   Active Years",
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
                      onPressed: () {
                       
                        if (_formKey.currentState!.validate()) {
                          // Validation successful, process the data
                          // Your logic for saving or navigating to the next step goes here
                           ClaimsDetails();
                          print('Form is valid.');
                        }
                      },
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
                // _buildCalendar(),
              ],
            ),
          ),
        ),
      
    );
  }

  Future<void> _selectDate(BuildContext context) async {
    final DateTime? picked = await showDatePicker(
      context: context,
      initialDate: Date ?? now,
      firstDate: DateTime(1900),
      lastDate: DateTime(2101),
    );
    if (picked != null && picked != Date) {
      setState(() {
        Date = picked;
        _dateController.text = DateFormat('yyyy-MM-dd').format(Date!);
      });
    }
  }

  Future<void> _endDate(BuildContext context) async {
    final DateTime? picked = await showDatePicker(
      context: context,
      initialDate: Date ?? now,
      firstDate: DateTime(1900),
      lastDate: DateTime(2100),
    );

    if (picked != null && picked != Date) {
      setState(() {
        Date = picked;
        _enddateController.text = DateFormat('yyyy-MM-dd').format(Date!);
      });
    }
  }

  // Validation function for Policy Number
  String? _validatePolicyNumber(String? value) {
    if (value == null || value.isEmpty) {
      return 'Please enter the Policy Number';
    }
    return null; // Return null if the input is valid
  }

  String? _validatePermiumInception(String? value) {
    if (value == null || value.isEmpty) {
      return 'Please enter the Premium Paid at Inception';
    }
    return null; // Return null if the input is valid
  }

  String? _validateDatePremium(String? value) {
    if (value == null || value.isEmpty) {
      return 'Please enter the DatePremium';
    }
    return null; // Return null if the input is valid
  }

  String? _validateAdditionPremium(String? value) {
    if (value == null || value.isEmpty) {
      return 'Please enter the  Addition Premium';
    }
    return null; // Return null if the input is valid
  }

  String? _validateDeletionPremium(String? value) {
    if (value == null || value.isEmpty) {
      return 'Please enter the  Deletion Premium';
    }
    return null; // Return null if the input is valid
  }

  String? _validatePolicyType(String? value) {
    if (value == null || value.isEmpty) {
      return 'Please enter the Policy Type';
    }
    return null; // Return null if the input is valid
  }

  String? _validateActiveYears(String? value) {
    if (value == null || value.isEmpty) {
      return 'Please enter the Active Years';
    }
    return null; // Return null if the input is valid
  }
}
