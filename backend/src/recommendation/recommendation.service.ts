import { Injectable } from '@nestjs/common';
import { FoodRecommendationDto } from './dto/recommendation.dto';
import { firstValueFrom } from 'rxjs';
import { HttpService } from '@nestjs/axios';

@Injectable()
export class FoodRecommendationService {
  constructor(private readonly httpService: HttpService) {}

  async recommendation(recommendation: FoodRecommendationDto) {
    const { nutritionalValues, ingredients } = recommendation;
    const res = this.getRecommendations(nutritionalValues, ingredients);
    return res;
  }

  async getRecommendations(
    nutritionInput: number[],
    ingredients: string[],
    params?: { n_neighbors?: number; return_distance?: boolean },
  ): Promise<any> {
    const requestBody = {
      nutrition_input: nutritionInput,
      ingredients: ingredients,
      params: params,
    };

    const url = 'http://localhost:8080/predict/';

    try {
      const response = await firstValueFrom(
        this.httpService.post(url, requestBody),
      );
      return response.data;
    } catch (error) {
      console.error('Error calling the Python API:', error);
      throw new Error('Failed to fetch recommendations');
    }
  }
}
