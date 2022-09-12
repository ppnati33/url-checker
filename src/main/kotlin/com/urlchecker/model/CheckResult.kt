package com.urlchecker.model

import com.urlchecker.model.enums.CheckResultType
import java.time.Instant
import javax.persistence.*

@Entity
@Table(name = "check_results")
data class CheckResult(

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "check_result_pk_seq")
    var id: Long? = null,

    @Enumerated(EnumType.STRING)
    @Column
    val checkResult: CheckResultType,

    @Column
    val checkTime: Instant,

    @Column
    val url: String
)