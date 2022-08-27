package gn.loua.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import gn.loua.entities.Compte;

public interface CompteRepository extends JpaRepository<Compte, String> {


}
