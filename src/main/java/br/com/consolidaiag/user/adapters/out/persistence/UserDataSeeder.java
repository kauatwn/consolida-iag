package br.com.consolidaiag.user.adapters.out.persistence;

import br.com.consolidaiag.user.application.ports.out.UserRepositoryPort;
import br.com.consolidaiag.user.domain.User;
import br.com.consolidaiag.user.domain.UserRole;
import br.com.consolidaiag.weeklyrequest.domain.ServiceCode;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserDataSeeder implements ApplicationRunner {

    private final UserRepositoryPort userRepository;

    public UserDataSeeder(UserRepositoryPort userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        seedUserIfAbsent("gestora@gmail.com", "Gestora Compras", UserRole.BUYER, null);
        seedUserIfAbsent("lider.cei@gmail.com", "Líder Cozinha CEI", UserRole.KITCHEN_LEADER, ServiceCode.CEI);
        seedUserIfAbsent("lider.cca@gmail.com", "Líder Cozinha CCA", UserRole.KITCHEN_LEADER, ServiceCode.CCA);
        seedUserIfAbsent("lider.cj@gmail.com", "Líder Cozinha CJ", UserRole.KITCHEN_LEADER, ServiceCode.CJ);
    }

    private void seedUserIfAbsent(String email, String name, UserRole role, ServiceCode serviceCode) {
        if (userRepository.findByEmail(email).isEmpty()) {
            User user = User.create(null, email, name, role, serviceCode);
            userRepository.save(user);
        }
    }
}
