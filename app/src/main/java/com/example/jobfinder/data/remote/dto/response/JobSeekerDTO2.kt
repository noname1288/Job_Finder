package com.example.jobfinder.data.remote.dto.response

data class JobSeekerDTO2(
    val birthDate: String? = null,
    val certifications: List<String> = emptyList(),
    val cvFile: String? = null,
    val education:List<String> = emptyList(),
    val email: String ="",
    val fullName: String ="",
    val id: Int = 0,
    val languages:List<String> = emptyList(),
    val phoneNumber: String? = null,
    val profilePicture: String? = null,
    val skills:List<String> = emptyList(),
    val workExperience: String? = null
)