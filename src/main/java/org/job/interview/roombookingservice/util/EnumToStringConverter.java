package org.job.interview.roombookingservice.util;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

@Component
public class EnumToStringConverter<T extends Enum> implements Converter<T, String> {

    @Override
    public String convert(MappingContext<T, String> context) {
        return context.getSource().toString();
    }
}
