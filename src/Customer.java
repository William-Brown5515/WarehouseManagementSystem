public class Customer {

    private String name;
    private String customerId;
    private String email;
    private String phone;

    public Customer(String name, String customerId, String email, String phone) {
        this.name = name;
        this.customerId = customerId;
        this.email = email;
        this.phone = phone;
    }

    public String getName() { return name; }

    public String getCustomerId() { return customerId; }

    public String getEmail() { return email; }

    public String getPhone() { return phone; }

    public void setName(String name) { this.name = name; }

    public void setEmail(String email) { this.email = email; }

    public void setPhone(String phone) { this.phone = phone; }

    @Override
    public String toString() {
        return "Customer ID: " + customerId + ", Name: " + name + ", Email: " + email + ", Phone: " + phone;
    }
}
