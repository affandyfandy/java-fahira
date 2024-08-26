import { Component, Input, OnInit } from '@angular/core';
import { Product } from '../../../models/product.model';
import { ProductService } from '../../../services/product.service';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-product-detail',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink
  ],
  templateUrl: './product-detail.component.html',
  styleUrl: './product-detail.component.scss'
})
export class ProductDetailComponent implements OnInit{
  productId: number | undefined;
  product: any;

  constructor(
    private route: ActivatedRoute,
    private productService: ProductService
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.productId = +params.get('id')!;
      this.loadProduct();
    });
  }

  loadProduct(): void {
    if (this.productId !== undefined) {
      this.productService.get(this.productId).subscribe(product => {
        this.product = product;
      });
    }
  }

  setStatus(isActive: boolean): void {
    if (this.product) {
      const updatedProduct = { ...this.product, isactive: !isActive };
      this.productService.update(updatedProduct.id, updatedProduct).subscribe(
        () => {
          this.product = updatedProduct;
        },
        error => {
          console.error('Error updating product status', error);
        }
      );
    }
  }
}
