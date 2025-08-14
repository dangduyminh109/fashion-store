package com.fashion_store.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum ErrorCode {
    INTERNAL_EXCEPTION(99, "Lỗi máy chủ nội bộ", HttpStatus.INTERNAL_SERVER_ERROR),
    NOT_EXIST(11, "Đối tượng không tồn tại", HttpStatus.NOT_FOUND),
    EXISTED(12, "Đối tượng đã tồn tại", HttpStatus.BAD_REQUEST),
    INVALID_KEY(13, "Khóa không hợp lệ", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_NAME(14, "Tên không hợp lệ", HttpStatus.BAD_REQUEST),
    INVALID_VALUE(16, "Giá trị không hợp lệ", HttpStatus.BAD_REQUEST),
    INVALID_SKU(17, "SKU phải có ít nhất 3 ký tự", HttpStatus.BAD_REQUEST),
    INVALID_TYPE(18, "Loại hiển thị không hợp lệ", HttpStatus.BAD_REQUEST),
    INVALID_PHONE(19, "Số điện thoại không hợp lệ", HttpStatus.BAD_REQUEST),
    INVALID_FILE(21, "Tệp không hợp lệ", HttpStatus.BAD_REQUEST),
    FILE_SAVE_FAILED(22, "Lưu tệp thất bại", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_PRICE(23, "Giá không hợp lệ", HttpStatus.BAD_REQUEST),
    INVALID_ORIGINAL_PRICE(30, "Giá gốc phải lớn hơn 0", HttpStatus.BAD_REQUEST),
    INVALID_SALE_PRICE(31, "Giá bán phải lớn hơn 0", HttpStatus.BAD_REQUEST),
    INVALID_PROMOTIONAL_PRICE(32, "Giá khuyến mãi phải lớn hơn 0", HttpStatus.BAD_REQUEST),
    INVALID_PROMOTION_TIME(33, "Thời gian khuyến mãi không hợp lệ", HttpStatus.BAD_REQUEST),
    INVALID_VARIANT(33, "Yêu cầu ít nhất một biến thể", HttpStatus.BAD_REQUEST),
    INVALID_FORM_FORMAT(34, "Định dạng không hợp lệ", HttpStatus.BAD_REQUEST),
    INVALID_PROMOTION_START_TIME(34, "Thời gian bắt đầu khuyến mãi không hợp lệ", HttpStatus.BAD_REQUEST),
    INVALID_PROMOTION_END_TIME(34, "Thời gian kết thúc khuyến mãi không hợp lệ", HttpStatus.BAD_REQUEST),
    INVALID_ATTRIBUTE_VALUE(34, "Phải có ít nhất một giá trị thuộc tính trong biến thể", HttpStatus.BAD_REQUEST),
    VARIANT_ID_MISMATCH(35, "ID biến thể không khớp với danh sách cần cập nhật", HttpStatus.BAD_REQUEST),
    INVALID_VARIANT_LIST(36, "Dữ liệu biến thể không hợp lệ", HttpStatus.BAD_REQUEST),
    INVALID_CONTENT(37, "Nội dung không được để trống", HttpStatus.BAD_REQUEST),
    INVALID_TITLE(38, "Tiêu đề không hợp lệ", HttpStatus.BAD_REQUEST),
    INVALID_LIST_IMPORT_ITEM(39, "Danh sách mục nhập phải có ít nhất một mục", HttpStatus.BAD_REQUEST),
    INVALID_QUANTITY(40, "Số lượng phải lớn hơn 0", HttpStatus.BAD_REQUEST),
    INVALID_VARIANT_ID(41, "ID biến thể không hợp lệ", HttpStatus.BAD_REQUEST),
    INVALID_VARIANT_SKU(41, "Mã SKU của biến thể không hợp lệ", HttpStatus.BAD_REQUEST),
    INVALID_IMPORT_PRICE(42, "Giá nhập không hợp lệ", HttpStatus.BAD_REQUEST),
    INVALID_CODE(43, "Mã không hợp lệ", HttpStatus.BAD_REQUEST),
    INVALID_VOUCHER_TIME(44, "Thời gian voucher không hợp lệ", HttpStatus.BAD_REQUEST),
    INVALID_VOUCHER_DISCOUNT(45, "Giảm giá voucher không hợp lệ", HttpStatus.BAD_REQUEST),
    INVALID_ORDER_VALUE(46, "Giá trị đơn hàng tối thiểu không hợp lệ", HttpStatus.BAD_REQUEST),
    INVALID_VOUCHER_TYPE(47, "Loại voucher không hợp lệ", HttpStatus.BAD_REQUEST),
    INVALID_TYPE_DATA(48, "Dữ liệu loại không hợp lệ", HttpStatus.BAD_REQUEST),
    INVALID_ATTRIBUTE_COUNT(20, "Thuộc tính phải có ít nhất một giá trị", HttpStatus.BAD_REQUEST),
    INVALID_CUSTOMER_ID(49, "ID khách hàng không hợp lệ", HttpStatus.BAD_REQUEST),
    INVALID_CUSTOMER_NAME(50, "Tên khách hàng không hợp lệ", HttpStatus.BAD_REQUEST),
    INVALID_ADDRESS(51, "Địa chỉ không hợp lệ", HttpStatus.BAD_REQUEST),
    INVALID_ADDRESS_PHONE(52, "Số điện thoại trong địa chỉ không hợp lệ", HttpStatus.BAD_REQUEST),
    INVALID_CITY(53, "Thành phố không hợp lệ", HttpStatus.BAD_REQUEST),
    INVALID_DISTRICT(54, "Quận/Huyện không hợp lệ", HttpStatus.BAD_REQUEST),
    INVALID_AUTH_PROVIDER(56, "Nhà cung cấp xác thực không hợp lệ", HttpStatus.BAD_REQUEST),
    INVALID_WARD(55, "Phường/Xã không hợp lệ", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(57, "Tên người dùng phải có ít nhất 3 ký tự", HttpStatus.BAD_REQUEST),
    INVALID_DOB(58, "Ngày sinh phải từ 17 tuổi trở lên", HttpStatus.BAD_REQUEST),
    CANNOT_BE_DELETE(59, "Không thể xóa vai trò quản trị viên", HttpStatus.BAD_REQUEST),
    CANNOT_BE_UPDATE(60, "Không thể cập nhật vai trò quản trị viên", HttpStatus.BAD_REQUEST),
    INVALID_ROLE_ID(61, "ID vai trò không hợp lệ", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(62, "Mật khẩu không hợp lệ", HttpStatus.BAD_REQUEST),
    ROLE_IN_USE(63, "Không thể xóa vai trò vì hiện đang được gán cho một hoặc nhiều người dùng", HttpStatus.BAD_REQUEST),
    UNSUPPORTED_MEDIA_TYPE(64, "Loại phương tiện không được hỗ trợ", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(66, "Chưa được xác thực", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(67, "Bạn không có quyền truy cập", HttpStatus.FORBIDDEN),
    CANNOT_CREATE_TOKEN(68, "Không thể tạo token", HttpStatus.BAD_REQUEST),
    INVALID_TOKEN(69, "Token không hợp lệ", HttpStatus.UNAUTHORIZED),
    ACTION_FORBIDDEN(70, "Hành động bị cấm", HttpStatus.FORBIDDEN),
    INVALID_LIST_PERMISSION_ID(65, "Danh sách ID quyền không hợp lệ", HttpStatus.BAD_REQUEST),
    EMAIL_REQUIRED(71, "Yêu cầu nhập email", HttpStatus.BAD_REQUEST),
    INVALID_EMAIL(72, "Định dạng email không hợp lệ", HttpStatus.BAD_REQUEST),
    PASSWORD_REQUIRED(73, "Yêu cầu nhập mật khẩu", HttpStatus.BAD_REQUEST),
    PASSWORD_LENGTH_INVALID(74, "Mật khẩu phải từ 6 đến 32 ký tự", HttpStatus.BAD_REQUEST),
    INVALID_OTP(76, "OTP không hợp lệ", HttpStatus.BAD_REQUEST),
    EMAIL_SEND_FAILED(77, "Gửi email thất bại", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_PRODUCT_ID(78, "ID sản phẩm không hợp lệ", HttpStatus.BAD_REQUEST),
    INVALID_QUANTITY_UPDATE(79, "Số lượng yêu cầu vượt quá số lượng hiện có", HttpStatus.BAD_REQUEST),
    VARIANT_NOT_AVAILABLE(80, "Sản phẩm không có sẵn", HttpStatus.BAD_REQUEST),
    INVALID_PAYMENT_METHOD(81, "Phương thức thanh toán không hợp lệ", HttpStatus.BAD_REQUEST),
    INVALID_ORDER_ITEM_LIST(82, "Đơn hàng phải có ít nhất 1 sản phẩm", HttpStatus.BAD_REQUEST),
    VOUCHER_NOT_STARTED(83, "Voucher chưa được áp dụng", HttpStatus.BAD_REQUEST),
    VOUCHER_USAGE_LIMIT_EXCEEDED(83, "Voucher đã được sử dụng hết", HttpStatus.BAD_REQUEST),
    VOUCHER_EXPIRED(84, "Voucher đã hết hạn", HttpStatus.BAD_REQUEST),
    INVALID_ORDER_STATUS(84, "Trạng thái đơn hàng không hợp lệ", HttpStatus.BAD_REQUEST),
    PAID_ORDER(84, "Đơn hàng đã được thanh toán", HttpStatus.BAD_REQUEST),
    NOT_AUTHORIZED_ORDER_ACCESS(85, "Bạn không được phép truy cập đơn hàng này", HttpStatus.FORBIDDEN),
    INVALID_SIGNATURE(85, "Chữ ký không hợp lệ", HttpStatus.BAD_REQUEST),
    PASSWORD_WEAK(75, "Mật khẩu phải chứa ít nhất một chữ cái in hoa, một chữ cái thường, một số và một ký tự đặc biệt", HttpStatus.BAD_REQUEST);

    int code;
    String message;
    HttpStatusCode httpStatusCode;
}