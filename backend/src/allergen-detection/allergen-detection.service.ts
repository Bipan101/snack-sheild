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

    for (const allergen of allergens) {
      if (allergensData[allergen]) {
        const keywords = allergensData[allergen];
        for (const ingredient of ingredients) {
          if (
            keywords.some((keyword) =>
              ingredient.toLowerCase().includes(keyword),
            )
          ) {
            detectedAllergens.add(allergen);
            break;
          }
        }
      }
    }

    // Convert Set to Array and return
    return Array.from(detectedAllergens);
  }

  async detectAllergenByImage(filePath: string) {
    try {
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

      return response.data;
    } catch (error) {
      console.error('Error detecting allergen:', error);
    }
  }
}
