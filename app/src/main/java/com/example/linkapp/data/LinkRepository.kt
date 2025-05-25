package com.example.linkapp.data

class LinkRepository(private val dao: LinkDao) {
    suspend fun getAllLinks() = dao.getAll()
    suspend fun insertLink(link: LinkEntity) = dao.insert(link)
    suspend fun deleteLink(link: LinkEntity) = dao.delete(link)
}