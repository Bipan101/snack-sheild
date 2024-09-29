import { Module } from '@nestjs/common';
import { HttpModule } from '@nestjs/axios';
import { BarCodeScanService } from './bar-code-scan.service';
import { BarCodeScanController } from './bar-code-scan.controller';
import { UserDetailsModule } from 'src/user-detail/user-detail.module';

@Module({
  imports: [HttpModule, UserDetailsModule],
  providers: [BarCodeScanService],
  controllers: [BarCodeScanController],
  exports: [BarCodeScanService],
})
export class BarCodeScanModule {}
