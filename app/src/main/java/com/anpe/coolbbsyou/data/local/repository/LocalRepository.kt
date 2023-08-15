package com.anpe.coolbbsyou.data.local.repository

import com.anpe.coolbbsyou.data.local.database.LaterDatabase
import com.anpe.coolbbsyou.data.local.entity.later.LaterEntity
import com.anpe.coolbbsyou.util.MyApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LocalRepository {
    private val dao = LaterDatabase.getDatabase(MyApplication.context).getLaterDao()

    fun upsertLater(vararg laterEntity: LaterEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            dao.upsertLater(*laterEntity)
        }
    }

    fun deleteAllLater() {
        CoroutineScope(Dispatchers.IO).launch {
            dao.deleteAllLater()
        }
    }

    fun getAllLater() = dao.getAllLater()
}