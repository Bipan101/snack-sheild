import { IsArray } from 'class-validator';

export class FoodRecommendationDto {
  @IsArray()
  nutritionalValues: number[];

  @IsArray()
  ingredients: string[];
}
