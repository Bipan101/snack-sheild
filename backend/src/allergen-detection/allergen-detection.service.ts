import { Injectable } from '@nestjs/common';
import { AllergenDetectionDto } from './dto/allergen-detection.dto';
import { UserDetailsService } from '../user-detail/user-detail.service';

@Injectable()
export class AllergenDetectionService {
  constructor(private readonly userDetailsService: UserDetailsService) {}

  async detectAllergen(allergenData: AllergenDetectionDto) {
    const { userId, ingredients } = allergenData;
    const UserDetails = await this.userDetailsService.findOne(userId);
    const { allergens } = UserDetails;

    // Check if any allergen is a substring of the ingredient
    const detectedAllergens = ingredients.filter((ingredient) =>
      allergens.some((allergen) =>
        ingredient.toLowerCase().includes(allergen.toLowerCase()),
      ),
    );
    return detectedAllergens;
  }
}
