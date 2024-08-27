import { Invoice } from "./invoice.model";
import { Product } from "./product.model";

export class OrderItem{
  id!: number;
  product!: Product;
  quantity: number = 1;
  invoice!: Invoice;

  constructor(product: Product) {
    this.product = product;
  }

  get price(): number {
    return this.product.price * this.quantity;
  }
}
