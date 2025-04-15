package com.example.jobfinder.domain.entity

data class Seeker(
    // Thông tin cơ bản
    val name: String,
    val dateOfBirth: String,
    val email: String,
    val phoneNumber: String,

    // Danh sách các thuộc tính liên quan đến trải nghiệm
    val experiences: List<Experience> = emptyList(),

    // Danh sách các thuộc tính học vấn
    val educations: List<Education> = emptyList(),

    // Danh sách kỹ năng
    val skills: List<Skill> = emptyList(),

    // Danh sách chứng chỉ
    val certificates: List<Certificate> = emptyList(),

    // Thông tin CV (có thể để null nếu không có)
    val cv: CV? = null
) {
    // Data class lồng cho kinh nghiệm làm việc
    data class Experience(
        val companyName: String,
        val duration: String
    )

    // Data class lồng cho học vấn
    data class Education(
        val schoolName: String,
        val duration: String
    )

    // Data class lồng cho kỹ năng
    data class Skill(
        val name: String
    )

    // Data class lồng cho chứng chỉ
    data class Certificate(
        val title: String
    )

    // Data class lồng cho thông tin CV
    data class CV(
        val title: String,
        val link: String
    )
}
