package dao;

import model.User;

import java.util.List;
import java.util.Map;

public interface UserDAO {
    boolean insert(User user);
    List<User> select(User user) throws NullPointerException;
    // other CRUD (create, read, update, delete) operations
}
