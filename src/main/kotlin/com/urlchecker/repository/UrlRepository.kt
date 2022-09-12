package com.urlchecker.repository

import com.urlchecker.model.Url
import org.springframework.data.repository.PagingAndSortingRepository

interface UrlRepository : PagingAndSortingRepository<Url, String>