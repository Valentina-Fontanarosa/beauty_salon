package com.beautysalon.demo.controller;

import com.beautysalon.demo.model.Disponibilita;
import com.beautysalon.demo.model.Professionista;
import com.beautysalon.demo.service.DisponibilitaService;
import com.beautysalon.demo.service.ProfessionistaService;
import com.beautysalon.demo.validation.DisponibilitaValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

import static com.beautysalon.demo.model.Disponibilita.DIR_ADMIN_PAGES_DISP;

@Controller
public class DisponibilitaController {

    @Autowired
    private DisponibilitaService disponibilitaService;

    @Autowired
    private DisponibilitaValidator disponibilitaValidator;

    @Autowired
    private ProfessionistaService professionistaService;


    /* METHODS ADMIN */

    @GetMapping("/admin/disponibilita")
    public String getAdminDisponibilitaProfessionista( Model model) {
        model.addAttribute("disponibilitaList", this.disponibilitaService.findAll());
        return DIR_ADMIN_PAGES_DISP + "adminDisponibilita";
    }

    // --- INSERIMENTO

    @GetMapping("/admin/disponibilita/add/{id}")
    public String addGetDisponibilita(@PathVariable("id") Long id, Model model) {
        model.addAttribute("idProfessionista", id);
        model.addAttribute("disponibilita", new Disponibilita());

        return DIR_ADMIN_PAGES_DISP + "formDisponibilita";
    }

    @PostMapping("/admin/disponibilita/add/{id}")
    public String addDisponibilita(@Valid @ModelAttribute("disponibilita") Disponibilita disponibilita, BindingResult bindingResult,
                                   @PathVariable("id") Long id, Model model) {

        Professionista professionista = this.professionistaService.findById(id);
        disponibilita.setProfessionista(professionista);
        disponibilita.setActive(true);
        this.disponibilitaValidator.validate(disponibilita, bindingResult);

        if(!bindingResult.hasErrors()) {
            this.professionistaService.addDisponibilita(professionista, disponibilita);
            return this.getAdminDisponibilitaProfessionista(model);
        }

        model.addAttribute("id", id);
        return DIR_ADMIN_PAGES_DISP + "formDisponibilita";
    }

    // --- ELIMINAZIONE

    @GetMapping("/admin/disponibilita/delete/{id}")
    public String deleteDisponibilita(@PathVariable("id") Long id, Model model) {
        Disponibilita disponibilita = this.disponibilitaService.findById(id);
        Professionista p = this.professionistaService.findById(disponibilita.getProfessionista().getId());

        p.getDisponibilita().remove(disponibilita);
        this.disponibilitaService.delete(disponibilita);
        this.professionistaService.save(p);

        return "redirect:/admin/disponibilita";
    }

    // --- MODIFICA

    @GetMapping("/admin/disponibilita/edit/{id}")
    public String editDisponibilita(@PathVariable("id") Long id, Model model) {
        model.addAttribute("disponibilita", this.disponibilitaService.findById(id));

        return DIR_ADMIN_PAGES_DISP + "modificaDisponibilita";
    }

    @PostMapping("/admin/disponibilita/edit/{id}")
    public String editDisponiblita(@Valid @ModelAttribute("disponibilita") Disponibilita disponibilita,
                                   BindingResult bindingResult,
                                   @PathVariable("id") Long id,
                                   Model model) {

        Disponibilita d = this.disponibilitaService.findById(id);
        disponibilita.setProfessionista(d.getProfessionista());
        this.disponibilitaValidator.validate(disponibilita, bindingResult);
        if(!bindingResult.hasErrors()) {

            this.disponibilitaService.update(d, disponibilita);

            return this.getAdminDisponibilitaProfessionista(model);
        }

        disponibilita.setId(id);
        return DIR_ADMIN_PAGES_DISP + "modificaDisponibilita";
    }
}
