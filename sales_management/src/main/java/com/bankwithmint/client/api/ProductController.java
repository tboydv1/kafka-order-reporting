package com.bankwithmint.client.api;

import com.bankwithmint.data.dto.ProductDto;
import com.bankwithmint.client.exceptions.ProductDoesNotExistsException;
import com.bankwithmint.data.models.Product;
import com.bankwithmint.service.product.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @author oluwatobi
 * @version 1.0
 * @date on 13/07/2021
 * inside the package - com.bankwithmint.sales.client.api
 */

@RestController
@RequestMapping("v1/api/products")
@Slf4j
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping({"", "/"})
    public ResponseEntity<?> getProducts(){

        List<Product> existingProducts =
                productService.getProducts()
                                    .stream()
                                    .map(product ->  product.add(
                                            linkTo(methodOn(ProductController.class)
                                                    .findById(product.getId())).withSelfRel(),
                                            linkTo(methodOn(ProductController.class).getProducts()).withRel("products")
                                    )).collect(Collectors.toList());
        return ResponseEntity.ok().body(existingProducts);
    }

    @PostMapping({"", "/"})
    public ResponseEntity<?> save(@Valid @RequestBody ProductDto productDto)
    {
        Product product = productService.createProduct(productDto);

        product.add(linkTo(methodOn(
                ProductController.class)
                        .findById(product.getId())).withSelfRel(),
                linkTo(methodOn(ProductController.class)
                        .getProducts()).withRel("products"));

        return ResponseEntity.created(
                product.getRequiredLink(
                        IanaLinkRelations.SELF).toUri()).body(product);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id){
        return null;
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id,
                                            @RequestBody ProductDto productDto){
        Product product = null;

        try {
            product = productService.updateProduct(productDto, id);

            product.add(
                    linkTo(methodOn(
                            ProductController.class)
                            .findById(product.getId())).withSelfRel(),
                    linkTo(methodOn(ProductController.class)
                            .getProducts()).withRel("products"));
        }
        catch (ProductDoesNotExistsException productDoesNotExistsException){
            log.info("Error occurred --> {}", productDoesNotExistsException.getMessage());
            ResponseEntity.badRequest().body(productDoesNotExistsException.getMessage());
        }

        return ResponseEntity.ok().body(product);
    }




}
