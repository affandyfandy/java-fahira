import { OrderItem } from "./orderitem.model";
import { User } from "./user.model";

export class Invoice {
  id!: number;
  customer?: User;
  items: OrderItem[] = [];
  invoicedate?: Date;

  get totalamount(): number {
    let totalAmount = 0;
    this.items.forEach((item) => (totalAmount += item.price));
    return totalAmount;
  }
}
