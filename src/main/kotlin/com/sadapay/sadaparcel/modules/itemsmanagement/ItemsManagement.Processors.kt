package com.sadapay.sadaparcel.modules.itemsmanagement

import com.sadapay.sadaparcel.modules.transformations.MonadProcessor

data class ItemsManagementMonadProcessor(
    var wasAnItemUpdated: Boolean = false,
) : MonadProcessor()
