import 'package:allrfq/Corporator.dart';
import 'package:allrfq/Coveragedetails.dart';
import 'package:allrfq/PolicyDetails.dart';
import 'package:allrfq/SendRfq.dart';
import 'package:flutter/material.dart';

class FreshPolicy extends StatefulWidget {
  const FreshPolicy({super.key});

  @override
  State<FreshPolicy> createState() => _FreshPolicyState();
}

class _FreshPolicyState extends State<FreshPolicy> {
  @override
  Widget build(BuildContext context) {
    int currentStep=0;
     void continueStep() {
    if (currentStep < currentStep +1) {
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
    return Stepper(
      
      onStepContinue: currentStep < 3 ? continueStep : null,
                       onStepCancel: cancelStep,
      
      steps: [
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
    ]);
  }
}