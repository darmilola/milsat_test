package com.application.milsat.android.Room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Form(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "NAME_BLD") val buildingName: String?,
    @ColumnInfo(name = "ADDRESS") val address: String?,
    @ColumnInfo(name = "OWNER") val owner: String?,
    @ColumnInfo(name = "BLD_STAT") val buildingStatus: String?,
    @ColumnInfo(name = "NUM_OWN") val ownersPhone: String?,
    @ColumnInfo(name = "NAM_OWN") val ownersFullName: String?,
    @ColumnInfo(name = "BLD_USE") val buildingUses: String?)