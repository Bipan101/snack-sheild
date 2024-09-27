import {
  Controller,
  Get,
  Post,
  Body,
  Param,
  Delete,
  Patch,
} from '@nestjs/common';
import { UserDetailsService } from './user-detail.service';
import { UserDetails } from './entities/user-detail.entity';
import { CreateUserDetailsDTO } from './dto/user-detail.dto';

@Controller('user-details')
export class UserDetailsController {
  constructor(private readonly userDetailsService: UserDetailsService) {}

  @Get(':userId')
  async findOne(
    @Param('userId') userId: string,
  ): Promise<{ data?: UserDetails; message?: string }> {
    const userDetail = await this.userDetailsService.findOne(userId);
    return { data: userDetail, message: 'User Fetched Sussessfully' };
  }

  @Post()
  async create(
    @Body() user: CreateUserDetailsDTO,
  ): Promise<{ data?: UserDetails; message: string }> {
    const newUser = await this.userDetailsService.create(user);
    return { data: newUser, message: 'user created Successfully' };
  }

  @Patch(':userId')
  async update(
    @Param('userId') userId: string,
    @Body() updateUserDetailsDto: CreateUserDetailsDTO,
  ): Promise<{ data?: UserDetails; message: string }> {
    const user = await this.userDetailsService.update(
      userId,
      updateUserDetailsDto,
    );
    return { data: user, message: 'User Updated Sussessfully' };
  }

  @Delete(':id')
  remove(@Param('id') id: string): Promise<void> {
    return this.userDetailsService.remove(id);
  }
}
