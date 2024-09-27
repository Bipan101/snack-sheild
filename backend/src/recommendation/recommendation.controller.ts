import {
  Controller,
  Get,
  Post,
  Body,
  Param,
  Delete,
  Patch,
} from '@nestjs/common';
import { FoodRecommendationService } from './recommendation.service';
import { FoodRecommendationDto } from './dto/recommendation.dto';

@Controller('recommendation')
export class FoodRecommendationController {
  constructor(
    private readonly recommendationService: FoodRecommendationService,
  ) {}

  @Post()
  async create(@Body() recommendation: FoodRecommendationDto) {
    const newUser =
      await this.recommendationService.recommendation(recommendation);
    return { data: newUser, message: 'user created Successfully' };
  }
}
