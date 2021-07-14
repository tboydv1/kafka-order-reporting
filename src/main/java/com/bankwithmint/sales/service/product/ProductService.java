package com.bankwithmint.sales.service.product;

import com.bankwithmint.sales.client.exceptions.ProductDoesNotExistsException;
import com.bankwithmint.sales.data.dto.ProductDto;
import com.bankwithmint.sales.data.models.Product;

import java.util.List;

/**
 * @author oluwatobi
 * @version 1.0
 * @date on 12/07/2021
 * inside the package - com.bankwithmint.sales.service.product
 */


public interface ProductService {

     Product createProduct(ProductDto productDto);
     List<Product> getProducts();
     Product updateProduct(ProductDto productDto, Long ProductId) throws ProductDoesNotExistsException;
     boolean exists(Long productId);
     Product findById(Long productId);
     void updateQuantity(Product product, Integer quantityLess);
}
