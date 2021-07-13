package com.bankwithmint.sales.data.repository;

import com.bankwithmint.sales.data.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author oluwatobi
 * @version 1.0
 * @date_created on 12/07/2021
 * inside the package - com.bankwithmint.sales.data.repository
 */

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
