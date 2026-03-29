package com.cemede.cemede.data.data_base.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "partners",
    foreignKeys = [
        ForeignKey(
            entity = StaffMemberEntity::class,
            parentColumns = ["id"],
            childColumns = ["staffMemberId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [Index(value = ["name"], unique = true)],
)
data class PartnerEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val processType: String,
    val staffMemberId: Int,
)
