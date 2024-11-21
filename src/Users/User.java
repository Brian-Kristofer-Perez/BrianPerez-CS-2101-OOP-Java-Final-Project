package Users;
import java.util.Scanner;

public abstract class User {
    protected String name;
    protected String email;
    protected String contactNumber;

    protected User(String name){
        this.name = name;
    }

    protected void printContactDetails() {
        System.out.println("=====================================");
        System.out.println("      Current Contact Details        ");
        System.out.println("-------------------------------------");
        System.out.println("Email:       " + this.getEmail()); // we'll use getters, for their conditional formatting when null :)
        System.out.println("Contact #:   " + this.getContactNumber());
        System.out.println();
        System.out.println("=====================================");
    }


    public String getName(){
        return this.name;
    }

    public String getEmail(){

        if(this.email == null){
            return "No email listed.";
        }
        if(this.email.isBlank()){
            return "No email listed.";
        }
        return this.email;
    }

    public String getContactNumber(){

        if(this.contactNumber == null){
            return "No contact number listed.";
        }
        if(this.contactNumber.isBlank()){
            return "No contact number listed.";
        }
        return this.contactNumber;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setContactNumber(String contactNumber){
        this.contactNumber = contactNumber;
    }

    public abstract void login(Scanner input);

    protected abstract void editContactDetails(Scanner input);
}


