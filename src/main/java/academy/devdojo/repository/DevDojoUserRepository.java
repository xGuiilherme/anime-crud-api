package academy.devdojo.repository;

import academy.devdojo.domain.DevDojoUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DevDojoUserRepository extends JpaRepository<DevDojoUser, Long> {

    DevDojoUser findByUsername(String username);
}
