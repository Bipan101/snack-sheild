import {
  Controller,
  Get,
  Post,
  Body,
  Param,
  Delete,
  Patch,
} from '@nestjs/common';
import { AllergenDetectionService } from './allergen-detection.service';
import { AllergenDetectionDto } from './dto/allergen-detection.dto';

@Controller('allergen')
export class AllergenDetectionController {
  constructor(
    private readonly allergenDetectionService: AllergenDetectionService,
  ) {}

  @Post()
  async create(@Body() allergenDetectionData: AllergenDetectionDto) {
    const detectedAllergen = await this.allergenDetectionService.detectAllergen(
      allergenDetectionData,
    );
    return { data: detectedAllergen, message: 'user created Successfully' };
  }
}
