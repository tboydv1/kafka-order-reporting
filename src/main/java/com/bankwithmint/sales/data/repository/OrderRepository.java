package com.bankwithmint.sales.data.repository;

import com.bankwithmint.sales.data.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author oluwatobi
 * @version 1.0
 * @date on 12/07/2021
 * inside the package - com.bankwithmint.sales.data.repository
 */

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
