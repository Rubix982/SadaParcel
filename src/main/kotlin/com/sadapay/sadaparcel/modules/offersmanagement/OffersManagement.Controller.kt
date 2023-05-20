package com.sadapay.sadaparcel.modules.offersmanagement

import com.sadapay.sadaparcel.modules.models.entities.Offer
import com.sadapay.sadaparcel.modules.offer.OfferDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
class OffersManagementController @Autowired constructor(offersManagementService: OffersManagementService?) {

    private val offersManagementService: OffersManagementService?

    init {
        this.offersManagementService = offersManagementService
    }

    @GetMapping(produces = ["application/json"])
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    fun getOffersManagement(): MutableIterable<Offer?>? = offersManagementService?.findAll()

    @PostMapping("/offers-management", produces = ["application/json"])
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    fun addOffer(@Valid @RequestBody offerDto: OfferDto): ResponseEntity<OfferDto> {

        if (offerDto.itemId.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(offerDto)
        }

        offersManagementService?.save(offerDto)
        return ResponseEntity.status(HttpStatus.CREATED).body(offerDto)
    }

    @DeleteMapping("/offers-management", produces = ["application/json"])
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