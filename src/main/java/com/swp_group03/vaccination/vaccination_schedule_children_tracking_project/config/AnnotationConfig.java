package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.config;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.sql.Date;
import java.time.LocalDate;

@Converter(autoApply = true)  // Tự động áp dụng cho tất cả LocalDate trong project
public class AnnotationConfig implements AttributeConverter<LocalDate, Date> {

    // Chuyển từ LocalDate (Java) sang Date (SQL) khi lưu xuống DB
    @Override
    public Date convertToDatabaseColumn(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }
        return Date.valueOf(localDate);
    }

    // Chuyển từ Date (SQL) sang LocalDate (Java) khi đọc từ DB
    @Override
    public LocalDate convertToEntityAttribute(Date sqlDate) {
        if (sqlDate == null) {
            return null;
        }
        return sqlDate.toLocalDate();
    }
}