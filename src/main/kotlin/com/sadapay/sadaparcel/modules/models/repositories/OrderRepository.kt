package com.sadapay.sadaparcel.modules.models.repositories

import com.sadapay.sadaparcel.modules.models.entities.Order
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository : CrudRepository<Order?, Long?>, JpaRepository<Order?, Long?>,
    PagingAndSortingRepository<Order?, Long?>