import java.util.List;

public class Lab4 {
    public static void main(String[] args) {
        // Example list of objects (can be any type)
        List<String> dataList = List.of("Item 1", "Item 2", "Item 3", "Item 4", "Item 5");

        // Create a Page object with pageSize = 2, pageNumber = 1
        Page<String> page = new Page<>(dataList.subList(0, 2), 2, 1, dataList.size());

        // Print the current page
        System.out.println("Current Page:");
        System.out.println(page);

        // Navigate to the next page and print
        if (page.hasNextPage()) {
            page = page.nextPage();
            System.out.println("Next Page:");
            System.out.println(page);
        }

        // Navigate back to the previous page and print
        if (page.hasPreviousPage()) {
            page = page.previousPage();
            System.out.println("Previous Page:");
            System.out.println(page);
        }
    }
}

class Page<T>{
    private List<T> data;
    private int pageSize;
    private int pageNumber;
    private int totalItems;

    public Page(List<T> data, int pageSize, int pageNumber, int totalItems) {
        this.data = data;
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
        this.totalItems = totalItems;
    }

    public List<T> getData() {
        return data;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public int getTotalPages() {
        return (int) Math.ceil((double) totalItems / pageSize);
    }

    public boolean hasNextPage() {
        return pageNumber < getTotalPages();
    }

    public boolean hasPreviousPage() {
        return pageNumber > 1;
    }

    // Method to navigate to the next page
    public Page<T> nextPage() {
        if (!hasNextPage()) {
            throw new IllegalStateException("No next page available.");
        }
        return new Page<>(data, pageSize, pageNumber + 1, totalItems);
    }

    // Method to navigate to the previous page
    public Page<T> previousPage() {
        if (!hasPreviousPage()) {
            throw new IllegalStateException("No previous page available.");
        }
        return new Page<>(data, pageSize, pageNumber - 1, totalItems);
    }

    @Override
    public String toString() {
        return "Page{" +
                "pageNumber=" + pageNumber +
                ", pageSize=" + pageSize +
                ", totalItems=" + totalItems +
                ", data=" + data +
                '}';
    }

}