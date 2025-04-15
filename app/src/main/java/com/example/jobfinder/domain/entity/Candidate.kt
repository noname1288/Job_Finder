package com.example.jobfinder.domain.entity

import com.example.jobfinder.R

data class Candidate(
    val name: String,
    val birthDate: String,     // "14 tháng 1, 2003"
    val location: String,      // "Quận Hai Bà Trưng, Hà Nội"
    val isVerified: Boolean,   // có “tick xanh” bên cạnh tên hay không
    val avatarRes: Int         // resource ảnh đại diện, ví dụ R.drawable.avatar
)

val sampleCandidates = listOf(
    Candidate(
        name = "Donal Trump",
        birthDate = "14 tháng 1, 2003",
        location = "Quận Hai Bà Trưng, Hà Nội",
        isVerified = true,
        avatarRes = R.drawable.avt_candidate // ví dụ
    ),
    Candidate(
        name = "Donal Trump",
        birthDate = "14 tháng 1, 2003",
        location = "Quận Hai Bà Trưng, Hà Nội",
        isVerified = true,
        avatarRes = R.drawable.avt_candidate
    ),
    // ... Thêm tuỳ ý
)
