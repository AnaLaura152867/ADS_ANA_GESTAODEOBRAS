package br.com.obras.converter;

import br.com.obras.dao.ResponsavelDAO;
import br.com.obras.model.Responsavel;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

@FacesConverter(forClass = Responsavel.class)
public class ResponsavelConverter implements Converter<Responsavel> {

    @Override
    public Responsavel getAsObject(FacesContext ctx, UIComponent comp, String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            return null;
        }
        return new ResponsavelDAO().buscarPorId(Long.valueOf(valor));
    }

    @Override
    public String getAsString(FacesContext ctx, UIComponent comp, Responsavel r) {
        return (r == null || r.getId() == null) ? "" : String.valueOf(r.getId());
    }
}