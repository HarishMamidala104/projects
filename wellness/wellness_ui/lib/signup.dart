import 'package:flutter/material.dart';
import 'package:wellness_ui/api_services.dart';
import 'package:wellness_ui/app_colors.dart';
import 'package:wellness_ui/app_icons.dart';
import 'package:wellness_ui/app_images.dart';
import 'package:wellness_ui/app_styles.dart';
import 'package:responsive_builder/responsive_builder.dart';
import 'package:wellness_ui/user.dart';

import 'OtpScreenLatest.dart';

class SignUp extends StatefulWidget {
  const SignUp({super.key});

  @override
  State<SignUp> createState() => _SignUpState();
}

class _SignUpState extends State<SignUp> {
  final TextEditingController nameController = TextEditingController();
  final TextEditingController emailController = TextEditingController();
  final TextEditingController mobileController = TextEditingController();
  final TextEditingController cityController = TextEditingController();
  String? _emailError;
  String? _nameError;
  String? _mobileError;
  String? _cityError;
  @override
  void dispose() {
    nameController.dispose();
    emailController.dispose();
    super.dispose();
  }

  Widget buildErrorInputField({required String? errorName}) {
    return Align(
      alignment: Alignment.centerLeft,
      child: SizedBox(
        // height: height * 0.03,
        child: Padding(
          padding: const EdgeInsets.only(left: 20.0, top: 4.0),
          child: Text(
            errorName!,
            style: const TextStyle(
              color: Colors.red,
              fontSize: 12.0,
            ),
          ),
        ),
      ),
    );
  }

