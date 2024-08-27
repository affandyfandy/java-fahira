import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { Invoice } from '../../models/invoice.model';
import { InvoiceService } from '../../services/invoice.service';
import { Product } from '../../models/product.model';

@Component({
  selector: 'app-invoice',
  standalone: true,
  imports: [
    RouterLink,
    CommonModule
  ],
  templateUrl: './invoice.component.html',
  styleUrl: './invoice.component.scss'
})
export class InvoiceComponent implements OnInit {
  invoice!: Invoice;

  constructor(private invoiceService: InvoiceService) {}

  ngOnInit(): void {
    this.setInvoice();
  }

  setInvoice() {
    this.invoice = this.invoiceService.getInvoice();
  }
}
