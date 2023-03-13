package com.guavapay.delivery.service.api;

import com.guavapay.delivery.dto.request.ProductRequest;
import com.guavapay.delivery.dto.response.ProductResponse;

public interface ProductService {

    ProductResponse createProduct(ProductRequest productRequest);

}
