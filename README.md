# 🛍️ Fashion Store - Backend

**Fashion Store** là ứng dụng **backend** được xây dựng bằng **Spring Boot** để cung cấp REST API cho frontend (FE).  
Dự án tập trung vào các chức năng thương mại điện tử như quản lý sản phẩm, giỏ hàng, đơn hàng, thanh toán và xác thực người dùng.

---

## 🚀 Công nghệ & Thư viện sử dụng
- **Spring Boot** – Backend framework
- **Spring Data JPA** – Tương tác với cơ sở dữ liệu
- **Spring Security (OAuth2, JWT)** – Xác thực & phân quyền
- **Lombok** – Giảm boilerplate code
- **MapStruct** – Mapping DTO ↔ Entity
- **Nimbus JOSE + JWT** – Xử lý JWT
- **Cloudinary** – Lưu trữ & quản lý hình ảnh
- **VNPay** – Tích hợp thanh toán trực tuyến
- **MySQL** – Cơ sở dữ liệu chính

---

## 🔐 Xác thực & Phân quyền
- **Customer**
  - Đăng ký / đăng nhập bằng **Google OAuth2**
  - Xác thực bằng **JWT + Refresh Token**
- **System User (Admin / Staff)**
  - Quản lý theo **Role & Permission**
  - Xác thực bằng **JWT + Refresh Token**

---

## ⚙️ Các chức năng chính
- 👕 Quản lý sản phẩm, danh mục, hình ảnh (Cloudinary),...
- 🛒 Giỏ hàng, đơn hàng  
- 💳 Thanh toán qua **VNPay**  
- 🔑 Xác thực & phân quyền với **JWT**  
- 🌐 Đăng nhập Google OAuth2 cho customer  
- 👤 Quản lý user, role, permission  
