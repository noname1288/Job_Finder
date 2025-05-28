# 💼 Job Finder – Ứng dụng Quản lý Tuyển dụng Ngắn Hạn

Ứng dụng Android giúp **nhà tuyển dụng** tạo và quản lý các công việc ngắn hạn, theo dõi **ứng viên**, cập nhật thông tin hồ sơ, và điều hướng dễ dàng qua nhiều màn hình.

---

## 📲 Tính năng chính
- 🔐 Đăng ký, đăng nhập tài khoản
- 📋 Tạo và quản lý danh sách công việc ngắn hạn
- 👥 Quản lý hồ sơ ứng viên và người làm việc
- 📝 Chỉnh sửa thông tin cá nhân, hồ sơ công việc
- 🧭 Điều hướng đa màn hình sử dụng **Navigation Compose**

---

## 🛠️ Công nghệ & Kiến trúc
- **Ngôn ngữ**: Kotlin
- **UI Toolkit**: Jetpack Compose + Material Design 3
- **Networking**: Retrofit + Gson
- **Kiến trúc tổng thể**: Clean Architecture + MVVM

```

📁 Cấu trúc dự án:
├── core                      # Định nghĩa NetworkResult wrapper
├── data
│   ├── local                # (RoomDB hoặc local caching)
│   └── remote
│       ├── api             # Interface Retrofit
│       ├── dto             # Data Transfer Object
│       │   ├── request
│       │   └── response
│       ├── mapper          # Chuyển đổi từ DTO → domain
│       └── repository      # Triển khai repository từ domain
├── domain
│   ├── entity              # Mô hình domain (sạch)
│   ├── repository          # Interface kết nối tầng data
│   └── usecase             # Logic kiểm tra, xử lý nghiệp vụ
├── navigation              # Định nghĩa route màn hình
├── presentation
│   └── ...                 # Các màn hình: login, home, profile, message, register, workspace/...
├── service\_locator         # Dependency Injection thủ công
├── ui/theme                # Màu sắc, typography
└── utils/component         # Các component tái sử dụng

````

---

## 🧠 Điểm nổi bật trong dự án
- Tách rõ 3 tầng: `data`, `domain`, `presentation`
- Dữ liệu đi từ API → DTO → Mapper → Entity → UI
- Xử lý lỗi API tập trung thông qua `NetworkResult<T>`
- Giao diện Compose kết hợp State Management hiện đại

```

---

✅ Nếu bạn cần mình **chuyển sang tiếng Anh**, **thêm ảnh demo**, hoặc **tạo badge đẹp như GitHub Stars / Android Version**, chỉ cần nói nhé.  
Bạn có muốn mình **xuất file README.md sẵn** để copy vào dự án không?
```
