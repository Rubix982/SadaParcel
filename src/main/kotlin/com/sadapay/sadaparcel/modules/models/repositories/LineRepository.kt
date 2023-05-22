package com.sadapay.sadaparcel.modules.models.repositories

import com.sadapay.sadaparcel.modules.models.entities.Line
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface LineRepository : CrudRepository<Line?, Long?>, JpaRepository<Line?, Long?>,
    PagingAndSortingRepository<Line?, Long?>