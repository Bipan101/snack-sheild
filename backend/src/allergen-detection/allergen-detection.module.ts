import { HttpModule } from '@nestjs/axios';
import { Module } from '@nestjs/common';
import { AllergenDetectionService } from './allergen-detection.service';
import { AllergenDetectionController } from './allergen-detection.controller';
import { UserDetailsModule } from 'src/user-detail/user-detail.module';

@Module({
  imports: [HttpModule, UserDetailsModule],
  providers: [AllergenDetectionService],
  controllers: [AllergenDetectionController],
  exports: [AllergenDetectionService],
})
export class AllergenDetectionModule {}
