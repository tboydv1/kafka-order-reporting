package com.bankwithmint.sales.service.product;

import com.bankwithmint.sales.client.exceptions.ProductDoesNotExistsException;
import com.bankwithmint.sales.data.dto.ProductDto;
import com.bankwithmint.sales.data.models.Product;
import com.bankwithmint.sales.data.repository.ProductRepository;
import com.bankwithmint.sales.service.mapper.ProductMapper;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author oluwatobi
 * @version 1.0
 * @date on 12/07/2021
 * inside the package - com.bankwithmint.sales.service.product
 */

@Service
@Slf4j
@NoArgsConstructor
public class ProductServiceImpl implements ProductService{

    ProductRepository productRepository;

    ProductMapper productMapper;

    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock writeLock = lock.writeLock();
    private final Lock readLock = lock.readLock();

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    /**
     *
     * @param productDto
     * @return
     */
    @Override
    public Product createProduct(ProductDto productDto) {

        Product product = new Product();
        productMapper.mapToProduct(productDto, product);

        return productRepository.save(product);
    }

    /**
     *
     * @return
     */
    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }



    @Override
    public Product updateProduct(ProductDto productDto, Long id) throws ProductDoesNotExistsException {

        Product product = productRepository.findById(id)
                .orElseThrow( () -> new ProductDoesNotExistsException("Product with id === "+id+" does not exist"));

        //maps productdto to product ignoring null values
        productMapper.mapToProduct(productDto,product);

        log.info("Product object before update --> {}", product);
        return productRepository.save(product);

    }

    @Override
    public boolean exists(Long productId) {
        return productRepository.findById(productId).isPresent();
    }

    @Override
    public Product findById(Long productId) {
        return productRepository.findById(productId).orElse(null);
    }

    @Override
    public void updateQuantity(Product product, Integer quantityLess) {
        Integer quantity = product.getQuantityInStock();
        Integer newQuantity = quantity - quantityLess;
        product.setQuantityInStock(newQuantity);
        productRepository.save(product);
    }

}
