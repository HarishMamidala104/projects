import 'package:flutter/material.dart';
import 'package:wellness_ui/api_services.dart';
import 'package:wellness_ui/app_colors.dart';

class SubscriptionPricing extends StatefulWidget {
  const SubscriptionPricing({Key? key}) : super(key: key);

  @override
  State<SubscriptionPricing> createState() => _SubscriptionPricingState();
}

class _SubscriptionPricingState extends State<SubscriptionPricing> {
  List<Map<String, dynamic>> apiResponse = [];
  List<bool> isHovered = [];

  @override
  void initState() {
    super.initState();
    fetchDataFromAPI();
  }

  Future<void> fetchDataFromAPI() async {
    var apiService = ApiService();
    final response =
        await apiService.get('/subscription/viewallsubscriptions');

    if (response.statusCode == 200) {
      setState(() {
        apiResponse = List<Map<String, dynamic>>.from(response.body);
        apiResponse.sort((a, b) => a['price'].compareTo(b['price']));

        // Initialize the hovered state for each card to false
        isHovered = List<bool>.filled(apiResponse.length, false);
      });
    } else {
      print('API request error: ${response.statusCode}');
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SizedBox(
        child: Padding(
          padding: const EdgeInsets.only(left: 50, right: 50),
          child: SingleChildScrollView(
            child: Column(
              children: [
                const SizedBox(
                  height: 50,
                ),
                const Align(
                  alignment: Alignment.centerLeft,
                  child: Text(
                    'PRICING',
                    style: TextStyle(
                      color: Colors.black,
                      fontSize: 40,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                ),
                const SizedBox(height: 20),
                SingleChildScrollView(
                  scrollDirection: Axis.horizontal,
                  child: Row(
                    children: List.generate(
                      apiResponse.length,
                      (index) {
                        var cardData = apiResponse[index];
                        return MouseRegion(
                          cursor: SystemMouseCursors.click,
                          onEnter: (_) {
                            setState(() {
                              isHovered[index] = true;
                            });
                          },
                          onExit: (_) {
                            setState(() {
                              isHovered[index] = false;
                            });
                          },
                          child: TweenAnimationBuilder<double>(
                            duration: const Duration(milliseconds: 200),
                            tween: Tween<double>(
                              begin: isHovered[index] ? 1.0 : 1.1,
                              end: isHovered[index] ? 1.1 : 1.0,
                            ),
                            builder: (context, value, child) {
                              return Transform.scale(
                                scale: value,
                                child: GestureDetector(
                                  onTap: () {
                                    // Handle the card tap event
                                  },
                                  child: Card(
                                    color: isHovered[index]
                                        ? Colors.orange
                                        : AppColors.lightBlue,
                                    margin: const EdgeInsets.all(40),
                                    child: Padding(
                                      padding: const EdgeInsets.all(12.0),
                                      child: SizedBox(
                                        width: 150 * value,
                                        height: 150 * value,
                                        child: Center(
                                          child: Column(
                                            children: [
                                              Padding(
                                                padding:
                                                    const EdgeInsets.all(8.0),
                                                child: Text(
                                                  cardData['planType'],
                                                  style: TextStyle(
                                                    color: isHovered[index]
                                                        ? Colors.white
                                                        : AppColors.textColor,
                                                    fontSize: 25 * value,
                                                    fontWeight: FontWeight.w600,
                                                  ),
                                                ),
                                              ),
                                              const SizedBox(height: 10),
                                              Text(
                                                'Price: ${cardData['price']}',
                                                style: TextStyle(
                                                  fontSize: 16 * value,
                                                  fontWeight: FontWeight.normal,
                                                  color: isHovered[index]
                                                      ? Colors.white
                                                      : AppColors.textColor,
                                                ),
                                              ),
                                              const SizedBox(height: 20),
                                              ElevatedButton(
                                                onPressed: () {},
                                                style: ButtonStyle(
                                                  backgroundColor:
                                                      MaterialStateProperty.all(
                                                    isHovered[index]
                                                        ? Colors.orange
                                                        : Colors.red,
                                                  ),
                                                ),
                                                child:
                                                    const Text('Subscribe Now'),
                                              ),
                                            ],
                                          ),
                                        ),
                                      ),
                                    ),
                                  ),
                                ),
                              );
                            },
                          ),
                        );
                      },
                    ),
                  ),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}




  

