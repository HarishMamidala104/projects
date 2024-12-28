import 'package:allrfq/ClaimsDetails.dart';
import 'package:allrfq/Corporator.dart';
import 'package:allrfq/Coveragedetails.dart';
import 'package:allrfq/ExpiringDetails.dart';
import 'package:allrfq/PolicyDetails.dart';
import 'package:allrfq/SendRfq.dart';
import 'package:flutter/material.dart';
import 'package:graphql_flutter/graphql_flutter.dart';
import 'custom.dart';

class StepPage extends StatelessWidget {
  final String title;
  final String description;
  StepPage({required this.title, required this.description});
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(title),
      ),
      body: Center(
        child: Text(description),
      ),
    );
  }
}

class RfqCopy extends StatefulWidget {
  const RfqCopy({super.key});
  @override
  State<RfqCopy> createState() => _RfqCopyState();
}

class _RfqCopyState extends State<RfqCopy> {
  String? selectedProductCategory;
  String? productCategeory;
  List<Map<String, dynamic>> productCategeoryList = [];
  String? selectedProductName;
  List<dynamic> productNameList = [];
  String? selectedProductTypelist;
  List<String> dropdownItems = List.generate(50, (index) => 'Item $index');
  List<String> policyTypeList = ['Fresh', 'Renewal', 'Both'];
  bool isVisible = false;
  int currentStep = 0;
  @override
  void dispose() {
    // You can safely refer to the ancestor widget here using 'ancestor'
    // ... dispose logic ...

    super.dispose();
  }

  void continueStep() {
    if (currentStep < getFreshSteps().length - 1) {
      setState(() {
        currentStep++;
      });
    }
  }

  void cancelStep() {
    if (currentStep > 0) {
      setState(() {
        currentStep--;
      });
    }
  }

  void onStepTapped(int value) {
    setState(() {
      currentStep = value;
    });
  }

  @override
  void initState() {
    // TODO: implement initState
    super.initState();
  }

  @override
  void didChangeDependencies() {
    super.didChangeDependencies();

    fetchDropdownList(GraphQLProvider.of(context).value);
  }

  void _toggleDisplay() {
    setState(() {
      isVisible = !isVisible;
    });
  }

  void fetchDropdownOptionsProductList(GraphQLClient client) async {
    try {
      final String productfetcherQuery = r'''
      query findNameByProductType($productCategeory: String) {
        findNameByProductType(productCategeory: $productCategeory) {
          productName
        }
      }
    ''';

      final String? productCategory = selectedProductCategory;
      // Replace with the actual product category you want to query
      print(productCategory);
      final QueryOptions options = QueryOptions(
        document: gql(productfetcherQuery),
        variables: {
          'productCategeory': productCategory,
        },
      );

      final QueryResult result = await client.query(options);

      if (!result.hasException) {
        if (mounted) {
          setState(() {
            productNameList =
                result.data?['findNameByProductType']['productName'];
          });
        }
        print(productNameList);
      } else {
        print('Failed to fetch dropdown options');
      }
    } catch (error) {
      print('Error: $error');
    }
  }

  void showErrorMessage(String message) {
    final snackBar = SnackBar(
      content: Text(message),
      backgroundColor: Colors.red,
    );
    ScaffoldMessenger.of(context).showSnackBar(snackBar);
  }

  void showSuccessMessage(String message) {
    final snackBar = SnackBar(
      content: Text(message),
      backgroundColor: Colors.green,
    );
    ScaffoldMessenger.of(context).showSnackBar(snackBar);
  }

