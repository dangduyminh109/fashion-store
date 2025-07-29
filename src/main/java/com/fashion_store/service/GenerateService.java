package com.fashion_store.service;

import com.fashion_store.entity.BaseModel;
import com.fashion_store.exception.AppException;
import com.fashion_store.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.repository.JpaRepository;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED)
public abstract class GenerateService<T extends BaseModel, ID> {
    abstract JpaRepository<T, ID> getRepository();

    public void delete(ID id) {
        T item = getRepository().findById(id).orElseThrow(() -> new AppException(ErrorCode.NOT_EXIST));
        try {
            item.setIsDeleted(true);
            getRepository().save(item);
        } catch (Exception e) {
            throw new AppException(ErrorCode.INTERNAL_EXCEPTION);
        }
    }

    public void destroy(ID id) {
        getRepository().deleteById(id);
    }

    public void restore(ID id) {
        T item = getRepository().findById(id).orElseThrow(() -> new AppException(ErrorCode.NOT_EXIST));
        try {
            item.setIsDeleted(false);
            getRepository().save(item);
        } catch (Exception e) {
            throw new AppException(ErrorCode.INTERNAL_EXCEPTION);
        }
    }
}
