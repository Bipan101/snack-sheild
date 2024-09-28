import { IsArray } from 'class-validator';
import { NutritionInput } from './nutrition-input.dto';

export class FoodRecommendationDto {
  nutrientInput?: NutritionInput;
  @IsArray()
  ingredients?: string[];
}
