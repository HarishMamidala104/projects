
import 'package:flutter/material.dart';
import 'package:wellness_ui/app_colors.dart';
import 'package:wellness_ui/app_icons.dart';
import 'package:wellness_ui/app_images.dart';
import 'package:wellness_ui/app_styles.dart';
import 'package:responsive_builder/responsive_builder.dart';

class SubscriptionPlan extends StatefulWidget {
  const SubscriptionPlan({Key? key}) : super(key: key);

  @override
  State<SubscriptionPlan> createState() => _SubscriptionPlanState();
}

class _SubscriptionPlanState extends State<SubscriptionPlan> {
  final TextEditingController nameController = TextEditingController();
  final TextEditingController emailController = TextEditingController();

  @override
  void dispose() {
    nameController.dispose();
    emailController.dispose();
    super.dispose();
  }

  Widget buildInputField({
    required String labelText,
    required String suffixIcon,
    required TextEditingController controller,
  }) {
    return Container(
      height: 40.0,
      constraints: const BoxConstraints(maxWidth: 401),
      decoration: BoxDecoration(
        borderRadius: BorderRadius.circular(3.0),
        color: AppColors.whiteColor,
      ),
      child: TextFormField(
        controller: controller,
        style: ralewayStyle.copyWith(
          fontWeight: FontWeight.w400,
          color: AppColors.blueDarkColor,
          fontSize: 16.0,
        ),
        decoration: InputDecoration(
          labelText: labelText,
          enabledBorder: const OutlineInputBorder(
            borderSide: BorderSide(color: AppColors.lightblueColor),
          ),
          focusedBorder: const OutlineInputBorder(
            borderSide: BorderSide(color: AppColors.lightblueColor),
          ),
          suffixIcon: IconButton(
            onPressed: () {},
            icon: Image.asset(suffixIcon),
          ),
          contentPadding: const EdgeInsets.only(top: 16.0, left: 20.0),
          hintStyle: ralewayStyle.copyWith(
            fontWeight: FontWeight.w400,
            color: AppColors.blueDarkColor.withOpacity(0.8),
            fontSize: 14.0,
          ),
        ),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Stack(
        children: [
          // Background Image
          Image.asset(
            AppImages.banner2,
            fit: BoxFit.cover,
            width: double.infinity,
            height: double.infinity,
          ),
          // Container with Input Boxes
          Padding(
            padding: const EdgeInsets.all(40.0),
            child: Container(
              color: Colors.white,
              padding: const EdgeInsets.all(16.0),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  const Row(
                    children: [
                      Align(
                        alignment: Alignment.centerLeft,
                        child: SizedBox(
                          child: Image(
                            image: AssetImage(AppImages.logo),
                            width: 60,
                            height: 50,
                          ),
                        ),
                      ),
                    ],
                  ),
                  Center(
                    child: Column(
                      children: [
                        const Text(
                          'Subscribe',
                          style: TextStyle(
                            fontSize: 40,
                            fontWeight: FontWeight.w600,
                            color: AppColors.textColor,
                          ),
                        ),
                        const SizedBox(height: 5),
                        Container(
                          constraints: const BoxConstraints(maxWidth: 401),
                          decoration: BoxDecoration(
                            borderRadius: BorderRadius.circular(3.0),
                            color: AppColors.planTypeColor,
                          ),
                          child: Padding(
                            padding: const EdgeInsets.all(6.0),
                            child: Row(
                              mainAxisAlignment: MainAxisAlignment.spaceBetween,
                              children: [
                                const Text(
                                  "Basic Plan -",
                                  textAlign: TextAlign.left,
                                  style: TextStyle(
                                      fontSize: 25,
                                      color: AppColors.whiteColor),
                                ),
                                RichText(
                                  text: TextSpan(children: [
                                    TextSpan(
                                        text: " ₹ ",
                                        style: ralewayStyle.copyWith(
                                          fontSize: 23.0,
                                          color: AppColors.whiteColor,
                                          fontWeight: FontWeight.w200,
                                        )),
                                    TextSpan(
                                        text: "100",
                                        style: ralewayStyle.copyWith(
                                          fontSize: 37.0,
                                          color: AppColors.whiteColor,
                                          fontWeight: FontWeight.w600,
                                        )),
                                    TextSpan(
                                        text: "/PPPM",
                                        style: ralewayStyle.copyWith(
                                          fontSize: 20.0,
                                          color: AppColors.whiteColor,
                                          fontWeight: FontWeight.w200,
                                        ))
                                  ]),
                                )
                              ],
                            ),
                          ),
                        ),
                        const SizedBox(height: 7),
                        ResponsiveBuilder(
                          builder: (context, sizingInformation) {
                            if (sizingInformation.deviceScreenType ==
                                    DeviceScreenType.mobile ||
                                sizingInformation.deviceScreenType ==
                                    DeviceScreenType.tablet) {
                              // Mobile view layout
                              return SingleChildScrollView(
                                child: Column(
                                  children: [
                                    Column(
                                      mainAxisAlignment:
                                          MainAxisAlignment.center,
                                      children: [
                                        const SizedBox(height: 25),
                                        buildInputField(
                                          labelText: 'Name',
                                          suffixIcon: AppIcons.emailIcon,
                                          controller: nameController,
                                        ),
                                        const SizedBox(height: 16.0),
                                        buildInputField(
                                          labelText: 'Email',
                                          suffixIcon: AppIcons.emailIcon,
                                          controller: emailController,
                                        ),
                                      ],
                                    ),
                                    Column(
                                      mainAxisAlignment:
                                          MainAxisAlignment.center,
                                      children: [
                                        const SizedBox(height: 25),
                                        buildInputField(
                                          labelText: 'Name',
                                          suffixIcon: AppIcons.emailIcon,
                                          controller: nameController,
                                        ),
                                        const SizedBox(height: 16.0),
                                        buildInputField(
                                          labelText: 'Email',
                                          suffixIcon: AppIcons.emailIcon,
                                          controller: emailController,
                                        ),
                                      ],
                                    ),
                                  ],
                                ),
                              );
                            } else {
                              // Desktop view layout
                              return Column(
                                children: [
                                  Center(
                                    child: Row(
                                      mainAxisAlignment:
                                          MainAxisAlignment.center,
                                      children: [
                                        SizedBox(height: 50),
                                        buildInputField(
                                          labelText: 'Name',
                                          suffixIcon: AppIcons.emailIcon,
                                          controller: nameController,
                                        ),
                                        SizedBox(width: 200),
                                        buildInputField(
                                          labelText: 'Email',
                                          suffixIcon: AppIcons.lockIcon,
                                          controller: emailController,
                                        ),
                                      ],
                                    ),
                                  ),
                                  Center(
                                    child: Row(
                                      mainAxisAlignment:
                                          MainAxisAlignment.center,
                                      children: [
                                        SizedBox(height: 50),
                                        buildInputField(
                                          labelText: 'Name',
                                          suffixIcon: AppIcons.emailIcon,
                                          controller: nameController,
                                        ),
                                        SizedBox(width: 200),
                                        buildInputField(
                                          labelText: 'Email',
                                          suffixIcon: AppIcons.lockIcon,
                                          controller: emailController,
                                        ),
                                      ],
                                    ),
                                  ),
                                  Center(
                                    child: Row(
                                      mainAxisAlignment:
                                          MainAxisAlignment.center,
                                      children: [
                                        SizedBox(height: 50),
                                        buildInputField(
                                          labelText: 'Name',
                                          suffixIcon: AppIcons.emailIcon,
                                          controller: nameController,
                                        ),
                                        SizedBox(width: 200),
                                        buildInputField(
                                          labelText: 'Email',
                                          suffixIcon: AppIcons.lockIcon,
                                          controller: emailController,
                                        ),
                                      ],
                                    ),
                                  ),
                                  Center(
                                    child: Row(
                                      mainAxisAlignment:
                                          MainAxisAlignment.center,
                                      children: [
                                        SizedBox(height: 50),
                                        buildInputField(
                                          labelText: 'Name',
                                          suffixIcon: AppIcons.emailIcon,
                                          controller: nameController,
                                        ),
                                        SizedBox(width: 200),
                                        buildInputField(
                                          labelText: 'Email',
                                          suffixIcon: AppIcons.lockIcon,
                                          controller: emailController,
                                        ),
                                      ],
                                    ),
                                  ),
                                  Center(
                                    child: Row(
                                      mainAxisAlignment:
                                          MainAxisAlignment.center,
                                      children: [
                                        SizedBox(height: 50),
                                        buildInputField(
                                          labelText: 'Name',
                                          suffixIcon: AppIcons.emailIcon,
                                          controller: nameController,
                                        ),
                                        SizedBox(width: 200),
                                        buildInputField(
                                          labelText: 'Email',
                                          suffixIcon: AppIcons.lockIcon,
                                          controller: emailController,
                                        ),
                                      ],
                                    ),
                                  ),
                                  SizedBox(
                                    height: 30,
                                  ),
                                  Center(
                                    child: Row(
                                      mainAxisAlignment:
                                          MainAxisAlignment.center,
                                      children: [
                                        SizedBox(height: 50),
                                        LayoutBuilder(
                                          builder: (context, constraints) {
                                            final maxWidth =
                                                constraints.maxWidth;
                                            final buttonWidth = maxWidth < 400
                                                ? maxWidth.toDouble()
                                                : 400.0;

                                            return Container(
                                              width: buttonWidth,
                                              child: ElevatedButton(
                                                style: ButtonStyle(
                                                  backgroundColor:
                                                      MaterialStateProperty.all<
                                                              Color>(
                                                          AppColors.greyButton),
                                                ),
                                                onPressed: () {},
                                                child: Padding(
                                                  padding: const EdgeInsets.all(
                                                      12.0),
                                                  child: Text('Cancel'),
                                                ),
                                              ),
                                            );
                                          },
                                        ),
                                        SizedBox(width: 50),
                                        LayoutBuilder(
                                          builder: (context, constraints) {
                                            final maxWidth =
                                                constraints.maxWidth;
                                            final buttonWidth = maxWidth < 400
                                                ? maxWidth.toDouble()
                                                : 400.0;

                                            return Container(
                                              width: buttonWidth,
                                              child: ElevatedButton(
                                                style: ButtonStyle(
                                                  backgroundColor:
                                                      MaterialStateProperty
                                                          .all<Color>(AppColors
                                                              .lightblueColor),
                                                ),
                                                onPressed: () {},
                                                child: Padding(
                                                  padding: const EdgeInsets.all(
                                                      12.0),
                                                  child: Text('Subscribe Now'),
                                                ),
                                              ),
                                            );
                                          },
                                        ),
                                      ],
                                    ),
                                  ),
                                ],
                              );
                            }
                          },
                        ),
                      ],
                    ),
                  ),
                ],
              ),
            ),
          ),
        ],
      ),
    );
  }
}
