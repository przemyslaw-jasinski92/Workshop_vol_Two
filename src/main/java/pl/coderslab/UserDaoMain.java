package pl.coderslab;

import pl.coderslab.entity.User;
import pl.coderslab.entity.UserDao;

public class UserDaoMain {
    public static void main(String[] args) {
        UserDao userDao = new UserDao();

        //add new user to database
//        User user = new User();
//        user.setUserName("userator");
//        user.setPassword("usermastronpass");
//        user.setEmail("usrator22@gmail.com");
//        userDao.create(user);

        //create new object from database
//        if(UserDao.checkId(5)){
//            User user1 = userDao.read(5);
//            System.out.println(user1);
//        }

        //enter with wrong value of user id
//        if(UserDao.checkId(25)){
//            User user2 = userDao.read(25);
//            System.out.println(user2);
//        }


        //update object from database - without new password
//        user1.setUserName("jerry62");
//        user1.setEmail("jerry62@gmail.com");
//        userDao.updateWithNoPass(user1);

        //update object from database - with new password
//        if(UserDao.checkId(4)) {
//            User user3 = userDao.read(4);
//            user3.setUserName("analnyzator");
//            user3.setEmail("analnyzator@yahoo.com");
//            user3.setPassword("zatwardzenieboli");
//            userDao.updateWithPass(user3);
//        }

        //read all users in database and add to users array
        User[] users = userDao.findAll();
        userDao.showUsers(users);

    }
}