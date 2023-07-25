package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {

    AccountDAO accountDAO;

    public AccountService() {
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    /**
     * login a user
     * 
     * @param username
     * @param password
     * @return
     */
    public Account userLogin(String username, String password) {
        // TODO Auto-generated method stub
        Account account = AccountDAO.getAccountLogin(username); // accountDAO.getAccountByUsername(username);
        if (account != null && account.getPassword().equals(password)) {
            return account;
        }
        return null;
    }

    /**
     * register a user
     * 
     * @param username
     * @param password
     * @return
     */
    public Account userRegister(Account account) {
        // TODO Auto-generated method stub
        Account exAccount = AccountDAO.getAccountLogin(account.getUsername());
        if (exAccount != null) {
            return null; // Username already exists, registration fails.
        } else {
            if (account.getUsername() != null && !account.getUsername().isEmpty() &&
                    account.getPassword() != null && account.getPassword().length() >= 4) {
                return AccountDAO.registerUserCreateAccount(account);
            } else {
                return null; // Username or password does not meet the requirements, registration fails.
            }
        }
    }

}

