import {
  Controller,
  Post,
  Body,
  UseInterceptors,
  HttpException,
  HttpStatus,
  UploadedFile,
} from '@nestjs/common';
import { AllergenDetectionService } from './allergen-detection.service';
import { AllergenDetectionDto } from './dto/allergen-detection.dto';
import { FileInterceptor } from '@nestjs/platform-express';
import { diskStorage } from 'multer';
import { extname } from 'path';

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
  @UseInterceptors(
    FileInterceptor('file', {
      storage: diskStorage({
        destination: './uploads', // Directory to save uploaded images
        filename: (req, file, cb) => {
          // Generate a unique filename
          const randomName = Array(32)
            .fill(null)
            .map(() => Math.round(Math.random() * 16).toString(16))
            .join('');
          cb(null, `${randomName}${extname(file.originalname)}`);
        },
      }),
      limits: {
        fileSize: 10 * 1024 * 1024,
      },
      fileFilter: (req, file, cb) => {
        // Filter to allow only images
        if (!file.mimetype.match(/\/(jpg|jpeg|png|gif)$/)) {
          cb(
            new HttpException(
              'Only image files are allowed!',
              HttpStatus.BAD_REQUEST,
            ),
            false,
          );
        } else {
          cb(null, true);
        }
      },
    }),
  )
  async detectAllergenByImage(@UploadedFile() file: Express.Multer.File) {
    try {
      if (!file) {
        throw new HttpException('No file uploaded', HttpStatus.BAD_REQUEST);
      }
      const detectedAllergen =
        await this.allergenDetectionService.detectAllergenByImage(file?.path);

      return {
        data: detectedAllergen,
        message: 'Allergen detection successful',
      };
    } catch (error) {
      throw new HttpException(
        `Error detecting allergens: ${error.message}`,
        HttpStatus.INTERNAL_SERVER_ERROR,
      );
    }
  }
}
