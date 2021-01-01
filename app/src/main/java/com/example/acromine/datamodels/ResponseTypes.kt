package com.example.acromine.datamodels

data class AcroResponse(
    val sf: String,
    val lfs: List<Longform>
)

data class Longform(
    val lf: String,
    val since: Int
)