package com.example.jobfinder.data.remote

import com.example.jobfinder.data.remote.dto.response.JobSeekerDTO2
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class JobSeekerDTO2Deserializer : JsonDeserializer<JobSeekerDTO2> {
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): JobSeekerDTO2 {
        val obj = json.asJsonObject

        fun parseListOrString(key: String): List<String> {
            return when {
                obj.get(key)?.isJsonArray == true ->
                    obj.getAsJsonArray(key).mapNotNull { it.asString.trim() }

                obj.get(key)?.isJsonPrimitive == true ->
                    obj.get(key).asString.split(",").map { it.trim() }.filter { it.isNotBlank() }

                else -> emptyList()
            }
        }

        return JobSeekerDTO2(
            id = obj["id"].asInt,
            fullName = obj["fullName"].asString,
            email = obj["email"].asString,
            profilePicture = obj["profilePicture"]?.asString,
            phoneNumber = obj["phoneNumber"]?.asString,
            birthDate = obj["birthDate"]?.asString,
            workExperience = obj["workExperience"]?.asString,
            cvFile = obj["cvFile"]?.asString,
            education = parseListOrString("education"),
            certifications = parseListOrString("certifications"),
            skills = parseListOrString("skills"),
            languages = parseListOrString("languages")
        )
    }
}
