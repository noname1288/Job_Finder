├── core
│   └── NetworkResult<out T>
├── data
│   ├── local
│   └── remote
│       ├── api
│       ├── dto
│       │   ├── request
│       │   └── response
│       ├── mapper
│       └── repository            # impl cho repository của domain
├── domain
│   ├── entity
│   ├── repository                # Giao tiếp với API, chỉ trả về NetworkResult<BaseResponse<T>>
│   └── usecase                  # Xử lý logic kiểm tra code, data, message, chuyển đổi thành domain
├── navigation
├── presentation                 # Nhận dữ liệu domain đã "sạch", chỉ lo hiển thị
│   ├── candidate
│   ├── forgotpassword
│   ├── home
│   ├── login
│   ├── message
│   ├── notification
│   ├── profile
│   ├── register
│   └── workspace
│       ├── create
│       ├── detail
│       └── update
├── service_locator
├── ui
│   └── theme
└── utils
    └── component
