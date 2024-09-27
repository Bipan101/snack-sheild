import { Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { User } from './entities/user.entity';
import { CreateUserDto, UpdateUserDto } from './dto/user.dto';

@Injectable()
export class UserService {
  constructor(
    @InjectRepository(User)
    private userRepository: Repository<User>,
  ) {}

  async findAll(): Promise<User[]> {
    return await this.userRepository.find();
  }

  async findOne(id: string): Promise<User> {
    return await this.userRepository
      .createQueryBuilder('user')
      .leftJoinAndSelect('user.userDetails', 'userDetails')
      .where('user.id = :id', { id: id })
      .getOne();
  }

  async create(user: CreateUserDto): Promise<User> {
    return await this.userRepository.save(user);
  }

  async remove(id: string): Promise<void> {
    await this.userRepository.delete(id);
  }

  async update(id: string, updateData: UpdateUserDto): Promise<User> {
    const user = await this.findOne(id);
    if (!user) {
      throw new Error('User not found');
    }
    Object.assign(user, updateData);
    await await this.userRepository.save(user);
    return await this.findOne(id);
  }
}
