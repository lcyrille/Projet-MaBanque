package gn.loua;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import gn.loua.dao.ClientRepository;
import gn.loua.dao.CompteRepository;
import gn.loua.dao.OperationRepository;
import gn.loua.entities.Client;
import gn.loua.entities.Compte;
import gn.loua.entities.CompteCourant;
import gn.loua.entities.CompteEpargne;
import gn.loua.entities.Retrait;
import gn.loua.entities.Versement;
import gn.loua.metier.IBanqueMetier;

@SpringBootApplication
public class MaBanqueApplication implements CommandLineRunner {
	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private CompteRepository compteRepository;
	@Autowired
	private OperationRepository operationRepository;
	@Autowired
	private IBanqueMetier banqueMetier;

	public static void main(String[] args) {
		SpringApplication.run(MaBanqueApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Client c1=clientRepository.save(new Client("loua","loua@gmail.com"));
		Client c2=clientRepository.save(new Client("christine","christine@gmail.com"));
		
		Compte cp1=compteRepository.save(new CompteCourant("c1", new Date() , 90000, c1,6000));
		Compte cp2=compteRepository.save(new CompteEpargne("c2", new Date() , 6000, c2,5.5));
		
		operationRepository.save(new Versement(new Date(),9000,cp1));
		operationRepository.save(new Versement(new Date(),2300,cp1));
		operationRepository.save(new Retrait(new Date(),1500,cp1)); 
		
		operationRepository.save(new Versement(new Date(),90500,cp2));
		operationRepository.save(new Versement(new Date(),2600,cp2));
		operationRepository.save(new Retrait(new Date(),2300,cp2));
		
		banqueMetier.verser("c1", 22222);
	}

}