  Widget buildInputField({
    required String labelText,
    required String suffixIcon,
    required TextEditingController controller,
  }) {
    return Container(
      height: 48.0,
      constraints: const BoxConstraints(maxWidth: 301),
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

  Widget build(BuildContext context) {
    double height = MediaQuery.of(context).size.height;
    double width = MediaQuery.of(context).size.width;
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
          SingleChildScrollView(
            scrollDirection: Axis.vertical,
            child: Padding(
              padding: const EdgeInsets.all(40),
              child: Container(
                height: height * 0.9,
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
                            'Sign-up',
                            style: TextStyle(
                              fontSize: 48,
                              fontWeight: FontWeight.w600,
                              color: AppColors.textColor,
                            ),
                          ),
                          const SizedBox(height: 60),
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
                                          const SizedBox(height: 30),
                                          buildInputField(
                                            labelText: 'Name',
                                            suffixIcon: AppIcons.emailIcon,
                                            controller: nameController,
                                          ),
                                          // if (_nameError != null)
                                          //   buildErrorInputField(
                                          //       errorName: _nameError),
                                          Align(
                                            alignment: Alignment.centerLeft,
                                            child: SizedBox(
                                              height: 10,
                                              child: Padding(
                                                padding: const EdgeInsets.only(
                                                    left: 20.0, top: 4.0),
                                                child: Text(
                                                  _nameError!,
                                                  style: const TextStyle(
                                                    color: Colors.red,
                                                    fontSize: 12.0,
                                                  ),
                                                ),
                                              ),
                                            ),
                                          ),
                                          const SizedBox(height: 16.0),
                                          buildInputField(
                                            labelText: 'Email',
                                            suffixIcon: AppIcons.emailIcon,
                                            controller: emailController,
                                          ),
                                          Align(
                                            alignment: Alignment.centerLeft,
                                            child: SizedBox(
                                              height: 100,
                                              child: Padding(
                                                padding: const EdgeInsets.only(
                                                    left: 20.0, top: 4.0),
                                                child: Text(
                                                  _nameError!,
                                                  style: const TextStyle(
                                                    color: Colors.red,
                                                    fontSize: 12.0,
                                                  ),
                                                ),
                                              ),
                                            ),
                                          ),
                                        ],
                                      ),
                                      Column(
                                        mainAxisAlignment:
                                            MainAxisAlignment.center,
                                        children: [
                                          const SizedBox(height: 30),
                                          buildInputField(
                                            labelText: 'Mobile',
                                            suffixIcon: AppIcons.emailIcon,
                                            controller: mobileController,
                                          ),
                                          const SizedBox(height: 16.0),
                                          buildInputField(
                                            labelText: 'city',
                                            suffixIcon: AppIcons.emailIcon,
                                            controller: cityController,
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
                                          SizedBox(height: 80),
                                          Column(
                                            mainAxisAlignment:
                                                MainAxisAlignment.start,
                                            children: [
                                              buildInputField(
                                                labelText: 'Name',
                                                suffixIcon: AppIcons.emailIcon,
                                                controller: nameController,
                                              ),
                                              Container(
                                                child: _nameError != null
                                                    ? buildErrorInputField(
                                                        errorName: _nameError)
                                                    : null,
                                              ),
                                            ],
                                          ),
                                          SizedBox(width: 200),
                                          Column(
                                            children: [
                                              buildInputField(
                                                labelText: 'Email',
                                                suffixIcon: AppIcons.lockIcon,
                                                controller: emailController,
                                              ),
                                              Container(
                                                child: _emailError != null
                                                    ? buildErrorInputField(
                                                        errorName: _emailError)
                                                    : null,
                                              ),
                                            ],
                                          )
                                        ],
                                      ),
                                    ),
                                    Center(
                                      child: Row(
                                        mainAxisAlignment:
                                            MainAxisAlignment.center,
                                        children: [
                                          SizedBox(height: 80),
                                          Column(
                                            children: [
                                              buildInputField(
                                                labelText: 'Mobile',
                                                suffixIcon: AppIcons.emailIcon,
                                                controller: mobileController,
                                              ),
                                              Container(
                                                child: _mobileError != null
                                                    ? buildErrorInputField(
                                                        errorName: _mobileError)
                                                    : null,
                                              ),
                                            ],
                                          ),
                                          SizedBox(width: 200),
                                          Column(
                                            children: [
                                              Container(
                                                child: buildInputField(
                                                  labelText: 'City',
                                                  suffixIcon: AppIcons.lockIcon,
                                                  controller: cityController,
                                                ),
                                              ),
                                              Container(
                                                child: _cityError != null
                                                    ? buildErrorInputField(
                                                        errorName: _cityError)
                                                    : null,
                                              ),
                                            ],
                                          )
                                        ],
                                      ),
                                    ),
                                    SizedBox(
                                      height: 80,
                                    ),
                                    Center(
                                      child: Row(
                                        mainAxisAlignment:
                                            MainAxisAlignment.center,
                                        children: [
                                          SizedBox(height: 60),
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
                                                                .greyButton),
                                                  ),
                                                  onPressed: () {},
                                                  child: Padding(
                                                    padding:
                                                        const EdgeInsets.all(
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
                                                  onPressed: () {
                                                    validateRegister();
                                                  },
                                                  child: Padding(
                                                    padding:
                                                        const EdgeInsets.all(
                                                            12.0),
                                                    child: Text('Sign-up'),
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
          ),
        ],
      ),
    );
  }

  void validateRegister() async {
    setState(() {
      _emailError = _validateEmail(emailController.text);
      _nameError = _validateName(nameController.text);
      _mobileError = _validateMobileNumber(mobileController.text);
      _cityError = _validateCity(cityController.text);
    });
    print(_emailError);
    if (_emailError == null &&
        _nameError == null &&
        _mobileError == null &&
        _cityError == null) {
      var user = User(
          email: emailController.text,
          name: nameController.text,
          mobile: mobileController.text,
          city: cityController.text);
      var apiService = ApiService();
      final data = {
        'email': user.email,
        'name': user.name,
        "mobile": user.mobile,
        'city': user.city
      };
      var response = await apiService.post('/user/register', data);
      // String message = response.body['message'];
      print(response.statusCode.toString());
      print(response.statusCode);
      if (response.statusCode == 200) {
        // showSuccessMessage(message);
        Navigator.push(
          context,
          MaterialPageRoute(builder: (context) => const OtpScreenLatest()),
        );
        print(response.body);
      } else {
        print(response.body);
      }
    }
    print("register");
  }

  String? _validateEmail(String? value) {
    if (value == null || value.isEmpty) {
      return 'Email is mandatory';
    } else if (!RegExp(r'^[\w-]+(\.[\w-]+)*@([\w-]+\.)+[a-zA-Z]{2,7}$')
        .hasMatch(value)) {
      return 'Invalid email format';
    }
    return null;
  }

  String? _validateName(String? value) {
    if (value == null || value.isEmpty) {
      return 'Name is mandatory';
    }
    return null;
  }

  String? _validateMobileNumber(String? value) {
    if (value == null || value.isEmpty) {
      return 'Mobile number is mandatory';
    } else if (!RegExp(r'^[0-9]{10}$').hasMatch(value)) {
      return 'Invalid mobile number format';
    }
    return null;
  }

  String? _validateCity(String? value) {
    if (value == null || value.isEmpty) {
      return 'City is mandatory';
    }
    return null;
  }
}
