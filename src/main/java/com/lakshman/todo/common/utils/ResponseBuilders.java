package com.lakshman.todo.common.utils;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.lakshman.todo.common.dto.ApiResponse;
import com.lakshman.todo.common.dto.ApiResponseWithErrors;
import com.lakshman.todo.common.dto.ApiResponseWithPagination;
import com.lakshman.todo.common.dto.Pagination;

public class ResponseBuilders {

    public static <T> ResponseEntity<ApiResponse<T>> buildCreateApiResponse(ApiResponse<T> response) {
        if (response.getSuccess())
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    public static <T> ResponseEntity<ApiResponse<T>> buildApiOkResponse(ApiResponse<T> response) {
        if (response.getSuccess())
            return ResponseEntity.status(HttpStatus.OK).body(response);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // public static <T> ResponseEntity<ApiResponseWithPagination<T>>
    // buildApiPaginatedOkResponse(ApiResponseWithPagination<T> response) {
    // if (response.getSuccess())
    // return ResponseEntity.status(HttpStatus.OK).body(response);
    // return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    // }

    public static <T> ApiResponse<T> buildSuccessResponse(T response, String message, String systemCode) {
        ApiResponse<T> apiResponse = new ApiResponse<>();
        apiResponse.setData(response);
        apiResponse.setMessage(message);
        apiResponse.setSuccess(true);
        apiResponse.setHttpCode(HttpStatus.OK);

        if (systemCode != null)
            apiResponse.setSystemCode(systemCode); // any other custom code in our system
        else
            apiResponse.setSystemCode(HttpStatus.OK + ""); // any other custom code in our system

        return apiResponse;
    }

    public static <T> ApiResponseWithPagination<List<T>> buildSuccessPaginatedResponse(Page<T> res, String message,
            String sysCode) {
        ApiResponseWithPagination<List<T>> response = new ApiResponseWithPagination<>();
        response.setData(res.getContent());
        response.setMessage(message);
        response.setSuccess(true);
        response.setPagination(getPaginationDto(res));
        response.setSystemCode(sysCode);
        return response;
    }

    public static <T> ApiResponseWithPagination<List<T>> buildSuccessPaginatedResponse(Page<?> res, List<T> list,
            String message, String sysCode) {
        ApiResponseWithPagination<List<T>> response = new ApiResponseWithPagination<>();
        response.setData(list);
        response.setMessage(message);
        response.setSuccess(true);
        response.setPagination(getPaginationDto(res));
        response.setSystemCode(sysCode);
        return response;
    }

    private static <T> Pagination getPaginationDto(Page<?> res) {
        Pagination paginationDto = new Pagination();
        paginationDto.setLast(res.isLast());
        paginationDto.setPage(res.getNumber());
        paginationDto.setSize(res.getSize());
        paginationDto.setTotalElements(res.getTotalElements());
        paginationDto.setTotalPages(res.getTotalPages());
        return paginationDto;
    }

    public static <T> ApiResponseWithErrors<T> buildResponseWithErrors(String message, Map<String, String> errors) {
        ApiResponseWithErrors<T> apiErrorResponse = new ApiResponseWithErrors<>();
        apiErrorResponse.setData(null);
        apiErrorResponse.setMessage(message);
        apiErrorResponse.setSuccess(false);
        return apiErrorResponse;
    }

    public static <T> ApiResponse<T> buildResponseWithErrorMessage(String message) {
        ApiResponse<T> apiErrorResponse = new ApiResponse<>();
        apiErrorResponse.setData(null);
        apiErrorResponse.setMessage(message);
        apiErrorResponse.setSuccess(false);
        return apiErrorResponse;
    }
}