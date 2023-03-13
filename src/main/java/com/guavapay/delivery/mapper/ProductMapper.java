package com.guavapay.delivery.mapper;

import com.guavapay.delivery.dto.request.ProductRequest;
import com.guavapay.delivery.dto.response.ProductResponse;
import com.guavapay.delivery.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product mapToEntity(ProductRequest productRequest);

    ProductResponse mapToResponse(Product product);
}
