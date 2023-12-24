package io.strongapp.gymworkout.data.repository

import io.strongapp.gymworkout.data.database.daos.UserDao
import io.strongapp.gymworkout.data.database.entities.UserEntity

class UserRepository(private val userDao: UserDao) {
	suspend fun insertUser(userEntity: UserEntity): Long {
		return userDao.insert(userEntity)
	}

	fun getInfo(): UserEntity {
		return userDao.getInfo()
	}
}