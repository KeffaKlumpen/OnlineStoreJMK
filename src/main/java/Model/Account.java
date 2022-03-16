/*
  Author: Joel Eriksson Sinclair
  Id: ai7892
  Study program: Sys 21h
*/

package Model;

public class Account {
    private int id;
    private String email;
    private boolean isAdmin;

    public Account(){

    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public boolean isAdmin() {
        return isAdmin;
    }
    public void setAdminStatus(boolean adminStatus) {
        this.isAdmin = adminStatus;
    }
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Account{");
        sb.append("id=").append(id);
        sb.append(", email='").append(email).append('\'');
        sb.append(", adminStatus=").append(isAdmin);
        sb.append('}');
        return sb.toString();
    }
}
