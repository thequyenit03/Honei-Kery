export class ApiConstants {
    private static API(path: string): string {return `/api/${path}`;}

    //API - Auth
    private static API_AUTH(path: string): string {return this.API(`auth/${path}`);}
    public static readonly API_AUTH_LOGIN: string = this.API_AUTH('login');

}