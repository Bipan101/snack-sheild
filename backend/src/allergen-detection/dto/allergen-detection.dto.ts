import { IsArray, IsString } from 'class-validator';

export class AllergenDetectionDto {
  @IsArray()
  ingredients: string[];

  @IsString()
  userId: string;
}
