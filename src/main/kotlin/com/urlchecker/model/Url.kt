package com.urlchecker.model

import java.time.Instant
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "urls")
data class Url(

    @Id
    @Column
    val url: String,

    @Column
    val createdAt: Instant
)
