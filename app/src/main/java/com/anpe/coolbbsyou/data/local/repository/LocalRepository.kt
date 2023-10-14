package com.anpe.coolbbsyou.data.local.repository

import com.anpe.coolbbsyou.data.local.database.MyDatabase
import com.anpe.coolbbsyou.data.local.entity.cookie.CookieEntity
import com.anpe.coolbbsyou.data.local.entity.device.DeviceEntity
import com.anpe.coolbbsyou.data.local.entity.later.LaterEntity
import com.anpe.coolbbsyou.util.MyApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LocalRepository {
    private val database = MyDatabase.getDatabase(MyApplication.context)
    private val cookieDao = database.getCookieDao()
    private val deviceDao = database.getDeviceDao()
    private val laterDao = database.getLaterDao()

    /**
     * Later DAO
     */
    fun upsertLater(vararg laterEntity: LaterEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            laterDao.upsertLater(*laterEntity)
        }
    }

    fun deleteAllLater() {
        CoroutineScope(Dispatchers.IO).launch {
            laterDao.deleteAllLater()
        }
    }

    fun getAllLater() = laterDao.getAllLater()

    /**
     * Cookie DAO
     */
    fun upsertCookie(vararg cookieEntity: CookieEntity) = cookieDao.upsert(*cookieEntity)

    fun getCookie(host: String) = cookieDao.find(host)

    fun getCookieAll() = cookieDao.getAll()

    fun deleteCookie(vararg cookieEntity: CookieEntity) = cookieDao.delete(*cookieEntity)

    fun deleteCookieAll() = CoroutineScope(Dispatchers.IO).launch {
        cookieDao.deleteAll()
    }

    /**
     * Device DAO
     */
    fun upsertDevice(vararg device: DeviceEntity) = CoroutineScope(Dispatchers.IO).launch {
        deviceDao.upsert(*device)
    }

    fun deleteDevice(vararg device: DeviceEntity) = CoroutineScope(Dispatchers.IO).launch {
        deviceDao.delete(*device)
    }

    fun deleteDeviceAll() = CoroutineScope(Dispatchers.IO).launch {
        deviceDao.deleteAll()
    }

    fun getDeviceAll() = deviceDao.getDeviceAll()
}