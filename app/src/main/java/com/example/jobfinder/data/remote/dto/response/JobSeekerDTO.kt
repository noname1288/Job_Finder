package com.example.jobfinder.data.remote.dto.response

data class JobSeekerDTO(
    val birthDate: String?,
    val certifications: String="",
    val cvFile: String?,
    val education: String?,
    val email: String,
    val fullName: String,
    val id: Int,
    val languages: String="",
    val phoneNumber: String?,
    val profilePicture: String?,
    val skills: String="",
    val workExperience: String?
)