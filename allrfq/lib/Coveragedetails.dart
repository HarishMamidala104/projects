import 'dart:io';
import 'package:flutter/material.dart';
import 'package:image_picker/image_picker.dart';

class Coveragedetails extends StatefulWidget {
  @override
  _CoveragedetailsState createState() => _CoveragedetailsState();
}

class _CoveragedetailsState extends State<Coveragedetails> {
  int? radioValue; // Added radioValue variable
  TextEditingController employeeDataController = TextEditingController();
  TextEditingController mandateLetterController = TextEditingController();
  TextEditingController coveragesSoughtController = TextEditingController();

  File? employeeDataFile;
  File? mandateLetterFile;
  File? coveragesSoughtFile;

  Future<void> chooseFile(TextEditingController controller) async {
    final pickedFile =
        await ImagePicker().pickImage(source: ImageSource.gallery);
    if (pickedFile != null) {
      setState(() {
        controller.text = pickedFile.path;
      });
    }
  }

  @override
  void dispose() {
    employeeDataController.dispose();
    mandateLetterController.dispose();
    coveragesSoughtController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return
       Container(
        padding: EdgeInsets.fromLTRB(55, 0, 0, 0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            SizedBox(height: 40.0),
            Text('Details', style: TextStyle(fontSize: 25.0)),
            SizedBox(height: 30.0),
            Row(
              children: [
                Expanded(
                  child: buildTextFieldWithLabel(
                    'Policy Type',
                    'Enter policy type',
                  ),
                ),
                SizedBox(width: 10.0),
                Expanded(
                  child: buildTextFieldWithLabel(
                      'Family Definition', 'Enter family definition'),
                ),
                SizedBox(width: 10.0),
                Expanded(
                  child: buildTextFieldWithLabel(
                      'Sum Insured', 'Enter sum insured'),
                ),
              ],
            ),
            SizedBox(height: 20.0),
            Row(
              children: [
                Expanded(
                  child: buildRadio(
                    1,
                    'I have Employee Data',
                  ),
                ),
                SizedBox(width: 4.0),
                Padding(
                  padding: const EdgeInsets.only(left: 8.0),
                  child: Expanded(
                    child: buildRadio(
                      2,
                      'I Need to get the Data From the Employee',
                    ),
                  ),
                ),
              ],
            ),
            Visibility(
              visible: radioValue == 1,
              child: const Column(
                children: [
                  Row(
                    children: [
                      Text(
                        "Employee & Dependent Data   ",
                        style: TextStyle(
                          color: Colors.black,
                          fontSize: 20,
                        ),
                      ),
                    ],
                  ),
                  SizedBox(
                    height: 20,
                  ),
                  Row(
                    children: [
                      Text(
                        "Mandate letter",
                        style: TextStyle(
                          color: Colors.black,
                          fontSize: 20,
                        ),
                        textAlign: TextAlign.right,
                      ),
                    ],
                  ),
                  SizedBox(
                    height: 20,
                  ),
                  Row(
                    children: [
                      Text(
                        "Coverages Sought",
                        style: TextStyle(
                          color: Colors.black,
                          fontSize: 20,
                        ),
                        textAlign: TextAlign.right,
                      ),
                    ],
                  ),
                ],
              ),
            ),
            SizedBox(height: 20.0),
            Row(
              children: [
                Expanded(
                  child: buildFilePickerTextField(
                    'Employee & Dependent Data',
                    employeeDataController,
                    employeeDataFile,
                    () => chooseFile(employeeDataController),
                  ),
                ),
                SizedBox(width: 10.0),
                Expanded(
                  child: buildFilePickerTextField(
                    'Mandate Letter',
                    mandateLetterController,
                    mandateLetterFile,
                    () => chooseFile(mandateLetterController),
                  ),
                ),
                SizedBox(width: 10.0),
                Expanded(
                  child: buildFilePickerTextField(
                    'Coverages Sought',
                    coveragesSoughtController,
                    coveragesSoughtFile,
                    () => chooseFile(coveragesSoughtController),
                  ),
                ),
              ],
            ),
          ],
        ),
      
    );
  }

  Widget buildLinkButton(String label, Function onPressed) {
    return TextButton(
      onPressed: onPressed as void Function()?,
      child: Text(
        label,
        style: TextStyle(
          color: Colors.blue,
          fontSize: 18.0,
        ),
      ),
    );
  }

  Widget buildTextFieldWithLabel(String label, String hintText) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Text(label, style: TextStyle(fontSize: 20.0)),
        SizedBox(height: 15.0),
        Container(
          width: 300.0,
          height: 40.0,
          child: TextFormField(
            decoration: InputDecoration(
              hintText: hintText,
              hintStyle: TextStyle(fontSize: 12),
              border: OutlineInputBorder(
                borderRadius: BorderRadius.all(Radius.circular(15)),
              ),
            ),
          ),
        ),
        SizedBox(height: 10.0),
      ],
    );
  }

  Widget buildRadio(int value, String label) {
    return Row(
      children: [
        Radio(
          value: value,
          groupValue: radioValue,
          onChanged: (int? value) {
            setState(() {
              radioValue = value;
            });
          },
        ),
        Text(
          label,
          style: TextStyle(fontSize: 18),
        ),
        SizedBox(
          width: 300,
        ),
      ],
    );
  }

  Widget buildFilePickerTextField(
    String label,
    TextEditingController controller,
    File? file,
    VoidCallback onPressed,
  ) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Text(
          label,
          style: TextStyle(fontSize: 20.0),
        ),
        SizedBox(height: 10.0),
        Stack(
          children: [
            Container(
              width: 300.0, // Adjust the width as desired
              child: TextFormField(
                controller: controller,
                readOnly: true,
                decoration: InputDecoration(
                  hintText: 'Choose File',
                  border: OutlineInputBorder(
                    borderRadius: BorderRadius.all(Radius.circular(15)),
                  ),
                ),
              ),
            ),
            Positioned(
              child: ElevatedButton(
                onPressed: onPressed,
                style: ElevatedButton.styleFrom(
                  padding:
                      EdgeInsets.symmetric(vertical: 12.0, horizontal: 16.0),
                  textStyle: TextStyle(fontSize: 14.0),
                ),
                child: Text('Choose File'),
              ),
            ),
          ],
        ),
      ],
    );
  }
}

