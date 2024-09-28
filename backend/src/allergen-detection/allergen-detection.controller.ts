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

  @Post('/label')
  async detectAllergenByLabel(
    @Body() allergenDetectionData: AllergenDetectionDto,
  ) {
    const detectedAllergen =
      await this.allergenDetectionService.detectAllergenByLabel(
        allergenDetectionData,
      );
    return { data: detectedAllergen, message: 'user created Successfully' };
  }

  @Post('/image')
  async detectAllergenByImage() {
    const detectedAllergen =
      await this.allergenDetectionService.detectAllergenByImage();
    return { data: detectedAllergen, message: 'user created Successfully' };
  }
}
