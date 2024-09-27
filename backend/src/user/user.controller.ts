import {
  Controller,
  Get,
  Post,
  Body,
  Param,
  Delete,
  Patch,
} from '@nestjs/common';
import { UserService } from './user.service';
import { User } from './entities/user.entity';
import { CreateUserDto, UpdateUserDto } from './dto/user.dto';

@Controller('users')
export class UserController {
  constructor(private readonly userService: UserService) {}

  @Get()
  async findAll(): Promise<{ data?: User[]; message?: string }> {
    const users = await this.userService.findAll();
    return { data: users, message: 'Users Listed Sussessfully' };
  }

  @Get(':id')
  async findOne(
    @Param('id') id: string,
  ): Promise<{ data?: User; message?: string }> {
    const user = await this.userService.findOne(id);
    return { data: user, message: 'User Fetched Sussessfully' };
  }

  @Post()
  async create(
    @Body() user: CreateUserDto,
  ): Promise<{ data?: User; message: string }> {
    const newUser = await this.userService.create(user);
    return { data: newUser, message: 'user created Successfully' };
  }

  @Patch(':id')
  async update(
    @Param('id') id: string,
    @Body() updateUserDto: UpdateUserDto,
  ): Promise<{ data?: User; message: string }> {
    const user = await this.userService.update(id, updateUserDto);
    return { data: user, message: 'User Updated Sussessfully' };
  }

  @Delete(':id')
  remove(@Param('id') id: string): Promise<void> {
    return this.userService.remove(id);
  }
}
