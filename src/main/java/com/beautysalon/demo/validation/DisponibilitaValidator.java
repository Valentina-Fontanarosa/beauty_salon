package com.beautysalon.demo.validation;

import com.beautysalon.demo.model.Disponibilita;
import com.beautysalon.demo.service.DisponibilitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class DisponibilitaValidator implements Validator {

    @Autowired
    private DisponibilitaService disponibilitaService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Disponibilita.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if(LocalDate.parse(((Disponibilita)target).getData()).isBefore(LocalDate.now()))
            errors.reject("date.disponibilita");

        if(LocalTime.parse(((Disponibilita)target).getOraFine()).isBefore(LocalTime.parse(((Disponibilita)target).getOraInizio())))
            errors.reject("hour.disponibilita");

        if(this.disponibilitaService.alreadyExists((Disponibilita) target))
            errors.reject("duplicate.disponibilita");
    }

}
