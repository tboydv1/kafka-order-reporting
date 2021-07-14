package com.bankwithmint.data.repository;

import com.bankwithmint.data.models.SalesOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author oluwatobi
 * @version 1.0
 * @date on 12/07/2021
 * inside the package - com.bankwithmint.sales.data.repository
 */

@Repository
public interface OrderRepository extends JpaRepository<SalesOrder, Long> {

}
