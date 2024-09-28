# Problem Statement

The modern diet is increasingly dominated by processed and junk foods, leading to a rise in health issues such as obesity, diabetes, and heart disease. Consumers must often be made aware of these foods' harmful ingredients, allergens, and high acid levels. Even when nutritional information is provided, it is often difficult to interpret and understand, making it challenging for individuals to make informed, healthier food choices.

# Project Summary

As a junk food-obsessed generation, we often overlook the impact of our dietary choices, leading to rising health issues like obesity and heart disease. **SnackShield** is set to change that by empowering us to make healthier decisions. This innovative mobile app simplifies the task of understanding complex nutritional information. By scanning food barcodes or packaging, users receive instant, personalized insights into ingredients, sugar, fat levels, and potential allergens. SnackShield also assesses acid levels in foods and offers warnings for conditions like GERD. 
With AI-driven recommendations for healthier alternatives and tailored dietary guidance, SnackShield is poised to transform the way we shop, plan meals, and monitor our diets, making informed, healthier choices more accessible.
# Objectives / Project Goals

- **Develop a mobile app** that scans food packaging to provide detailed nutritional information, including warnings about high sugar or fat content, and alerts for allergens based on user profiles.

- **Create an AI-driven recommendation system** that suggests healthier alternatives and tailors dietary advice according to users' goals, such as reducing sugar intake or avoiding certain ingredients.

# How to Use This Project

1. Clone the repository:
   ```bash
   git clone https://github.com/siddhant7781/snack-sheild.git
   
   cd android-app
   
   - Open the project in Android Studio.
   - Wait for Gradle to sync.
   - Run the app on an emulator.

   ```bash

   cd ../
   
   cd backend
   npm install
   npm run start
   
   cd ../
   
   cd recommendation-system
   docker-compose up -d --build

# External API and References

### Recommendation System:
- GitHub Repository: https://github.com/zakaria-narjis/Diet-Recommendation-System
- Author: Narjis, Zakaria

### External Model:
- **RoboFlow for Allergen Detection**
  - URL: https://universe.roboflow.com/fluxblazesu2022batchc/food-allergy-detection-project/model/39
### Food Data:
- **Open Food Facts**
  - URL: https://openfoodfacts.github.io/openfoodfacts-server/api/


