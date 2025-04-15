package com.example.jobfinder.utils

/*
* com.example.jobfinder
|
├── di                // Dependency Injection (Hilt, Koin) cấu hình, modules
|
├── data              // Tầng Data: định nghĩa các repository, data source, API, local storage
|   ├── remote        // API service, DTO, network module
|   └── local         // Database, cache, dao, entities
|
├── domain            // (Tùy chọn) Chứa các use-case hoặc business logic chung
|   └── usecase       // Các use-case ứng với mỗi tác vụ chính
|
├── navigation        // Định nghĩa các màn hình và routes sử dụng Navigation Compose
|
├── ui                // Tầng UI chia theo feature
|   ├── common        // Các thành phần dùng chung: theme, component, utils, extension, custom view, dialog
|   ├── home          // Ví dụ: Home screen (dashboard)
|   │   ├── HomeScreen.kt            // Màn hình chính
|   │   ├── HomeViewModel.kt         // ViewModel cho home feature
|   │   └── HomeComponents.kt        // Các Composable chức năng riêng của Home
|   │
|   ├── joblist       // Feature: Danh sách việc làm
|   │   ├── JobListScreen.kt         // UI danh sách job
|   │   ├── JobListViewModel.kt      // ViewModel tương ứng
|   │   ├── JobListState.kt          // Các state, event, action của job list
|   │   └── components               // Các composable con (item job, header, footer)
|   │
|   ├── jobdetail     // Feature: Chi tiết việc làm
|   │   ├── JobDetailScreen.kt
|   │   ├── JobDetailViewModel.kt
|   │   └── components
|   │
|   ├── candidate     // Feature: Danh sách ứng viên / chi tiết ứng viên
|   │   ├── CandidateListScreen.kt
|   │   ├── CandidateDetailScreen.kt   // Nếu có
|   │   ├── CandidateViewModel.kt
|   │   └── components
|   │
|   ├── profile       // Feature: Profile người dùng
|   │   ├── ProfileScreen.kt
|   │   ├── ProfileViewModel.kt
|   │   └── components
|
└── util              // Các hàm tiện ích, constants, extensions (nếu chưa đặt trong common)
*
*
*
* */