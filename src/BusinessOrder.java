public class BusinessOrder extends BaseOrder {
    private Supplier supplier;

    public BusinessOrder(Supplier supplier) {
        this.supplier = supplier;
    }

    public Supplier getSupplier() { return supplier; }
}
