The values inside each table in the database after mock-up data are as follows:

![image](/Week%2003/Lecture%2006/Assignment3/image/show-all.png)

Next, we need to create a view to show the list of products customers bought. The result when we call the view is displayed below:

![image](/Week%2003/Lecture%2006/Assignment3/image/product-list.png)

To calculate the revenue for each cashier, we have created a function for this purpose:

![image](/Week%2003/Lecture%2006/Assignment3/image/revenue.png)

Subsequently, we created revenue_report table.

![image](/Week%2003/Lecture%2006/Assignment3/image/table.png)

Next, we created stored procedures to calculate and store the output in the revenue_report table:
```sql
CALL calculate_daily_revenue('2024-06-28');
CALL calculate_monthly_revenue('2024-06-01');
CALL calculate_yearly_revenue(2024);
```
The values in the revenue_report are as follows:

![image](/Week%2003/Lecture%2006/Assignment3/image/revenue-report.png)

