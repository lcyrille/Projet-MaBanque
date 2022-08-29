package gn.loua.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
			@RequestParam(name="page", defaultValue="0")int page,
			@RequestParam(name="size", defaultValue="5")int size) {
		model.addAttribute("codeCompte", codeCompte);
		try {
			Compte cp=banqueMetier.consulterCompte(codeCompte);
			Page<Operation> pageOperations=banqueMetier.listOperation(codeCompte,page,size);
			model.addAttribute("listeOperations",pageOperations.getContent());
			model.addAttribute("compte",cp);
			int[] pages=new int[pageOperations.getTotalPages()];
			model.addAttribute("pages",pages);
		} catch (Exception e){
			model.addAttribute("exception",e);
		}
		
		return "comptes";
	}
	
	@RequestMapping(value="/saveOperation", method=RequestMethod.POST)
	public String saveOperation(Model model, String typeOperation, 
			String codeCompte, double montant, String codeCompte2) {
		try {
			if(typeOperation.equals("VERS")) {
				banqueMetier.verser(codeCompte, montant);
			}
			else if(typeOperation.equals("RET")) {
				banqueMetier.retirer(codeCompte, montant);
			}
			if(typeOperation.equals("VIR")) {
				banqueMetier.virement(codeCompte, codeCompte2, montant);
				}
			} catch (Exception e) {
				model.addAttribute("error",e);
				return "redirect:/consulterCompte?codeCompte="+codeCompte+"&error="+e.getMessage();
			}
		return "redirect:/consulterCompte?codeCompte="+codeCompte;
	}

}
