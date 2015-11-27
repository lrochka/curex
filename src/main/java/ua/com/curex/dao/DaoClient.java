package ua.com.curex.dao;
import org.springframework.data.jpa.repository.JpaRepository;

import ua.com.curex.domain.Client;

public interface DaoClient extends JpaRepository<Client, Integer> {
}
 
