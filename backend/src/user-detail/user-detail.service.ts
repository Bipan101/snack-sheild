import { Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { UserDetails } from './entities/user-detail.entity';
import { CreateUserDetailsDTO } from './dto/user-detail.dto';
import { User } from 'src/user/entities/user.entity';

@Injectable()
export class UserDetailsService {
  constructor(
    @InjectRepository(UserDetails)
    private userDetailsRepository: Repository<UserDetails>,
  ) {}

  async findOne(userId: string): Promise<UserDetails> {
    return await this.userDetailsRepository
      .createQueryBuilder('userDetails')
      .leftJoinAndSelect('userDetails.user', 'user')
      .where('user.id = :id', { id: userId })
      .getOne();
  }

  async create(userDetail: CreateUserDetailsDTO): Promise<UserDetails> {
    const { userId, ...restData } = userDetail;
    const userDetailInstance = new UserDetails();
    userDetailInstance.user = new User();
    userDetailInstance.user.id = userId;
    Object.assign(userDetailInstance, restData);
    return await this.userDetailsRepository.save(userDetailInstance);
  }

  async remove(id: string): Promise<void> {
    await this.userDetailsRepository.delete(id);
  }

  async update(
    userId: string,
    updateData: CreateUserDetailsDTO,
  ): Promise<UserDetails> {
    const userDetail = await this.findOne(userId);
    if (!userDetail) {
      throw new Error('UserDetail not found');
    }
    Object.assign(userDetail, updateData);
    await this.userDetailsRepository.save(userDetail);
    return await this.findOne(userId);
  }
}
