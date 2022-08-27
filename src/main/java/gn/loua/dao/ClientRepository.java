package gn.loua.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import gn.loua.entities.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {

}
