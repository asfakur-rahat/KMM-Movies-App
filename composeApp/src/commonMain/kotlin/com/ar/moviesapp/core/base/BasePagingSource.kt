package com.ar.moviesapp.core.base

import app.cash.paging.PagingSource
import app.cash.paging.PagingSourceLoadParams
import app.cash.paging.PagingSourceLoadResult
import app.cash.paging.PagingSourceLoadResultError
import app.cash.paging.PagingSourceLoadResultPage
import app.cash.paging.PagingState
import com.ar.moviesapp.domain.model.PaginationItems

abstract class BasePagingSource<Value : Any> : PagingSource<Int, Value>() {

    protected abstract suspend fun fetchData(page: Int, limit: Int): PaginationItems<Value>

    override suspend fun load(params: PagingSourceLoadParams<Int>): PagingSourceLoadResult<Int, Value> {
        val currentPage = params.key ?: 1
        val limit = params.loadSize
        val maxPageAllowed = 100
        return try {
            val response = fetchData(currentPage, limit)

            PagingSourceLoadResultPage(
                data = response.items,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey =  ( currentPage + 1 ).takeIf { currentPage < maxPageAllowed }
            )

        } catch (e: Exception) {
            PagingSourceLoadResultError(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Value>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val closestPage = state.closestPageToPosition(anchorPosition)
            closestPage?.prevKey?.plus(1) ?: closestPage?.nextKey?.minus(1)
        }
    }
}