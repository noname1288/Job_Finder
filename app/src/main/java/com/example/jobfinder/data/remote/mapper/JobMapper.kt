package com.example.jobfinder.data.remote.mapper

import com.example.jobfinder.data.remote.dto.response.JobTemp
import com.example.jobfinder.data.remote.dto.response.JobTemp2
import com.example.jobfinder.domain.entity.Job
import com.example.jobfinder.domain.entity.Shift
import com.example.jobfinder.utils.Utils

fun Job.toJobTemp(): JobTemp {
    return JobTemp(
        companyAddress = this.location,
        createAt = Utils.localDateTimeToString(this.createAt),
        endAt = Utils.localDateTimeToString(this.updateAt),
        numberOfApplicants = this.candidateCount,
        numberOfRecruit = this.numberOfPositions,
        title = this.title,
        updateAt = Utils.localDateTimeToString(this.endAt)
    )
}

fun JobTemp.toJob(): Job {
    return Job(
        id = this.jobId,
        title = this.title,
        description = "",
        requirement = "",
        benefit = "",
        salary = "",
        location = this.companyAddress,
        numberOfPositions = this.numberOfRecruit,
        candidateCount = this.numberOfApplicants,
        createAt = Utils.stringToLocalDateTime(this.createAt),
        updateAt = Utils.stringToLocalDateTime(this.updateAt),
        endAt = Utils.stringToLocalDateTime(this.endAt),
        shift = Shift(
            endTime = Utils.stringToLocalDateTime(""),
            name = "",
            startTime = Utils.stringToLocalDateTime("")
        ),
        recruiter = "",
        status = "",
    )
}

fun JobTemp2.toJob(): Job {
    return Job(
        id = this.id,
        title = this.title,
        description = this.description,
        requirement = this.requirement,
        benefit = this.benefit,
        salary = this.salary,
        location = this.location,
        numberOfPositions = this.numberOfPositions,
        candidateCount = -1,
        createAt = Utils.stringToLocalDateTime(this.postDate),
        updateAt = Utils.stringToLocalDateTime(this.updatedDate), //can be null
        endAt = Utils.stringToLocalDateTime(this.deadLine),
        shift = Utils.parseShift(this.shift),
        recruiter = if(this.recruiter == null) "" else  this.recruiter,
        status = this.status
    )
}