  void fetchDropdownList(GraphQLClient client) async {
    print("calling fetch");
    final String fetcherQuery = r''' 
   query{
  getAll{
    productCategeory
  }
}''';

    final QueryOptions options = QueryOptions(
      document: gql(fetcherQuery),
    );

    final QueryResult response = await client.query(options);
    print(response);
    if (!response.hasException) {
      if (mounted) {
        final dynamic data = response.data;
        if (data != null && data['getAll'] != null) {
          setState(() {
            productCategeoryList =
                (data['getAll'] as List<dynamic>).cast<Map<String, dynamic>>();
          });
        }
      }
    } else {
      final errorMessage = response.exception?.graphqlErrors.first.message;
      showErrorMessage('Error: $errorMessage');
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Column(
        children: [
          Container(
            child: Column(
              children: [
                const Row(
                  children: [
                    SizedBox(width: 40),
                    Text(
                      'Select Product Category',
                      style: TextStyle(
                        fontSize: 16.0,
                        color: Colors.black,
                      ),
                    ),
                    SizedBox(width: 190.0),
                    Text(
                      'Select Product',
                      style: TextStyle(
                        fontSize: 16.0,
                        color: Colors.black,
                      ),
                    ),
                    SizedBox(width: 220.0),
                    Text(
                      'Policy Type',
                      style: TextStyle(
                        fontSize: 16.0,
                        color: Colors.black,
                      ),
                    ),
                  ],
                ),
                Padding(
                  padding: const EdgeInsets.only(left: 100),
                  child: Row(
                    children: [
                      Container(
                        height: 35,
                        width: 300,
                        child: Padding(
                          padding: const EdgeInsets.only(left: 5),
                          child: CustomDropdown(
                            value: selectedProductCategory,
                            onChanged: (String? newValue) {
                              setState(() {
                                selectedProductCategory = newValue;
                                selectedProductName = null;
                                selectedProductTypelist=null;

                              });
                              fetchDropdownOptionsProductList(
                                  GraphQLProvider.of(context).value);
                            },
                            items: [
                              DropdownMenuItem<String>(
                                value: null,
                                child: Text(
                                  'Select a product category',
                                  style: TextStyle(
                                    fontSize: 14.0,
                                  ),
                                ),
                              ),
                              ...productCategeoryList.map((category) {
                                return DropdownMenuItem<String>(
                                  value: category['productCategeory'],
                                  child: Text(
                                    category['productCategeory'],
                                    style: TextStyle(
                                      fontSize: 14.0,
                                    ),
                                  ),
                                );
                              }).toList(),
                            ],
                          ),
                        ),
                      ),
                      const SizedBox(width: 30.0),
                      Container(
                        // height: 35,
                        width: 300,
                        child: Padding(
                          padding: const EdgeInsets.only(left: 5),
                          
                          child: DropdownButton<String>(
                            value: selectedProductName,
                            hint: const Text(
                              'Select Product Name',
                              style: TextStyle(
                                fontSize: 14.0,
                              ),
                            ),
                            isExpanded: true,
                            icon: const Icon(Icons.keyboard_arrow_down),
                            underline: Container(),
                            items: [
                              DropdownMenuItem<String>(
                                value: null,
                                child: const Text(
                                  'Select a product Name',
                                  style: TextStyle(
                                    fontSize: 14.0,
                                  ),
                                ),
                              ),
                              ...productNameList.map((productName) {
                                print(productName.toString());
                                return DropdownMenuItem<String>(
                                  value: productName,
                                  child: Text(productName),
                                );
                              }).toList(),
                            ],
                            onChanged: (String? userSelectedProductName) {
                              setState(() {
                                selectedProductName = userSelectedProductName;
                              });
                            },
                          ),
                        ),
                      ),
                      const SizedBox(width: 30.0),
                      Container(
                        height: 35,
                        width: 200,
                        decoration: BoxDecoration(
                          borderRadius: BorderRadius.circular(4),
                          border: Border.all(),
                        ),
                        child: DropdownButton<String>(
                          value: selectedProductTypelist,
                          hint: const Text(
                            'Select Policy',
                          ),
                          isExpanded: true,
                          icon: const Icon(
                            Icons.keyboard_arrow_down,
                          ),
                          underline: Container(),
                          items:
                              policyTypeList.map((String selectedPolicyType) {
                            return DropdownMenuItem<String>(
                              value: selectedPolicyType,
                              child: Text(selectedPolicyType),
                            );
                          }).toList(),
                          onChanged: (String? userSelectedPolicy) {
                            setState(() {
                              selectedProductTypelist = userSelectedPolicy;
                               currentStep = 0;
                            });
                          },
                        ),
                      ),
                      const SizedBox(width: 20.0),
                      Container(
                        height: 50,
                        width: 100,
                        child: ElevatedButton(
                          child: const Text('Add RFQ'),
                          onPressed: _toggleDisplay,
                          style: ElevatedButton.styleFrom(
                            fixedSize: Size(200, 50),
                            primary: Colors.blue,
                          ),
                        ),
                      ),
                    ],
                  ),
                ),
              ],
            ),
          ),
          Visibility(
            visible: isVisible,
            child: Row(
              children: [
                Expanded(
                    child: Container(
                  height: 700,
                  child: Stepper(
                      type: StepperType.horizontal,
                      currentStep: currentStep,
                     onStepContinue: currentStep < 5 ? continueStep : null,
                      onStepCancel: cancelStep,
                     // onStepContinue: () {},
                      steps: selectedProductTypelist == 'Fresh'
                  ? getFreshSteps()
                  : getRenewalSteps(),)
                    //  steps: getCurrentSteps()),
                )),
              ],
            ),
          )
        ],
      ),
    );
  }

  List<Step> getFreshSteps() {
    return [
      Step(
        title: const Text("Corporate"),
        content: Coporates(),
        isActive: currentStep >= 0,
        state: StepState.complete,
      ),
      Step(
        title: const Text("Coverage"),
        content: Coveragedetails(),
        isActive: currentStep >= 1,
        state: StepState.complete,
      ),

      Step(
        title: const Text("Policy"),
        content: PolicyType(), // Replace this with your actual widget
        isActive: currentStep >= 2,
        state: StepState.complete,
      ), // Add other steps for Fresh policy type
      Step(
        title: const Text("SendRFQ"),
        content: SendForm(),
        isActive: currentStep >= 3,
        state: StepState.complete,
      ),
    ];
  }

  List<Step> getRenewalSteps() {
    return [
      Step(
        title: const Text("Corporate"),
        content: Coporates(), // Replace this with your actual widget
        isActive: currentStep >= 0,
        state: StepState.complete,
      ),
      Step(
        title: const Text("Coverage"),
        content: Coveragedetails(), // Replace this with your actual widget
        isActive: currentStep >= 1,
        state: StepState.complete,
      ),
      Step(
        title: const Text("Expiring Details"),
        content: ExpiringDetails(), // Replace this with your actual widget
        isActive: currentStep >= 2,
        state: StepState.complete,
      ),
      Step(
        title: const Text("Claims Details"),
        content: ClaimsDetails(), // Replace this with your actual widget
        isActive: currentStep >= 3,
        state: StepState.complete,
      ),
      Step(
        title: const Text("Policy"),
        content: PolicyType(), // Replace this with your actual widget
        isActive: currentStep >= 4,
        state: StepState.complete,
      ),
      Step(
        title: const Text("SendRFQ"),
        content: SendForm(), // Replace this with your actual widget
        isActive: currentStep >= 5,
        state: StepState.complete,
      ),
    ];
  }

  List<Step> getCurrentSteps() {
    if (selectedProductTypelist == 'Fresh') {
      return getFreshSteps();
    } else if (selectedProductTypelist == 'Renewal') {
      return getRenewalSteps();
    } else {
      // Default to Fresh steps if policy type is not selected or Both
      return getFreshSteps();
    }
  }
}
