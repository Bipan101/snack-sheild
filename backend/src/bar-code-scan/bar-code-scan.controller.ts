import {
  Controller,
  Get,
  Post,
  Body,
  Param,
  Delete,
  Patch,
} from '@nestjs/common';
import { BarCodeScanService } from './bar-code-scan.service';

@Controller('barcode')
export class BarCodeScanController {
  constructor(private readonly barcodeService: BarCodeScanService) {}

  @Post()
  async create(@Body() barcode: string) {
    const barcodeData = await this.barcodeService.getData(barcode);
    return { data: barcodeData, message: 'Bar Code Data fetched successfully' };
  }
}
