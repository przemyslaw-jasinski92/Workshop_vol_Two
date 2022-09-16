package pl.coderslab;

import pl.coderslab.entity.User;
import pl.coderslab.entity.UserDao;

public class UserDaoMain {
    public static void main(String[] args) {
        UserDao userDao = new UserDao();

        //add new user to database
//        User user = new User();
//        user.setUserName("johnny62");
//        user.setPassword("zlotazyla");
//        user.setEmail("johnny62@gmail.com");
//        userDao.create(user);

        //create new object from database
        User user1 = userDao.read(1);
        System.out.println(user1);
    }
}