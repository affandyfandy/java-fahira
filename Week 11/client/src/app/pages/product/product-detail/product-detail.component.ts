import { Component, Input, OnInit } from '@angular/core';
import { Product } from '../../../models/product.model';
import { ProductService } from '../../../services/product.service';
import { Router, RouterLink } from '@angular/router';
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
  @Input() viewMode: boolean = false;
  @Input() currentProduct: Product = {
    id: 0,
    name: '',
    isactive: false,
    quantity: 0,
    price: 0
  };

  constructor(
    private productService: ProductService,
    private router: Router
  ){}

  ngOnInit(): void {
    this.getProduct(this.currentProduct.id);
  }

  getProduct(id: number): void{
    console.log("view mode get product " + this.viewMode);
    this.productService.get(id).subscribe({
      next: (data) => {
        this.currentProduct = data;
        console.log(data);
      },
      error: (e) => console.error(e)
    });
  }

  editProduct(): void {
    // Implement the edit logic here
  }

  setProductStatus(): void {
    const newStatus = !this.currentProduct.isactive;
    this.productService.updateStatus(this.currentProduct.id, newStatus).subscribe({
      next: (updatedProduct) => {
        this.currentProduct = updatedProduct;
        console.log(this.currentProduct.isactive);
      },
      error: (e) => console.error(e)
    });
  }

  goBack(): void {
    this.viewMode = false;
    console.log("view mode " + this.viewMode);
  }

}
