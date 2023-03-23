package com.voc.honkai_stargazer.data.repo

import com.voc.honkai_stargazer.data.local.StargazerDao
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val stargazerDao: StargazerDao
) : Repository {

}