package com.cemede.cemede.presentation.util

import com.cemede.cemede.domain.model.StaffMember
import com.cemede.cemede.getPlatform

object PhonesHelper {
    private val staffPhones =
        mapOf(
            "Tomas" to "+5492494001895",
            "Macarena" to "+5491136303223",
            "Manuel" to "+5492494688414",
            "Santiago" to "+5492494682506",
            "Melany" to "+5492494311056",
            "Gonzalo" to "+5492926477145",
        )

    fun openWhatsApp(staffMemberName: String) {
        val phone = staffPhones[staffMemberName] ?: return
        val url = "https://wa.me/$phone"
        getPlatform().openUrl(url)
    }

    fun callStaffMember(staffMemberName: String) {
        val phone = staffPhones[staffMemberName] ?: return
        val url = "tel:$phone"
        getPlatform().openUrl(url)
    }
}
