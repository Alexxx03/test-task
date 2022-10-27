package org.job.interview.roombookingservice.util;

import org.job.interview.roombookingservice.exceptions.InvalidWeekdayException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class StringToEnumConverterFactory
        implements ConverterFactory<String, Enum> {

    private static class StringToEnumConverter<T extends Enum>
            implements Converter<String, T> {

        private Class<T> enumType;

        public StringToEnumConverter(Class<T> enumType) {
            this.enumType = enumType;
        }

        public T convert(String source) {
            try {
                return (T) Enum.valueOf(this.enumType, source.toUpperCase().replace(" ", "_").replace("-", "_"));
            } catch (IllegalArgumentException | NullPointerException ex) {
                throw new InvalidWeekdayException(HttpStatus.BAD_REQUEST, "Weekday is invalid");
            }
        }
    }

    @Override
    public <T extends Enum> Converter<String, T> getConverter(Class<T> targetType) {
        return new StringToEnumConverter<>(targetType);
    }
}
