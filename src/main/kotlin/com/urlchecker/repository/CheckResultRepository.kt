package com.urlchecker.repository

import com.urlchecker.model.CheckResult
import com.urlchecker.model.Url
import org.springframework.data.repository.PagingAndSortingRepository

interface CheckResultRepository : PagingAndSortingRepository<CheckResult, Long>