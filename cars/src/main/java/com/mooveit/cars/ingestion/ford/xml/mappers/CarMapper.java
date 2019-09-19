package com.mooveit.cars.ingestion.ford.xml.mappers;

import com.mooveit.cars.domain.Car;
import com.mooveit.cars.domain.Wheels;
import com.mooveit.cars.ingestion.ford.xml.model.FordModel;
import com.mooveit.cars.ingestion.ford.xml.model.FordWheels;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.Year;

@Mapper(componentModel = "spring")
public interface CarMapper {

    @Named("intToYear")
    static Year intToYear(int year) {
        return Year.of(year);
    }

    @Named("fordSizeToInt")
    static int fordSizeToInt(String size) {
        return Integer.parseInt(size.replaceFirst("R", ""));
    }

    @Mapping(target = "brand", expression = "java(\"Ford\")")
    @Mapping(source = "name", target = "modelName")
    @Mapping(source = "from", target = "productionYearFrom", qualifiedByName = "intToYear")
    @Mapping(source = "to", target = "productionYearTo", qualifiedByName = "intToYear")
    @Mapping(source = "subModels.fordSubModels", target = "subModels")
    @Mapping(target = "parentModel", ignore = true)
    Car fordModelToCar(FordModel fordModel);

    @Mapping(source = "size", target = "size", qualifiedByName = "fordSizeToInt")
    Wheels fordWheelsToWheels(FordWheels fordWheels);
}