import { Injectable } from '@nestjs/common';
import { firstValueFrom } from 'rxjs';
import { HttpService } from '@nestjs/axios';

@Injectable()
export class BarCodeScanService {
  constructor(private readonly httpService: HttpService) {}

  async getData(barcode: string) {
    const res = await this.getBarCodeData(barcode);
    return res;
  }

  async getBarCodeData(barcode: string): Promise<any> {
    const url = `https://world.openfoodfacts.net/api/v2/product/${barcode}`;
    try {
      const response = await firstValueFrom(this.httpService.get(url));
      if (!response) {
        throw new Error('Failed to Bar code data');
      }
      return response.data;
    } catch (error) {
      throw new Error('Failed to Bar code data');
    }
  }
}
