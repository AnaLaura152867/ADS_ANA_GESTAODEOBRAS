package br.com.obras.converter;

import br.com.obras.dao.EtapaDAO;
import br.com.obras.model.Etapa;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

@FacesConverter(forClass = Etapa.class)
public class EtapaConverter implements Converter<Etapa> {

    @Override
    public Etapa getAsObject(FacesContext ctx, UIComponent comp, String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            return null;
        }
        return new EtapaDAO().buscarPorId(Long.valueOf(valor));
    }

    @Override
    public String getAsString(FacesContext ctx, UIComponent comp, Etapa e) {
        return (e == null || e.getId() == null) ? "" : String.valueOf(e.getId());
    }
}