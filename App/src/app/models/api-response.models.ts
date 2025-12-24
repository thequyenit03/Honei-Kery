export interface ApiResponse<T> {
  datetime: string;
  errorCode: string;
  message: string;
  data: T;
  success: boolean;
}

export interface ApiResponseListContent<T> {
  content: T[];
  pageable: {
    pageNumber: number;
    pageSize: number;
    sort: {
      sorted: boolean;
      empty: boolean;
      unsorted: boolean;
    };
    findAll: boolean;
    offset: number;
    paged: boolean;
    unpaged: boolean;
  };
  totalElements: number;
  totalPages: number;
  last: boolean;
  size: number;
  number: number;
  sort: {
    sorted: boolean;
    empty: boolean;
    unsorted: boolean;
  };
  numberOfElements: number;
  first: boolean;
  empty: boolean;
}

export interface ApiResponseList<T> {
  datetime: string;
  errorCode: string;
  message: string;
  data: ApiResponseListContent<T>;
  success: boolean;
}

export interface ApiResponseListAll<T> {
  datetime: string;
  errorCode: string;
  message: string;
  data: T[];
  success: boolean;
}