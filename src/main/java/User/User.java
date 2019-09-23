package User;

public class User {

    private final int id;
    private final String name;
    private int balance;


    public User(int id, String name){
        this.id = id;
        this.name = name;
    }

    public User(int id, String name, int balance){
        this.id = id;
        this.name = name;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int amount) {
        this.balance = balance + amount;
    }


    public void transfer(User user, int amount) throws Exception{
        if (!user.hasSufficientBalance(amount)) {
            throw new Exception("Balance not sufficient");
        }

        user.setBalance(-amount);
        this.setBalance(amount);
    }

    public boolean hasSufficientBalance(int transferAmount){
        return (balance - transferAmount) >= 0;
    }

}
