import { Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { User } from './user/entities/user.entity';
import { UserDetails } from './user-detail/entities/user-detail.entity';
import { UserModule } from './user/user.module';
import { UserDetailsModule } from './user-detail/user-detail.module';
import { FoodRecommendationModule } from './recommendation/recommendation.module';
import { AllergenDetectionModule } from './allergen-detection/allergen-detection.module';
import { BarCodeScanModule } from './bar-code-scan/bar-code-scan.module';

@Module({
  imports: [
    TypeOrmModule.forRoot({
      type: 'postgres',
      host: 'localhost',
      port: 5432,
      username: 'postgres',
      password: 'postgresql',
      database: 'snack-sheild',
      // entities: [__dirname + '/**/*.entity{.ts,.js}'],
      entities: [User, UserDetails],
      synchronize: true,
    }),
    UserModule,
    UserDetailsModule,
    FoodRecommendationModule,
    AllergenDetectionModule,
    BarCodeScanModule,
  ],
  controllers: [AppController],
  providers: [AppService],
})
export class AppModule {}
