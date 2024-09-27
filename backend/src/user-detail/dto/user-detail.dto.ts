import { PartialType } from '@nestjs/mapped-types';
import { IsString, IsNumber, IsOptional, IsArray, IsIn } from 'class-validator';

export class CreateUserDetailsDTO {
  @IsString()
  userId: string;

  @IsOptional()
  @IsArray()
  @IsString({ each: true })
  allergens?: string[];

  @IsNumber()
  age?: number;

  @IsOptional()
  @IsString()
  @IsIn(['male', 'female', 'other'])
  gender?: string;

  @IsOptional()
  @IsNumber()
  height?: number;

  @IsOptional()
  @IsNumber()
  weight?: number;

  @IsOptional()
  @IsString()
  dietPreference?: string;
}

export class UpdateUserDetailsDto extends PartialType(CreateUserDetailsDTO) {
  @IsOptional()
  userId: string;
}
