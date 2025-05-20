package com.example.jobfinder.presentation.candidate.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.jobfinder.domain.entity.Seeker

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CandidateProfilePage(
    navController: NavController
) {
    val seeker = Seeker(
        name = "Nguyễn Bằng Long",
        dateOfBirth = "12/01/2000",
        email = "banglong@sample.com",
        phoneNumber = "0123456789",
        experiences = listOf(
            Seeker.Experience("Awesome Inc", "2015 - 2020"),
            Seeker.Experience("Cool Startup", "2020 - Nay")
        ),
        educations = listOf(
            Seeker.Education("ĐH X", "2012 - 2016"),
            Seeker.Education("ĐH Y", "2017 - 2019")
        ),
        skills = listOf(
            Seeker.Skill("Quản lý"),
            Seeker.Skill("Teamwork"),
            Seeker.Skill("Giám sát"),
            Seeker.Skill("Digital Marketing")
        ),
        certificates = listOf(
            Seeker.Certificate("Webabee Symposium (WBS)"),
            Seeker.Certificate("Google Ads Certificate")
        ),
        cv = Seeker.CV(
            title = "CV - UX/UI Designer",
            link = "https://example.com/nguyen-bang-long-cv.pdf"
        )
    )
    // Sử dụng Scaffold để có TopBar ở trên, nội dung ở dưới
    Scaffold(
        topBar = {
            TopBar(title = "Xem chi tiết ứng viên", onBack = { navController.popBackStack() })
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
        ) {
            // Phần Header (ảnh đại diện, tên)
            item {
                ProfileHeader(
                    name = seeker.name,
                    avatarUrl = null // hoặc link avatar thực tế
                )
            }

            // Thông tin cơ bản
            item {
                SectionCard(title = "Thông tin cơ bản") {
                    BasicInfoSection(seeker)
                }
            }

            // Kinh nghiệm
            if (seeker.experiences.isNotEmpty()) {
                item {
                    SectionCard(title = "Kinh nghiệm") {
                        ExperienceSection(seeker.experiences)
                    }
                }
            }

            // Học vấn
            if (seeker.educations.isNotEmpty()) {
                item {
                    SectionCard(title = "Học vấn") {
                        EducationSection(seeker.educations)
                    }
                }
            }

            // Kỹ năng
            if (seeker.skills.isNotEmpty()) {
                item {
                    SectionCard(title = "Kỹ năng") {
                        SkillsSection(seeker.skills)
                    }
                }
            }

            // Chứng chỉ
            if (seeker.certificates.isNotEmpty()) {
                item {
                    SectionCard(title = "Chứng chỉ") {
                        CertificateSection(seeker.certificates)
                    }
                }
            }

            // CV (nếu có)
            seeker.cv?.let { cv ->
                item {
                    SectionCard(title = "CV") {
                        CVSection(cv)
                    }
                }
            }

            // Khoảng trống cuối (nếu muốn)
            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(title: String, onBack: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
        },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    )
}

@Composable
fun ProfileHeader(
    name: String,
    avatarUrl: String? // link ảnh thực tế nếu có
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(bottom = 32.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            AvatarImage(avatarUrl)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = name,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.SemiBold
                )
            )
        }
    }
}

@Composable
fun AvatarImage(avatarUrl: String?, size: Dp = 80.dp) {
    // Ở đây minh họa icon mặc định. Có thể dùng Coil để load ảnh online.
    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(Color.White.copy(alpha = 0.2f)),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = "Avatar",
            tint = Color.White,
            modifier = Modifier.size(size / 2)
        )
    }
}

@Composable
fun SectionCard(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            content()
        }
    }
}

@Composable
fun BasicInfoSection(seeker: Seeker) {
    Column {
        InfoRow("Họ tên:", seeker.name)
        InfoRow("Ngày sinh:", seeker.dateOfBirth)
        InfoRow("Email:", seeker.email)
        InfoRow("SĐT:", seeker.phoneNumber)
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
            modifier = Modifier.width(100.dp)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun ExperienceSection(experiences: List<Seeker.Experience>) {
    Column {
        experiences.forEach { exp ->
            Text(
                text = exp.companyName,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium)
            )
            Text(
                text = exp.duration,
                style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray)
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun EducationSection(educations: List<Seeker.Education>) {
    Column {
        educations.forEach { edu ->
            Text(
                text = edu.schoolName,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium)
            )
            Text(
                text = edu.duration,
                style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray)
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@OptIn(ExperimentalLayoutApi::class) // FlowRow còn ở dạng experimental
@Composable
fun SkillsSection(skills: List<Seeker.Skill>) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        maxItemsInEachRow = 3,
    ) {
        skills.forEach { skill ->
            SkillChip(skillName = skill.name)

        }
    }
}

@Composable
fun SkillChip(skillName: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
            .padding(vertical = 4.dp, horizontal = 12.dp)
    ) {
        Text(
            text = skillName,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
            color = MaterialTheme.colorScheme.primary
        )
    }
}


@Composable
fun CertificateSection(certificates: List<Seeker.Certificate>) {
    Column {
        certificates.forEach { cert ->
            Text(
                text = cert.title,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium)
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun CVSection(cv: Seeker.CV) {
    Column {
        Text(
            text = cv.title,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = cv.link,
            style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.primary),
            modifier = Modifier.clickable {
                // TODO: Mở link CV hoặc download file
            }
        )
    }
}


