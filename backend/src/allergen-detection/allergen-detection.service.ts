import { Injectable } from '@nestjs/common';
import { AllergenDetectionDto } from './dto/allergen-detection.dto';
import { UserDetailsService } from '../user-detail/user-detail.service';

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
  constructor(private readonly userDetailsService: UserDetailsService) {}

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

  async detectAllergenByImage() {}
}
