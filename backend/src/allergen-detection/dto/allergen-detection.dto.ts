import { IsArray, IsOptional, IsString } from 'class-validator';

export class AllergenDetectionDto {
  @IsArray()
  ingredients: string;

  @IsString()
  @IsOptional()
  userId?: string;
}
