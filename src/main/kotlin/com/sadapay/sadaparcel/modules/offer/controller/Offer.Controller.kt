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

package com.sadapay.sadaparcel.modules.offer.controller

import com.sadapay.sadaparcel.config.controller.GlobalControllerConstants
import com.sadapay.sadaparcel.modules.models.constants.OfferConstants
import com.sadapay.sadaparcel.modules.offer.contract.OffersDto
import com.sadapay.sadaparcel.modules.offer.service.OfferService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class OffersController @Autowired constructor(offerService: OfferService?) {
    private val offerService: OfferService?

    init {
        this.offerService = offerService
    }

    @GetMapping("/${OfferConstants.TABLE_NAME}", produces = [GlobalControllerConstants.JSON])
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    fun getOffers(): ResponseEntity<OffersDto?> = ResponseEntity.status(HttpStatus.OK).body(offerService?.findAll())
}