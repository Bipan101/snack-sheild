import { Injectable } from '@nestjs/common';
import { FoodRecommendationDto } from './dto/recommendation.dto';
import { firstValueFrom } from 'rxjs';
import { HttpService } from '@nestjs/axios';

@Injectable()
export class FoodRecommendationService {
  constructor(private readonly httpService: HttpService) {}

  async recommendation(recommendation: FoodRecommendationDto) {
    const { nutritionalValues, ingredients } = recommendation;
    const res = await this.getRecommendations(nutritionalValues, ingredients);
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
    const req = {
      nutrition_input: [
        100.0, 200.0, 150.0, 120.0, 30.0, 0.0, 60.0, 10.0, 90.0,
      ],
      ingredients: ['chicken', 'tomato', 'garlic'],
      params: {
        n_neighbors: 5,
        return_distance: false,
      },
    };
    const url = 'http://localhost:8080/predict';
    // console.log(req);
    try {
      const response = await firstValueFrom(
        this.httpService.post(url, req, {
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
      console.error('Error calling the Python API:', error);
      throw new Error('Failed to fetch recommendations');
    }
  }
}
