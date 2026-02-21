package com.lakshman.todo.common.utils;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.lakshman.todo.common.dto.ApiResponse;
import com.lakshman.todo.common.dto.ApiResponseWithErrors;

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

    // public static <T> ResponseEntity<ApiResponseWithPagination<T>> buildApiPaginatedOkResponse(ApiResponseWithPagination<T> response) {
    //     if (response.getSuccess())
    //         return ResponseEntity.status(HttpStatus.OK).body(response);
    //     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    // }

    public static <T> ApiResponse<T> buildSuccessResponse(T response, String message) {
        ApiResponse<T> apiResponse = new ApiResponse<>();
        apiResponse.setData(response);
        apiResponse.setMessage(message);
        apiResponse.setSuccess(true);
        return apiResponse;
    }

    // public static <T> ApiResponseWithPagination<List<T>> buildSuccessPaginatedResponse(Page<T> res, String message) {
    //     ApiResponseWithPagination<List<T>> response = new ApiResponseWithPagination<>();
    //     response.setData(res.getContent());
    //     response.setMessage(message);
    //     response.setSuccess(true);
    //     response.setPagination(getPaginationDto(res));
    //     return response;
    // }

    // private static <T> PaginationDto getPaginationDto(Page<T> res) {
    //     PaginationDto paginationDto = new PaginationDto();
    //     paginationDto.setFirst(res.isFirst());
    //     paginationDto.setLast(res.isLast());
    //     paginationDto.setNumberOfElements(res.getNumberOfElements());
    //     paginationDto.setPageNumber(res.getNumber());
    //     paginationDto.setPageSize(res.getSize());
    //     paginationDto.setTotalElements(res.getTotalElements());
    //     paginationDto.setTotalPages(res.getTotalPages());
    //     return paginationDto;
    // }

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
