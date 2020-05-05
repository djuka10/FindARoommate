package rs.ac.uns.ftn.findaroommate.utils.login;

import rs.ac.uns.ftn.findaroommate.dto.login.LoggedInUser;
import rs.ac.uns.ftn.findaroommate.dto.login.Result;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    private String username = "userdemo";
    private String password = "userdemo";

    public Result<LoggedInUser> login(String username, String password) {

        try {
            // TODO: handle loggedInUser authentication
            /*if(!username.equals(this.username) || !password.equals(this.password)){
                throw new Exception();
            }*/

            LoggedInUser fakeUser =
                    new LoggedInUser(
                            java.util.UUID.randomUUID().toString(),
                            "User Demo");
            return new Result.Success<>(fakeUser);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}
