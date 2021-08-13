package com.example.demo.converter;

import java.text.ParseException;
import java.util.Objects;
import javax.persistence.AttributeConverter;
import org.quartz.CronExpression;
import org.springframework.stereotype.Component;

@Component
public class CronExpressionConverter implements AttributeConverter<CronExpression,String > {

    @Override
    public String convertToDatabaseColumn(CronExpression cronExpression) {
       if(Objects.isNull(cronExpression)){
        return null;
    }
            return cronExpression.toString();
    }

    @Override
    public CronExpression convertToEntityAttribute(String s) {
        try {
            return Objects.isNull(s)?null:new CronExpression(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
