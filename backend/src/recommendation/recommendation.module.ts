import { Module } from '@nestjs/common';
import { HttpModule } from '@nestjs/axios';
import { FoodRecommendationController } from './recommendation.controller';
import { FoodRecommendationService } from './recommendation.service';

@Module({
  imports: [HttpModule],
  providers: [FoodRecommendationService],
  controllers: [FoodRecommendationController],
  exports: [FoodRecommendationService],
})
export class FoodRecommendationModule {}
