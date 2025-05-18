package com.example.jobfinder.data.remote.mapper

import com.example.jobfinder.data.remote.dto.response.JobTemp
import com.example.jobfinder.domain.entity.Job
import com.example.jobfinder.domain.entity.Shift
import com.example.jobfinder.service_locator.AppContainer

fun Job.toJobTemp(): JobTemp {
    return JobTemp(
        companyAddress = this.location,
        createAt = AppContainer.localDateTimeToString(this.createAt),
        endAt = AppContainer.localDateTimeToString(this.updateAt),
        numberOfApplicants = this.candidateCount,
        numberOfRecruit = this.numberOfPositions,
        title = this.title,
        updateAt = AppContainer.localDateTimeToString(this.endAt)
    )
}

fun JobTemp.toJob() : Job{
    return Job(
        id = 0,
        title = this.title,
        description = "",
        requirement = "",
        benefit ="",
        salary = "",
        location = this.companyAddress,
        numberOfPositions = this.numberOfRecruit,
        candidateCount = this.numberOfApplicants,
        createAt = AppContainer.stringToLocalDateTime(this.createAt),
        updateAt =AppContainer.stringToLocalDateTime(this.updateAt),
        endAt = AppContainer.stringToLocalDateTime(this.endAt),
        shift = Shift(
            endTime = AppContainer.stringToLocalDateTime(""),
            name = "",
            startTime =AppContainer.stringToLocalDateTime("")
        ),
        recruiter = "",
        status =""
    )
}