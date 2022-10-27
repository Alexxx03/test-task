package org.job.interview.roombookingservice.util;

import org.job.interview.roombookingservice.exceptions.InvalidWeekdayException;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class StringToEnumConverter<T extends Enum> implements Converter<String, T> {

    @Override
    public T convert(MappingContext<String, T> context) {
        try {
            return (T) Enum.valueOf(context.getDestinationType(), context.getSource().toUpperCase().replace(" ", "_").replace("-", "_"));
        } catch (IllegalArgumentException | NullPointerException ex) {
            throw new InvalidWeekdayException(HttpStatus.BAD_REQUEST, "Weekday is invalid");
        }
    }
}
