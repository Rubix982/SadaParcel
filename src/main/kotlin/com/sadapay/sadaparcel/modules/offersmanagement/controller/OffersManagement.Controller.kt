/*
 * Copyright 2023, Saif Ul Islam @ SadaParcel, Inc
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the “Software”), to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of
 * the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 * OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package com.sadapay.sadaparcel.modules.offersmanagement.controller

import com.sadapay.sadaparcel.modules.offer.contract.OffersDto
import com.sadapay.sadaparcel.modules.offersmanagement.constants.OffersManagementConstants
import com.sadapay.sadaparcel.modules.offersmanagement.service.OffersManagementService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import com.sadapay.sadaparcel.config.controller.GlobalControllerConstants as constants

@RestController
class OffersManagementController @Autowired constructor(offersManagementService: OffersManagementService?) {

    private val offersManagementService: OffersManagementService?

    init {
        this.offersManagementService = offersManagementService
    }

    @GetMapping(OffersManagementConstants.ROUTE, produces = [constants.JSON])
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    fun getOffersManagement(): ResponseEntity<OffersDto?> =
        ResponseEntity.status(HttpStatus.OK).body(offersManagementService?.findAll())

    @PostMapping(OffersManagementConstants.ROUTE, produces = [constants.JSON])
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    fun addOffer(@Valid @RequestBody offersDto: OffersDto): ResponseEntity<OffersDto> {

        if (offersManagementService == null) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(offersDto)
        }

        if (offersDto.offers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(offersDto)
        }

        offersManagementService.save(offersDto)

        return ResponseEntity.status(HttpStatus.CREATED).body(offersDto)
    }

    @DeleteMapping(OffersManagementConstants.ROUTE, produces = [constants.JSON])
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    fun removeOffer(offerIds: List<String>): ResponseEntity<List<String>> {

        if (offerIds.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(listOf())
        }

        offersManagementService?.areAllIdsValid(offerIds)?.let { areAllIdsValid ->
            if (!areAllIdsValid) {
                // If offerIds has some or all invalid Ids, return a 409 Conflict
                // https://stackoverflow.com/questions/25122472/rest-http-status-code-if-delete-impossible
                return ResponseEntity.status(HttpStatus.CONFLICT).body(listOf())
            }
        }

        offersManagementService?.deleteOffers(offerIds)

        return ResponseEntity.status(HttpStatus.OK).body(offerIds)
    }
}