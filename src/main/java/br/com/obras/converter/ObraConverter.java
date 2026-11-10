package br.com.obras.converter;

import br.com.obras.dao.ObraDAO;
import br.com.obras.model.Obra;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

@FacesConverter(value = "obraConverter", managed = true)
public class ObraConverter implements Converter<Obra> {

    private final ObraDAO obraDAO = new ObraDAO();

    @Override
    public Obra getAsObject(FacesContext context,
                            UIComponent component,
                            String value) {

        if (value == null || value.trim().isEmpty()) {
            return null;
        }

        return obraDAO.buscarPorId(Long.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext context,
                              UIComponent component,
                              Obra obra) {

        if (obra == null || obra.getId() == null) {
            return "";
        }

        return obra.getId().toString();
    }
}
