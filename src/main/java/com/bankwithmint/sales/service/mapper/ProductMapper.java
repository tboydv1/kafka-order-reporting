package com.bankwithmint.sales.service.mapper;

import com.bankwithmint.sales.data.dto.ProductDto;
import com.bankwithmint.sales.data.models.Product;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * @author oluwatobi
 * @version 1.0
 * @date on 13/07/2021
 * inside the package - com.bankwithmint.sales.service.mapper
 */

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @BeanMapping(nullValuePropertyMappingStrategy =
            NullValuePropertyMappingStrategy.IGNORE)
    void mapToProduct(ProductDto productDto,
                      @MappingTarget Product Product);
}
