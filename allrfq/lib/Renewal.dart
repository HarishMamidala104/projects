import 'package:allrfq/ClaimsDetails.dart';
import 'package:allrfq/Corporator.dart';
import 'package:allrfq/Coveragedetails.dart';
import 'package:allrfq/ExpiringDetails.dart';
import 'package:allrfq/PolicyDetails.dart';
import 'package:allrfq/SendRfq.dart';
import 'package:flutter/material.dart';

class RenewalPolicy extends StatefulWidget {
  const RenewalPolicy({super.key});

  @override
  State<RenewalPolicy> createState() => _RenewalPolicyState();
}

class _RenewalPolicyState extends State<RenewalPolicy> {
  @override
  Widget build(BuildContext context) {
    int currentStep = 0;
    void continueStep() {
      if (currentStep < currentStep + 1) {
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
        onStepContinue: currentStep < 5 ? continueStep : null,
        onStepCancel: cancelStep,
        steps: [
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
        ]);
  }
}
