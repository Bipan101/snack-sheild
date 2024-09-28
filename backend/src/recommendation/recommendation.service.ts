import { Injectable } from '@nestjs/common';
import { FoodRecommendationDto } from './dto/recommendation.dto';
import { firstValueFrom } from 'rxjs';
import { HttpService } from '@nestjs/axios';

@Injectable()
export class FoodRecommendationService {
  constructor(private readonly httpService: HttpService) {}

  async recommendation(recommendation: FoodRecommendationDto) {
    const { nutrientInput, ingredients } = recommendation;
    const {
      calories,
      fatContent,
      saturatedFatContent,
      cholesterolContent,
      sodiumContent,
      carbohydrateContent,
      fiberContent,
      sugarContent,
      proteinContent,
    } = nutrientInput;
    const nutritionalValues = [
      calories || 0,
      fatContent || 0,
      saturatedFatContent || 0,
      cholesterolContent || 0,
      sodiumContent || 0,
      carbohydrateContent || 0,
      fiberContent || 0,
      sugarContent || 0,
      proteinContent || 0,
    ];

    const res = await this.getRecommendations(nutritionalValues, ingredients);
    return res;
  }

  async getRecommendations(
    nutritionInput: number[],
    ingredients: string[],
  ): Promise<any> {
    const requestBody = {
      nutrition_input: nutritionInput,
      ingredients: ingredients,
      params: { n_neighbors: 5, return_distance: false },
    };

    // const req = {
    //   nutrition_input: [
    //     100.0, 200.0, 150.0, 120.0, 30.0, 0.0, 60.0, 10.0, 90.0,
    //   ],
    //   ingredients: ['okra', 'tomato', 'garlic'],
    //   params: {
    //     n_neighbors: 5,
    //     return_distance: false,
    //   },
    // };
    const url = 'http://localhost:8080/predict';
    try {
      const response = await firstValueFrom(
        this.httpService.post(url, requestBody, {
          headers: {
            'Content-Type': 'application/json', // Ensure correct content type
          },
        }),
      );

      if (!response) {
        throw new Error('Failed to fetch recommendations');
      }
      return response.data;
    } catch (error) {
      throw new Error('Failed to fetch recommendations');
    }
  }
}
