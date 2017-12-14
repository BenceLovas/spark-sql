package dao;

import java.util.List;
import java.util.Map;

public interface UserDao {
    boolean insert(String userName, String userPassword);
    List<Map<String, String>> select(String userName) throws NullPointerException;
}
