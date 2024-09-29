import { Injectable } from '@nestjs/common';
import { AllergenDetectionDto } from './dto/allergen-detection.dto';
import { UserDetailsService } from '../user-detail/user-detail.service';
import { HttpService } from '@nestjs/axios';
import { lastValueFrom } from 'rxjs';
import * as fs from 'fs';
import * as sharp from 'sharp';

const allergensData = {
  dairy: ['milk', 'cheese', 'yogurt', 'butter', 'cream'],
  nuts: ['peanuts', 'almonds', 'walnuts', 'hazelnuts', 'cashews'],
  gluten: ['wheat', 'barley', 'rye'],
  shellfish: ['shrimp', 'crab', 'lobster'],
  fish: ['salmon', 'tuna', 'cod'],
  soy: ['tofu', 'soy sauce'],
  egg: ['egg whites', 'egg yolks'],
  sesame: ['sesame seeds'],
};

@Injectable()
export class AllergenDetectionService {
  constructor(
    private readonly httpService: HttpService,
    private readonly userDetailsService: UserDetailsService,
  ) {}

  async detectAllergenByLabel(allergenData: AllergenDetectionDto) {
    const { userId, ingredients } = allergenData;
    const UserDetails = await this.userDetailsService.findOne(userId);
    const { allergens } = UserDetails;

    const detectedAllergens = new Set<string>();

    // Convert the long ingredients string to lowercase for easier matching
    const ingredientsLower = ingredients?.toLowerCase();
    let isSafeForUser = true;
    for (const allergen of allergens) {
      if (allergensData[allergen]) {
        const keywords = allergensData[allergen];
        for (const keyword of keywords) {
          if (ingredientsLower.includes(keyword.toLowerCase())) {
            detectedAllergens.add(allergen);
            isSafeForUser = false;
            break;
          }
        }
      }
    }

    // Convert Set to Array and return
    const allergen = Array.from(detectedAllergens);
    return { allergen, isSafeForUser };
  }

  async detectAllergenByImage(filePath: string, userId?: string) {
    try {
      const userId = '2316407b-b321-4e12-96be-42dbdbd30f59';
      console.log(userId);
      let userAllergens = [];
      let isSafeForUser = true;
      if (userId) {
        const UserDetails = await this.userDetailsService.findOne(userId);
        userAllergens = UserDetails?.allergens;
      }
      const imageBuffer = fs.readFileSync(filePath);

      // Resize the image and compress it using sharp
      const compressedImage = await sharp(imageBuffer)
        .resize({ width: 800 }) // Resize to 800px width (or any appropriate size)
        .jpeg({ quality: 70 }) // Compress to JPEG with 70% quality
        .toBuffer();
      const url =
        'https://detect.roboflow.com/food-allergy-detection-project/39';
      const apiKey = 'HNSn6lNSiNEKlXXQTgSD';

      // Make the POST request
      const response = await lastValueFrom(
        this.httpService.post(url, compressedImage.toString('base64'), {
          params: { api_key: apiKey },
          headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        }),
      );

      const predictions = response?.data?.predictions || [];

      // Check if any allergen matches the prediction's class
      predictions?.forEach((prediction) => {
        if (userAllergens?.includes(prediction?.class)) {
          isSafeForUser = false;
        }
      });
      return { ...response?.data, isSafeForUser };
    } catch (error) {
      console.error('Error detecting allergen:', error);
    }
  }
}
