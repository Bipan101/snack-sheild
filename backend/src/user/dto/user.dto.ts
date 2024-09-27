import { PartialType } from '@nestjs/mapped-types';

export class CreateUserDto {
  name: string;
  email: string;
  isActive: boolean;
  password: string;
}

export class UpdateUserDto extends PartialType(CreateUserDto) {
  id: string;
}
