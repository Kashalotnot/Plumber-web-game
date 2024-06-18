package sk.tuke.gamestudio.service;

public interface UserService {
    void registerUser(String login, String password);
    boolean checkUser(String login, String password);
}
