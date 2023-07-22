package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {

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

}

