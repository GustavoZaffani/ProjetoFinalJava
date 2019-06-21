package br.edu.utfpr.pb.ProjetoFinal.util;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Date;
import java.time.LocalDate;

@Converter(autoApply = true)
public class LocalDateAttributeConverter implements
        AttributeConverter<LocalDate, Date> {

    @Override
    public Date convertToDatabaseColumn(LocalDate x) {
        return (x == null ? null : Date.valueOf(x));
    }

    @Override
    public LocalDate convertToEntityAttribute(Date y) {
        return (y == null ? null : y.toLocalDate());
    }

}
