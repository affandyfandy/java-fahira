import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ProductDetailComponent } from '../product-detail/product-detail.component';
import { Product } from '../../../models/product.model';
import { ProductService } from '../../../services/product.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [
    CommonModule,
    ProductDetailComponent
  ],
  templateUrl: './product-list.component.html',
  styleUrl: './product-list.component.scss'
})
export class ProductListComponent implements OnInit{
  products? : Product[];
  currentProduct: Product = {
    id: 0,
    name: '',
    quantity: 0,
    price: 0,
    isactive: false
  };
  viewMode: boolean = false;

  constructor(private productService: ProductService, private router: Router){}

  ngOnInit(): void {
    if (typeof window !== 'undefined' && localStorage.getItem("loggedUser") !== null) {
      return this.retrieveProduct();
    }
    this.router.navigate(['/login']);
  }

  retrieveProduct(): void {
    this.productService.getAll().subscribe({
      next: (data) => {
        this.products = data;
        console.log("logged user " + localStorage.getItem("loggedUser"))
      },
      error: (e) => console.log(e)
    });
  }

  // setActiveProduct(product: Product, index: number): void{
  //   this.currentProduct = product;
  //   this.currentIndex = index;
  //   console.log("current product " + this.currentProduct.id);
  // }

  showProductDetail(product: Product, index: number): void {
    this.currentProduct = product;
    this.currentProduct.id = index;
    this.viewMode = true;
    console.log("view mode detail " + this.viewMode);
  }

}
