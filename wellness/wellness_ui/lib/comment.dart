                  // RichText(
                  //       text: TextSpan(
                  //         children: [
                  //           TextSpan(
                  //               text: 'Let’s',
                  //               style: ralewayStyle.copyWith(
                  //                 fontSize: 25.0,
                  //                 color: AppColors.blueDarkColor,
                  //                 fontWeight: FontWeight.normal,
                  //               )),
                  //           TextSpan(
                  //             text: ' Sign In 👇',
                  //             style: ralewayStyle.copyWith(
                  //               fontWeight: FontWeight.w800,
                  //               color: AppColors.blueDarkColor,
                  //               fontSize: 25.0,
                  //             ),
                  //           ),
                  //         ],
                  //       ),
                  //     ),


                  //subscription

                  // import 'package:flutter/material.dart';
                  // import 'package:wellness_ui/app_colors.dart';
                  // import 'package:wellness_ui/app_icons.dart';
                  // import 'package:wellness_ui/app_images.dart';
                  // import 'package:wellness_ui/app_styles.dart';
                  // import 'package:responsive_builder/responsive_builder.dart';

                  // class SubscriptionPlan extends StatefulWidget {
                  //   const SubscriptionPlan({super.key});

                  //   @override
                  //   State<SubscriptionPlan> createState() => _SubscriptionPlanState();
                  // }

                  // class _SubscriptionPlanState extends State<SubscriptionPlan> {
                  //   @override
                  //   Widget build(BuildContext context) {
                  //     double height = MediaQuery.of(context).size.height;
                  //     double width = MediaQuery.of(context).size.width;
                  //     return Scaffold(
                  //       body: Stack(
                  //         children: [
                  //           // Background Image
                  //           Image.asset(
                  //             AppImages.banner2, // Replace with your image path
                  //             fit: BoxFit.cover,
                  //             width: double.infinity,
                  //             height: double.infinity,
                  //           ),
                  //           // Container with Input Boxes
                  //           Padding(
                  //             padding: const EdgeInsets.all(40.0),
                  //             child: Container(
                  //               color: Colors.white,
                  //               padding: const EdgeInsets.all(16.0),
                  //               child: Column(
                  //                 crossAxisAlignment: CrossAxisAlignment.start,
                  //                 children: [
                  //                   const Row(children: [
                  //                     Align(
                  //                         alignment: Alignment.centerLeft,
                  //                         child: SizedBox(
                  //                           child: Image(
                  //                             image: AssetImage(AppImages.logo),
                  //                             width: 144,
                  //                             height: 99,
                  //                           ),
                  //                         ))
                  //                   ]),
                  //                   Center(
                  //                     child: Column(
                  //                       children: [
                  //                         const Text(
                  //                           'Subscribe',
                  //                           style: TextStyle(
                  //                             fontSize: 48,
                  //                             fontWeight: FontWeight.w600,
                  //                             color: AppColors.textColor,
                  //                           ),
                  //                         ),
                  //                         const SizedBox(height: 10),
                  //                         Container(
                  //                           constraints: const BoxConstraints(maxWidth: 401),
                  //                           decoration: BoxDecoration(
                  //                             borderRadius: BorderRadius.circular(3.0),
                  //                             color: AppColors.planTypeColor,
                  //                           ),
                  //                           child: Padding(
                  //                             padding: const EdgeInsets.all(9.0),
                  //                             child: Row(
                  //                                 mainAxisAlignment:
                  //                                     MainAxisAlignment.spaceBetween,
                  //                                 children: [
                  //                                   const Text(
                  //                                     "Basic Plan -",
                  //                                     textAlign: TextAlign.left,
                  //                                     style: TextStyle(
                  //                                         fontSize: 28,
                  //                                         color: AppColors.whiteColor),
                  //                                   ),
                  //                                   RichText(
                  //                                       text: TextSpan(children: [
                  //                                     TextSpan(
                  //                                         text: " ₹ ",
                  //                                         style: ralewayStyle.copyWith(
                  //                                           fontSize: 25.0,
                  //                                           color: AppColors.whiteColor,
                  //                                           fontWeight: FontWeight.w200,
                  //                                         )),
                  //                                     TextSpan(
                  //                                         text: "100",
                  //                                         style: ralewayStyle.copyWith(
                  //                                           fontSize: 37.0,
                  //                                           color: AppColors.whiteColor,
                  //                                           fontWeight: FontWeight.w600,
                  //                                         )),
                  //                                     TextSpan(
                  //                                         text: "/PPPM",
                  //                                         style: ralewayStyle.copyWith(
                  //                                           fontSize: 20.0,
                  //                                           color: AppColors.whiteColor,
                  //                                           fontWeight: FontWeight.w200,
                  //                                         ))
                  //                                   ]))
                  //                                 ]),
                  //                           ),
                  //                         ),
                  //                         ResponsiveBuilder(
                  //                           builder: (context, sizingInformation) {
                  //                             if (sizingInformation.deviceScreenType ==
                  //                                 DeviceScreenType.mobile) {
                  //                               // Mobile view layout
                  //                               return Center(
                  //                                 child: Column(
                  //                                   mainAxisAlignment: MainAxisAlignment.center,
                  //                                   children: [
                  //                                     const SizedBox(height: 10),
                  //                                     Container(
                  //                                       height: 50.0,
                  //                                       constraints:
                  //                                           const BoxConstraints(maxWidth: 401),
                  //                                       decoration: BoxDecoration(
                  //                                         borderRadius:
                  //                                             BorderRadius.circular(3.0),
                  //                                         color: AppColors.whiteColor,
                  //                                       ),
                  //                                       child: TextFormField(
                  //                                         // controller: emailController,
                  //                                         style: ralewayStyle.copyWith(
                  //                                           fontWeight: FontWeight.w400,
                  //                                           color: AppColors.blueDarkColor,
                  //                                           fontSize: 16.0,
                  //                                         ),
                  //                                         decoration: InputDecoration(
                  //                                           labelText: 'Name',
                  //                                           enabledBorder:
                  //                                               const OutlineInputBorder(
                  //                                                   borderSide: BorderSide(
                  //                                                       color: AppColors
                  //                                                           .lightblueColor)),
                  //                                           focusedBorder:
                  //                                               const OutlineInputBorder(
                  //                                                   borderSide: BorderSide(
                  //                                                       color: AppColors
                  //                                                           .lightblueColor)),
                  //                                           suffixIcon: IconButton(
                  //                                             onPressed: () {},
                  //                                             icon:
                  //                                                 Image.asset(AppIcons.emailIcon),
                  //                                           ),
                  //                                           // suffixText: '@',
                  //                                           contentPadding: const EdgeInsets.only(
                  //                                               top: 16.0, left: 20.0),
                  //                                           // hintText: 'Enter Email',
                  //                                           hintStyle: ralewayStyle.copyWith(
                  //                                             fontWeight: FontWeight.w400,
                  //                                             color: AppColors.blueDarkColor
                  //                                                 .withOpacity(0.8),
                  //                                             fontSize: 14.0,
                  //                                           ),
                  //                                         ),
                  //                                       ),
                  //                                     ),

                  //                                     const SizedBox(
                  //                                         height: 16.0), // Spacing between fields
                  //                                     Container(
                  //                                       height: 50.0,
                  //                                       constraints:
                  //                                           const BoxConstraints(maxWidth: 401),
                  //                                       decoration: BoxDecoration(
                  //                                         borderRadius:
                  //                                             BorderRadius.circular(3.0),
                  //                                         color: AppColors.whiteColor,
                  //                                       ),
                  //                                       child: TextFormField(
                  //                                         // controller: emailController,
                  //                                         style: ralewayStyle.copyWith(
                  //                                           fontWeight: FontWeight.w400,
                  //                                           color: AppColors.blueDarkColor,
                  //                                           fontSize: 16.0,
                  //                                         ),
                  //                                         decoration: InputDecoration(
                  //                                           labelText: 'Name',
                  //                                           enabledBorder:
                  //                                               const OutlineInputBorder(
                  //                                                   borderSide: BorderSide(
                  //                                                       color: AppColors
                  //                                                           .lightblueColor)),
                  //                                           focusedBorder:
                  //                                               const OutlineInputBorder(
                  //                                                   borderSide: BorderSide(
                  //                                                       color: AppColors
                  //                                                           .lightblueColor)),
                  //                                           suffixIcon: IconButton(
                  //                                             onPressed: () {},
                  //                                             icon:
                  //                                                 Image.asset(AppIcons.emailIcon),
                  //                                           ),
                  //                                           // suffixText: '@',
                  //                                           contentPadding: const EdgeInsets.only(
                  //                                               top: 16.0, left: 20.0),
                  //                                           // hintText: 'Enter Email',
                  //                                           hintStyle: ralewayStyle.copyWith(
                  //                                             fontWeight: FontWeight.w400,
                  //                                             color: AppColors.blueDarkColor
                  //                                                 .withOpacity(0.8),
                  //                                             fontSize: 14.0,
                  //                                           ),
                  //                                         ),
                  //                                       ),
                  //                                     ),
                  //                                   ],
                  //                                 ),
                  //                               );
                  //                             } else {
                  //                               // Desktop view layout
                  //                               return Center(
                  //                                 child: Row(
                  //                                   mainAxisAlignment: MainAxisAlignment.center,
                  //                                   children: [
                  //                                     const SizedBox(
                  //                                       height: 100,
                  //                                     ),
                  //                                     Container(
                  //                                       height: 50.0,
                  //                                       constraints:
                  //                                           const BoxConstraints(maxWidth: 401),
                  //                                       decoration: BoxDecoration(
                  //                                         borderRadius:
                  //                                             BorderRadius.circular(3.0),
                  //                                         color: AppColors.whiteColor,
                  //                                       ),
                  //                                       child: TextFormField(
                  //                                         // controller: emailController,
                  //                                         style: ralewayStyle.copyWith(
                  //                                           fontWeight: FontWeight.w400,
                  //                                           color: AppColors.blueDarkColor,
                  //                                           fontSize: 16.0,
                  //                                         ),
                  //                                         decoration: InputDecoration(
                  //                                           labelText: 'Name',
                  //                                           enabledBorder:
                  //                                               const OutlineInputBorder(
                  //                                                   borderSide: BorderSide(
                  //                                                       color: AppColors
                  //                                                           .lightblueColor)),
                  //                                           focusedBorder:
                  //                                               const OutlineInputBorder(
                  //                                                   borderSide: BorderSide(
                  //                                                       color: AppColors
                  //                                                           .lightblueColor)),
                  //                                           suffixIcon: IconButton(
                  //                                             onPressed: () {},
                  //                                             icon:
                  //                                                 Image.asset(AppIcons.emailIcon),
                  //                                           ),
                  //                                           // suffixText: '@',
                  //                                           contentPadding: const EdgeInsets.only(
                  //                                               top: 16.0, left: 20.0),
                  //                                           // hintText: 'Enter Email',
                  //                                           hintStyle: ralewayStyle.copyWith(
                  //                                             fontWeight: FontWeight.w400,
                  //                                             color: AppColors.blueDarkColor
                  //                                                 .withOpacity(0.8),
                  //                                             fontSize: 14.0,
                  //                                           ),
                  //                                         ),
                  //                                       ),
                  //                                     ),
                  //                                     SizedBox(
                  //                                         width: 100.0), // Spacing between fields
                  //                                     Container(
                  //                                       height: 50.0,
                  //                                       constraints:
                  //                                           const BoxConstraints(maxWidth: 401),
                  //                                       decoration: BoxDecoration(
                  //                                         borderRadius:
                  //                                             BorderRadius.circular(3.0),
                  //                                         color: AppColors.whiteColor,
                  //                                       ),
                  //                                       child: TextFormField(
                  //                                         // controller: emailController,
                  //                                         style: ralewayStyle.copyWith(
                  //                                           fontWeight: FontWeight.w400,
                  //                                           color: AppColors.blueDarkColor,
                  //                                           fontSize: 16.0,
                  //                                         ),
                  //                                         decoration: InputDecoration(
                  //                                           labelText: 'Name',
                  //                                           enabledBorder:
                  //                                               const OutlineInputBorder(
                  //                                                   borderSide: BorderSide(
                  //                                                       color: AppColors
                  //                                                           .lightblueColor)),
                  //                                           focusedBorder:
                  //                                               const OutlineInputBorder(
                  //                                                   borderSide: BorderSide(
                  //                                                       color: AppColors
                  //                                                           .lightblueColor)),
                  //                                           suffixIcon: IconButton(
                  //                                             onPressed: () {},
                  //                                             icon:
                  //                                                 Image.asset(AppIcons.emailIcon),
                  //                                           ),
                  //                                           // suffixText: '@',
                  //                                           contentPadding: const EdgeInsets.only(
                  //                                               top: 16.0, left: 20.0),
                  //                                           // hintText: 'Enter Email',
                  //                                           hintStyle: ralewayStyle.copyWith(
                  //                                             fontWeight: FontWeight.w400,
                  //                                             color: AppColors.blueDarkColor
                  //                                                 .withOpacity(0.8),
                  //                                             fontSize: 14.0,
                  //                                           ),
                  //                                         ),
                  //                                       ),
                  //                                     ),
                  //                                   ],
                  //                                 ),
                  //                               );
                  //                             }
                  //                           },
                  //                         ),
                  //                       ],
                  //                     ),
                  //                   ),
                  //                 ],
                  //               ),
                  //             ),
                  //           ),
                  //         ],
                  //       ),
                  //     );
                  //   }
                  // }





                       // Text('Hey, Enter your details to get sign in \nto your account.',
                      //   style: ralewayStyle.copyWith(
                      //     fontSize: 12.0,
                      //     fontWeight: FontWeight.w400,
                      //     color: AppColors.textColor,
                      //   ),
                      // ),