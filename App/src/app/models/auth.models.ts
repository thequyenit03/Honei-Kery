export interface LoginRequest {
  username: string;
  password: string;
}

export interface LoginResponse {
    email: string;
    id: number;
    roleId: number
    roleName: string;
    token: string;
    username: string;
}

export interface RefreshTokenRequest {
  refreshToken: string;
}


export interface ChangePasswordRequest {
  passwordOld: string;
  passwordNew: string;
  userId: number;
}