package musicmanager.application;

import musicmanager.application.model.AdminUser;
import musicmanager.application.model.DefaultUser;
import musicmanager.application.model.TYPE_USER;
import musicmanager.application.persistence.UserPersistence;
import musicmanager.application.security.LoginManager;
import musicmanager.application.service.Logger;

import java.util.Scanner;

public class Application {

    public static void main(String[] args) {
        System.out.println("Iniciando aplicação...");
        while (true){
            System.out.println("Digite 1 para fazer Login e 2 para criar um novo usuário");
            Scanner scanner = new Scanner(System.in);

            String usersDataDir = "src/datas/usersdata.bin";

            String login;
            String password;
            String name;
            TYPE_USER userType;

            int option = scanner.nextInt();

            if (option == 2) {
                System.out.println("Digite seu login");
                login = scanner.next();
                while (UserPersistence.userAlreadyExist(usersDataDir, login)) {
                    System.out.println("Login já existente, digite outro");
                    login = scanner.next();
                }
                System.out.println("Digite seu nome");
                name = scanner.next();
                System.out.println("Digite sua senha");
                password = scanner.next();
                System.out.println("Digite 1 para usuário comum e 2 para administrador");
                int userTypeOption = scanner.nextInt();
                if (userTypeOption == 1) {
                    userType = TYPE_USER.DEFAULT_USER;
                } else {
                    userType = TYPE_USER.ADMIN_USER;
                }
                boolean ok = Logger.createUser(name, login, password, userType);
                if (ok) {
                    System.out.println("Usuário criado com sucesso");
                } else {
                    System.out.println("Erro ao criar usuário");
                }
            }
            System.out.println("Digite seu login");
            login = scanner.next();
            System.out.println("Digite sua senha");
            password = scanner.next();
            userType = UserPersistence.getUserType(usersDataDir, login);

            if (userType == TYPE_USER.DEFAULT_USER) {
                DefaultUser user = (DefaultUser) Logger.userCreator(login, password);
                System.out.println("Bem vindo " + user.getName());
                break;
            }
            if (userType == TYPE_USER.ADMIN_USER) {
                AdminUser user = (AdminUser) Logger.userCreator(login, password);
                System.out.println("Bem vindo administrador" + user.getName());
                break;
            }

        }

    }
}
