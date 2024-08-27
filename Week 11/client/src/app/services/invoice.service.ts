import { Injectable } from "@angular/core";
import { Invoice } from "../models/invoice.model";
import { OrderItem } from "../models/orderitem.model";
import { Product } from "../models/product.model";
import { UserService } from "./user.service";

@Injectable({
  providedIn: 'root',
})
export class InvoiceService{
  invoice: Invoice = new Invoice();

  constructor(private userService: UserService) {}

  getInvoice(){
    return this.invoice;
  }

  addToInvoice(product: Product, quantity: number =  1){
    console.log("masuk ke sini aman aja");
    const existOrderItem = this.invoice.items.find(
      (orderitem) => orderitem.product.id == product.id
    );

    if (existOrderItem){
      existOrderItem.quantity += quantity;
    }
    else{
      this.invoice.items.push(this.createOrderItem(product, quantity))
    }

    const usernameLoggedIn = localStorage.getItem("loggedUser");
    if (usernameLoggedIn) {
      this.userService.get(usernameLoggedIn).subscribe(user => {
        this.invoice.customer = user || undefined;
      });
    }
  }

  createOrderItem(product: Product, quantity: number){
    let orderItem = new OrderItem(product);
    orderItem.quantity = quantity;
    return orderItem;
  }
}
