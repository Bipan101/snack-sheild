import { Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { UserDetails } from './entities/user-detail.entity';
import { UserDetailsService } from './user-detail.service';
import { UserDetailsController } from './user-detail.controller';

@Module({
  imports: [TypeOrmModule.forFeature([UserDetails])],
  providers: [UserDetailsService],
  controllers: [UserDetailsController],
  exports: [UserDetailsService],
})
export class UserDetailsModule {}
