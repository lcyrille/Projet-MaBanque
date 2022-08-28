package gn.loua.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import gn.loua.dao.OperationRepository;
import gn.loua.entities.Compte;
import gn.loua.entities.Operation;
import gn.loua.metier.IBanqueMetier;

@Controller
public class BanqueController {
	@Autowired
	private IBanqueMetier banqueMetier;
	
	@RequestMapping("/operations")
	public String index() {
		
		return "comptes";
	}
	
	@RequestMapping("/consulterCompte")
	public String consulter(Model model, String codeCompte,
			@RequestParam(name="page", defaultValue="0")int p,
			@RequestParam(name="size", defaultValue="2")int s) {
		model.addAttribute("codeCompte", codeCompte);
		try {
			Compte cp=banqueMetier.consulterCompte(codeCompte);
			Page<Operation> pageOperations=banqueMetier.listOperation(codeCompte,p,s);
			model.addAttribute("listeOperations",pageOperations.getContent());
			model.addAttribute("compte",cp);
			int[] pages=new int[pageOperations.getTotalPages()];
			model.addAttribute("pages",pages);
			model.addAttribute("size",s);
			model.addAttribute("pageCourante",p);
		} catch (Exception e){
			model.addAttribute("exception",e);
		}
		
		return "comptes";
	}

}
