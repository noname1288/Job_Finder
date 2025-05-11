package com.example.jobfinder.domain.entity

interface User {
    val id: Int?
    val fulName: String
    val email: String
    val password: String
    val picUri: String?
    val token: String?
}

data class Recruiter1(
    override val id: Int?,
    override val fulName: String,
    override val email: String,
    override val password: String,
    override val picUri: String?,
    override val token: String?,
    val companyName: String?,
    val companyDescription: String?,
    val companyAddress: String?,
    val companyPhone: String?,
    val role_recrutier: String = "RECRUITER"
    ) : User

data class Seeker1(
    override val id: Int?,
    override val fulName: String,
    override val email: String,
    override val password: String,
    override val picUri: String,
    override val token: String,
    val birth_date: String,
    val work_experience: String,
    val education: String,
    val cv_file: String,
    val role_seeker: String = "JOB_SEEKER"
) : User