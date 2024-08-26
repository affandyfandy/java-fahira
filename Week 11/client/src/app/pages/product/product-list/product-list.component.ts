import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ProductDetailComponent } from '../product-detail/product-detail.component';
import { Product } from '../../../models/product.model';
import { ProductService } from '../../../services/product.service';
import { Router, RouterLink } from '@angular/router';
import { InvoiceService } from '../../../services/invoice.service';

@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [
    CommonModule,
    ProductDetailComponent,
    RouterLink
  ],
  templateUrl: './product-list.component.html',
  styleUrl: './product-list.component.scss'
})
export class ProductListComponent implements OnInit{
  products? : Product[];
  currentProduct?: Product;
  // viewMode: boolean = false;

  constructor(
    private productService: ProductService,
    private router: Router,
    private invoiceService: InvoiceService
  ){}

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

  checkout(product: Product){
    this.invoiceService.addToInvoice(product);
  }

}
