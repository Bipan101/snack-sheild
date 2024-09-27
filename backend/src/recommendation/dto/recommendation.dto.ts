import { IsArray } from 'class-validator';

export class FoodRecommendationDto {
  @IsArray()
  nutritionalValues: string[];

  @IsArray()
  ingredients: string[];
}
