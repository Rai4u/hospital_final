package ua.nure.shevchenko.hospital;


import ua.nure.shevchenko.hospital.database.ConnectionPool;
import ua.nure.shevchenko.hospital.database.TransactionManager;
import ua.nure.shevchenko.hospital.repository.DoctorRepository;
import ua.nure.shevchenko.hospital.repository.DoctorRepositoryImpl;
import ua.nure.shevchenko.hospital.repository.UserRepositoryImpl;
import ua.nure.shevchenko.hospital.service.UserService;
import ua.nure.shevchenko.hospital.service.UserServiceImpl;


public class Demo {

    public static void main(String[] args) {
        Context.put("transactionManager", new TransactionManager(ConnectionPool.setUpPool()));
        Context.put("userRepository", new UserRepositoryImpl());
        Context.put("doctorRepository", new DoctorRepositoryImpl());

        UserService service = new UserServiceImpl();

    }
}
